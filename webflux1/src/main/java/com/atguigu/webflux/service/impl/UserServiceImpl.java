package com.atguigu.webflux.service.impl;

import com.atguigu.webflux.entity.User;
import com.atguigu.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    //创建Map存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl() {
        this.users.put(1, new User("lucy", "female", 20));
        this.users.put(2, new User("mary", "female", 30));
        this.users.put(3, new User("jack", "male", 50));
    }

    //根据id查询
    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    //查询多个用户
    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    //添加用户
    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            //向Map中存储数据
            int id = users.size() + 1;
            users.put(id, user);
        }).thenEmpty(Mono.empty());
    }

}
