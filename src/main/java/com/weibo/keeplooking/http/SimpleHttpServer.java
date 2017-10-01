package com.weibo.keeplooking.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/*
 * A simple static http server.
 */
public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/hello", new My200Handler());
        server.createContext("/hi", new My401Handler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started at 8000 port!");
    }

    static class My200Handler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = "Hello World!".getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class My401Handler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            System.out.println("New request coming...");

            t.getRequestHeaders().entrySet().forEach(header -> System.out.println(header.getKey() + " -> " + header.getValue()));

            if (t.getRequestHeaders().entrySet().stream().map(header -> header.getKey()).anyMatch(key -> key.equalsIgnoreCase("Authorization"))) {
                byte[] response = "Hi World!".getBytes();
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();
            } else {
                byte[] response = "Authentication needed.".getBytes();
                Headers headers = t.getResponseHeaders();
                headers.add("Www-Authenticate", "Basic realm=\"Fake Realm\",OAuth2 realm=\"OpenAPI blablabla\"");
                t.sendResponseHeaders(401, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();
            }

            System.out.println("Response done.");
        }
    }
}
