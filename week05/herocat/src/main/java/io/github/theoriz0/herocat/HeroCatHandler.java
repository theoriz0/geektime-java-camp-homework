package io.github.theoriz0.herocat;

import java.util.Map;
import java.util.Set;

import io.github.theoriz0.servlet.HeroRequest;
import io.github.theoriz0.servlet.HeroResponse;
import io.github.theoriz0.servlet.HeroServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class HeroCatHandler extends ChannelInboundHandlerAdapter{
    private Map<String, HeroServlet> nameToServletMap;
    private Map<String, String> nameToClassNameMap;
    private Set<String> staticFilePathsSet;

    public HeroCatHandler(Map<String, HeroServlet> nameToServletMap, Map<String, String> nameToClassNameMap,
            Set<String> staticFilePathsSet) {
        this.nameToServletMap = nameToServletMap;
        this.nameToClassNameMap = nameToClassNameMap;
        this.staticFilePathsSet = staticFilePathsSet;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uriWithoutParams = request.uri().split("\\?")[0];
            String servletName = uriWithoutParams.substring(uriWithoutParams.lastIndexOf("/") + 1);
            HeroServlet heroServlet = new DefaultHeroServlet();
            if (nameToServletMap.containsKey(servletName)) {
                heroServlet = nameToServletMap.get(servletName);
            } else if (nameToClassNameMap.containsKey(servletName)) {
                // 双重检测锁
                if (nameToServletMap.get(servletName) == null) {
                    synchronized (this) {
                    String className = nameToClassNameMap.get(servletName);
                    heroServlet = (HeroServlet) Class.forName(className).newInstance();
                    nameToServletMap.put(servletName, heroServlet);
                    }
                }
            }
            
            if (staticFilePathsSet.contains(uriWithoutParams)) {
                heroServlet = new StaticFileServlet();
            }

            HeroRequest req = new HttpHeroRequest(request);
            HeroResponse resp = new HttpHeroResponse(request, ctx);

            if (request.method().name().equalsIgnoreCase("GET")) {
                heroServlet.doGet(req, resp);
            } else if (request.method().name().equalsIgnoreCase("POST")) {
                heroServlet.doPost(req, resp);
            }

            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
