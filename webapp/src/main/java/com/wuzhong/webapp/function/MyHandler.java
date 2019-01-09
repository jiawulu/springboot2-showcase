package com.wuzhong.webapp.function;

import io.undertow.Undertow;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.*;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class MyHandler {

    public static RouterFunction config() {

        HandlerFunction<ServerResponse> helloHandler = request -> {
            Optional<String> name = request.queryParam("name");
            return ServerResponse.ok().body(fromObject("Hello to "
                    + name.orElse("the world.")));
        };

        RouterFunction<ServerResponse> route =
                RouterFunctions.route(RequestPredicates.path("/hello2"), helloHandler);
        return route;
    }

    public static void main(String[] args) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(config());
        UndertowHttpHandlerAdapter adapter = new
                UndertowHttpHandlerAdapter(httpHandler);
        Undertow server = Undertow.builder().addHttpListener(8080,
                "127.0.0.1").setHandler(adapter).build();
        server.start();
    }


}
