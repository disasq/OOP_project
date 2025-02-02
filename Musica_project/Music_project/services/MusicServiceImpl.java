package services;

import models.Album;
import models.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicServiceImpl implements MusicService {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();

    @Override
    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public void addAlbum(Album album) {
        albums.add(album);
    }

    @Override
    public List<Song> searchSong(String title) {
        List<Song> result = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(song);
            }
        }
        return result;
    }

    @Override
    public List<Album> getAlbums() {
        return albums;
    }
}
