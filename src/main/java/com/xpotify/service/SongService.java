package com.xpotify.service;

import com.xpotify.entity.Song;

import java.util.List;

public interface SongService {

    Song getById(Long id);

    List<Song> getAll();

    void addSong(Song song);

    List<Song> searchSong(String param);

    void deleteSong(Long songId);
}
