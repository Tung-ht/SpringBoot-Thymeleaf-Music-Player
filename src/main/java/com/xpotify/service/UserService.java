package com.xpotify.service;

import com.xpotify.entity.Song;
import com.xpotify.entity.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    User save(User user);

    void purchaseSong(Long userId, Long songId);

    List<Song> getPurchasedSong(Long userId);
}
