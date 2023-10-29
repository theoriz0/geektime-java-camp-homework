package io.github.theoriz0.herocat;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import io.github.theoriz0.servlet.HeroServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HeroCatServer {
    private Map<String, HeroServlet> nameToServletMap = new ConcurrentHashMap<>();

    private Map<String, String> nameToClassNameMap = new HashMap<>();

    private Set<String> staticFilePathsSet = new HashSet<>();

    private String basePackage;

    public HeroCatServer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() throws Exception {
        cacheClassName(basePackage);
        cacheStaticFilePaths("");
        runServer();
    }

    private void cacheClassName(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        if (resource == null) {
            return;
        }

        File dir = new File(resource.getFile());

        for (File file: dir.listFiles()) {
            if (file.isDirectory()) {
                cacheClassName(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String simpleClassName = file.getName().replace(".class", "").trim();
                nameToClassNameMap.put(simpleClassName.toLowerCase(), basePackage + "." + simpleClassName);
                System.out.println(simpleClassName+ " added");
            }
        }
    }

    private void cacheStaticFilePaths(String basePath) {
        URL resource = this.getClass().getClassLoader().getResource("static/" + basePath);
        if (resource == null) {
            return;
        }

        File dir = new File(resource.getFile());

        for (File file: dir.listFiles()) {
            if (file.isDirectory()) {
                cacheStaticFilePaths(basePath + "/" + file.getName());
            } else {
                staticFilePathsSet.add(basePath + "/" + file.getName());
                System.out.println(basePath + "/" + file.getName() + " added");
            }
        }
    }

    private void runServer() throws Exception {
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent, child)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new HeroCatHandler(nameToServletMap, nameToClassNameMap, staticFilePathsSet));
                }
            });

            int port = initPort();
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("HeroCat init success, listening on port: " + port);
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }

    private int initPort() throws DocumentException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(in);
        Element portEle = (Element) doc.selectSingleNode("//port");
        return Integer.valueOf(portEle.getText());
    }
}
