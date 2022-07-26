package com.xpotify.service.impl;

import com.xpotify.entity.Song;
import com.xpotify.repo.UserRepository;
import com.xpotify.entity.User;
import com.xpotify.service.UserService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

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
    public void purchaseSong(Long userId, Song song) {
        User u = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found!"));
        u.getPurchasedSongs().add(song);
        userRepository.save(u);
    }

    @Override
    public List<Song> getPurchasedSong(Long userId) {
        return userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found!")).
                getPurchasedSongs();
    }
}
