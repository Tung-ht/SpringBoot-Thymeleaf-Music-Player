package com.xpotify.service;

import com.xpotify.entity.Song;

import java.util.List;

public interface SongService {

    List<Song> getSongsForHomePage();

    List<Song> getAll();

    void addSong(Song song);

    List<Song> searchSong(String param);

    void activateSong(Long songId);

    void deactivateSong(Long songId);
}
