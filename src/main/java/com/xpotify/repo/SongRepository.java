package com.xpotify.repo;

import com.xpotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByStatus(int status);

    @Query("SELECT s FROM Song s WHERE CONCAT(s.name, s.artist) LIKE %?1%")
    List<Song> searchSong(String param);

    @Modifying
    @Query("UPDATE Song SET status = 1 where id = ?1")
    void activeSong(Long songId);

    @Modifying
    @Query("UPDATE Song SET status = 0 where id = ?1")
    void deactiveSong(Long songId);
}
