package com.example.simplecrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.simplecrud.models.User;

@Service
public class UserServices {
    private final Map<Integer, User> userMap;
    private final AtomicInteger idGenerator;

    public UserServices(List<User> users) {
        this.userMap = users.stream().collect(
            Collectors.toConcurrentMap(User::id, user -> user)
        );
        this.idGenerator = new AtomicInteger(
            users.stream()
                .mapToInt(User::id)
                .max()
                .orElse(0)
        );
    }

    public UserServices() {
        this.userMap = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicInteger(0);
    }

    public User addUser(String name, String email) {
        Integer newId = idGenerator.incrementAndGet();
        User newUser = new User(newId, name, email);
        userMap.put(newId, newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public User getUserById(Integer id) {
        return userMap.get(id);
    }

    public boolean updateUser(Integer id, String name, String email) {
        userMap.put(id, new User(id, name, email));
        return userMap.containsKey(id);
    }

    public boolean deleteUser(Integer id) {
        userMap.remove(id);
        return !userMap.containsKey(id);
    }
}
