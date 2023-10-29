package io.github.theoriz0.herocat;

import java.util.List;
import java.util.Map;

import io.github.theoriz0.servlet.HeroRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class HttpHeroRequest implements HeroRequest {

    private HttpRequest request;

    public HttpHeroRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getMethod() {
        return request.method().name();
    }

    @Override
    public String getParameter(String name) {
        List<String> parameters = getParameters(name);
        if (parameters == null || parameters.size() == 0) {
            return null;
        }
        return parameters.get(0);
    }

    @Override
    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.path();
    }

    @Override
    public String getUri() {
        return request.uri();
    }
    
}
