package com.xpotify.service.impl;

import com.xpotify.entity.Song;
import com.xpotify.repo.UserRepository;
import com.xpotify.entity.User;
import com.xpotify.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addSongToFav(Song song) {

    }

    @Override
    public void removeSongFromFav(Song song) {

    }
}
