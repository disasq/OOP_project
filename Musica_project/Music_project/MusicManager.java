import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.sql.*;

public class MusicManager implements MusicCollection  {
    private DatabaseManager dbManager;

    public MusicManager() {
        this.dbManager = new DatabaseManager();
    }

    private Integer getOrCreateAlbum(String albumName) throws SQLException {
        String albumQuery = "SELECT id FROM albums WHERE name = ?";
        try (PreparedStatement stmt = dbManager.connect().prepareStatement(albumQuery)) {
            stmt.setString(1, albumName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                String createAlbumQuery = "INSERT INTO albums (name) VALUES (?)";
                try (PreparedStatement createStmt = dbManager.connect().prepareStatement(createAlbumQuery, Statement.RETURN_GENERATED_KEYS)) {
                    createStmt.setString(1, albumName);
                    createStmt.executeUpdate();

                    ResultSet generatedKeys = createStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return null;
    }


    public void addSong(String title, String artist, String albumName) {
        try {

            String checkQuery = "SELECT * FROM songs WHERE title = ? AND artist = ?";
            try (PreparedStatement stmt = dbManager.connect().prepareStatement(checkQuery)) {
                stmt.setString(1, title);
                stmt.setString(2, artist);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "This song already exists.");
                    return;
                }
            }


            Integer albumId = getOrCreateAlbum(albumName);

            String sql = "INSERT INTO songs (title, artist, album_id, is_favorite) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = dbManager.connect().prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setString(2, artist);
                stmt.setInt(3, albumId);
                stmt.setBoolean(4, false);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Song added: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void deleteSong(String songTitle) {
        try {
            String songQuery = "SELECT id FROM songs WHERE title = ?";
            PreparedStatement songStmt = dbManager.connect().prepareStatement(songQuery);
            songStmt.setString(1, songTitle);
            ResultSet songResultSet = songStmt.executeQuery();

            if (songResultSet.next()) {
                int songId = songResultSet.getInt("id");


                String deleteQuery = "DELETE FROM songs WHERE id = ?";
                PreparedStatement deleteStmt = dbManager.connect().prepareStatement(deleteQuery);
                deleteStmt.setInt(1, songId);
                deleteStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Song \"" + songTitle + "\" deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "Song \"" + songTitle + "\" not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addToFavorites(String songTitle) {
        try {
            String sql = "UPDATE songs SET is_favorite = TRUE WHERE title = ?";
            try (PreparedStatement stmt = dbManager.connect().prepareStatement(sql)) {
                stmt.setString(1, songTitle);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Song added to favorites.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeFromFavorites(String songTitle) {
        try {
            String sql = "UPDATE songs SET is_favorite = FALSE WHERE title = ?";
            try (PreparedStatement stmt = dbManager.connect().prepareStatement(sql)) {
                stmt.setString(1, songTitle);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Song removed from favorites.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewAllSongs(JTextArea textArea) {
        try {
            String sql = "SELECT * FROM albums LEFT JOIN songs ON albums.id = songs.album_id ORDER BY albums.name, songs.title";
            ResultSet resultSet = dbManager.connect().createStatement().executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            String currentAlbum = null;

            while (resultSet.next()) {
                String albumName = resultSet.getString("name");
                String songTitle = resultSet.getString("title");

                if (albumName != null && !albumName.equals(currentAlbum)) {
                    if (currentAlbum != null) {
                        sb.append("\n");
                    }
                    sb.append("Album: ").append(albumName).append("\n");
                    currentAlbum = albumName;
                }

                if (albumName == null) {
                    sb.append("Song: ").append(songTitle).append("\n");
                } else {
                    sb.append(" - ").append(songTitle).append("\n");
                }
            }

            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewFavorites(JTextArea textArea) {
        try {
            String sql = "SELECT * FROM songs WHERE is_favorite = TRUE";
            ResultSet resultSet = dbManager.connect().createStatement().executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                sb.append(resultSet.getString("title")).append(" by ").append(resultSet.getString("artist")).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAlbum(String albumName) {
        try {
            String sql = "INSERT INTO albums (name) VALUES (?)";
            try (PreparedStatement stmt = dbManager.connect().prepareStatement(sql)) {
                stmt.setString(1, albumName);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Album added: " + albumName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAlbumAndSongs(String albumName) {
        try {
            String albumQuery = "SELECT id FROM albums WHERE name = ?";
            PreparedStatement albumStmt = dbManager.connect().prepareStatement(albumQuery);
            albumStmt.setString(1, albumName);
            ResultSet albumResultSet = albumStmt.executeQuery();

            if (albumResultSet.next()) {
                int albumId = albumResultSet.getInt("id");

                String deleteSongsQuery = "DELETE FROM songs WHERE album_id = ?";
                PreparedStatement deleteSongsStmt = dbManager.connect().prepareStatement(deleteSongsQuery);
                deleteSongsStmt.setInt(1, albumId);
                deleteSongsStmt.executeUpdate();


                String deleteAlbumQuery = "DELETE FROM albums WHERE id = ?";
                PreparedStatement deleteAlbumStmt = dbManager.connect().prepareStatement(deleteAlbumQuery);
                deleteAlbumStmt.setInt(1, albumId);
                deleteAlbumStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Album \"" + albumName + "\" and all its songs have been deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "Album \"" + albumName + "\" not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void moveSongToAlbum(String songTitle, String newAlbumName) {
        try {
            String albumQuery = "SELECT id FROM albums WHERE name = ?";
            try (PreparedStatement albumStmt = dbManager.connect().prepareStatement(albumQuery)) {
                albumStmt.setString(1, newAlbumName);
                ResultSet albumResultSet = albumStmt.executeQuery();

                if (albumResultSet.next()) {
                    int newAlbumId = albumResultSet.getInt("id");

                    String songQuery = "SELECT id FROM songs WHERE title = ?";
                    try (PreparedStatement songStmt = dbManager.connect().prepareStatement(songQuery)) {
                        songStmt.setString(1, songTitle);
                        ResultSet songResultSet = songStmt.executeQuery();

                        if (songResultSet.next()) {
                            int songId = songResultSet.getInt("id");

                            String updateQuery = "UPDATE songs SET album_id = ? WHERE id = ?";
                            try (PreparedStatement updateStmt = dbManager.connect().prepareStatement(updateQuery)) {
                                updateStmt.setInt(1, newAlbumId);
                                updateStmt.setInt(2, songId);
                                updateStmt.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Song \"" + songTitle + "\" moved to album: " + newAlbumName);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Song not found.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Album \"" + newAlbumName + "\" not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
