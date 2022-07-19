package com.xpotify.service.impl;

import com.xpotify.entity.Song;
import com.xpotify.repo.SongRepository;
import com.xpotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;

    @Override
    public List<Song> getSongsForHomePage() {
        return songRepository.findAllByStatus(1);
    }

    @Override
    public void addSong(Song song) {
        songRepository.save(song);
    }

    @Override
    public List<Song> getAll() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> searchSong(String param) {
        return songRepository.searchSong(param);
    }

    @Override
    public void activateSong(Long songId) {
        Song song = songRepository.findById(songId).get();
        song.setStatus(1);
        songRepository.save(song);
    }

    @Override
    public void deactivateSong(Long songId) {
        Song song = songRepository.findById(songId).get();
        song.setStatus(0);
        songRepository.save(song);
    }
}
