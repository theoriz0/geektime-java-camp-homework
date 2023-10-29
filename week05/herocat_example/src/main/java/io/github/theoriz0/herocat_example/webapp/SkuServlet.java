package io.github.theoriz0.herocat_example.webapp;

import io.github.theoriz0.servlet.HeroRequest;
import io.github.theoriz0.servlet.HeroResponse;
import io.github.theoriz0.servlet.HeroServlet;

public class SkuServlet extends HeroServlet{

    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String method = request.getMethod();
        String name = request.getParameter("name");
        String content = "uri = " + uri + "\n" +
                         "path = " + path + "\n" +
                         "method = " + method + "\n" +
                         "param = " + name;
        response.write(content, "text/plain");
    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }
    
}
