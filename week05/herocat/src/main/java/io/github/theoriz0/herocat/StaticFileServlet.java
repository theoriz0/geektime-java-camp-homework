package io.github.theoriz0.herocat;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.github.theoriz0.servlet.HeroRequest;
import io.github.theoriz0.servlet.HeroResponse;
import io.github.theoriz0.servlet.HeroServlet;

public class StaticFileServlet extends HeroServlet {

    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();
        URL resource = this.getClass().getClassLoader().getResource("static/" + uri);
        String[] uriSplitted = uri.split("\\.");
        String ext = uriSplitted[uriSplitted.length - 1];
        String type = findContentType(ext);
        String content = new String(Files.readAllBytes(Paths.get(resource.getPath())));
        response.write(content, type);
    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }

    private String findContentType(String extension) {
        switch (extension) {
            case "html":
                return "text/html";
            case "htm":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "text/javascript";
            default:
                return "text/plain";
        }
    }
    
}
