package com.xpotify.service.impl;

import com.xpotify.entity.Song;
import com.xpotify.repo.SongRepository;
import com.xpotify.service.SongService;
import com.xpotify.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository songRepository;

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
    public void deleteSong(Long songId) {
        Song song = songRepository.findById(songId).
                orElseThrow(() -> new NotFoundException("Song not found!"));
        String songLink = song.getSongLink();
        String imgLink = song.getImgLink();
        FileUtils.deleteFile(songLink);
        FileUtils.deleteFile(imgLink);
        songRepository.delete(song);
    }
}
