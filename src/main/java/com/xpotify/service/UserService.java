package com.xpotify.service;

import com.xpotify.entity.User;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
}
