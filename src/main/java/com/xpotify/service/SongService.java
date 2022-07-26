package com.xpotify.service;

import com.xpotify.entity.Song;

import java.util.List;

public interface SongService {

    List<Song> getAll();

    void addSong(Song song);

    List<Song> searchSong(String param);

    void deleteSong(Long songId);
}
