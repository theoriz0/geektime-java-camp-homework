package io.github.theoriz0.herocat;

import io.github.theoriz0.servlet.HeroRequest;
import io.github.theoriz0.servlet.HeroResponse;
import io.github.theoriz0.servlet.HeroServlet;

public class DefaultHeroServlet extends HeroServlet{

    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();
        String name = uri.split("\\?")[0].substring(uri.lastIndexOf("/") + 1);;
        response.write("404 - not found. servlet: " + name, "text/plain");
    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }
    
}
