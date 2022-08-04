package com.xpotify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "song")
public class Song {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int isPremium;
    private String artist;
    private String songLink;
    private String imgLink;

    @JsonIgnore
    @ManyToMany(mappedBy = "purchasedSongs", fetch = FetchType.EAGER)
    List<User> users;

    @Transient
    public String getSongLink() {
        return this.songLink;
    }

    @Transient
    public String getImgLink() {
        return this.imgLink;
    }
}
