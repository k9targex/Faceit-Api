package com.faceit.faceit.component;

import java.util.LinkedHashMap;
import java.util.Map;
import com.faceit.faceit.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCache {
  public static final int MAX_CACHE_SIZE = 10;
  private final Map<String, User> cache =
      new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, User> eldest) {
          return size() > MAX_CACHE_SIZE;
        }
      };

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
