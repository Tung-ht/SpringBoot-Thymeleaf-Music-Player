package com.xpotify.service;

import com.xpotify.entity.Song;
import com.xpotify.entity.User;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
    void addSongToFav(Song song);
    void removeSongFromFav(Song song);
}
