import javax.swing.JTextArea;

public interface MusicCollection {
    void addSong(String title, String artist, String albumName);
    void deleteSong(String songTitle);
    void addToFavorites(String songTitle);
    void removeFromFavorites(String songTitle);
    void viewAllSongs(JTextArea textArea);
    void viewFavorites(JTextArea textArea);
    void addAlbum(String albumName);
    void deleteAlbumAndSongs(String albumName);
    void moveSongToAlbum(String songTitle, String newAlbumName);
}
