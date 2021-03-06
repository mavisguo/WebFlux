package com.atguigu.webflux;

import com.atguigu.webflux.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {

    public static void main(String[] args) {
        //调用服务器地址
        WebClient webClient = WebClient.create("http://127.0.0.1:11032");

        //根据id查询
        String id = "1";
        User user = webClient.get().uri("/user/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(User.class).block();
        System.out.println(user.getName());

        //查询所有
        Flux<User> users = webClient.get().uri("/user").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(User.class);

        users.map(User::getName).buffer().doOnNext(System.out::println).blockFirst();
    }

}
