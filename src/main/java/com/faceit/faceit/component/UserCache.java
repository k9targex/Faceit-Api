package com.faceit.faceit.component;

import java.util.LinkedHashMap;
import java.util.Map;
import com.faceit.faceit.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCache {
    private Map<String, User> cache = new LinkedHashMap<>();

    public void put(String username, User user) {
        cache.put(username, user);
    }

    public User get(String username) {
        return cache.get(username);
    }
    public void remove(String username) {
        cache.remove(username);
    }
    public void clear() {
        cache.clear();
    }
}
