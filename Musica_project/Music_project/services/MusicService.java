package services;

import models.Album;
import models.Song;

import java.util.List;

public interface MusicService {
    void addSong(Song song);
    void addAlbum(Album album);
    List<Song> searchSong(String title);
    List<Album> getAlbums();
}
