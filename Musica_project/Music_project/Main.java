import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        MusicCollection musicManager = new MusicManager();

        JFrame frame = new JFrame("Music_max");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton addSongButton = new JButton("Add song");
        JButton viewAllButton = new JButton("View all songs");
        JButton addToFavoritesButton = new JButton("Add to favorites");
        JButton addAlbumButton = new JButton("Add album");
        JButton deleteSongButton = new JButton("Delete song");
        JButton removeFromFavoritesButton = new JButton("Remove from favorites");
        JButton addSongToAlbumButton = new JButton("Add song to album");
        JButton deleteAlbumButton = new JButton("Delete album");
        JButton viewFavoritesButton = new JButton("View favorites");


        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);


        JPanel panel = new JPanel();

        panel.add(addSongButton);
        panel.add(deleteSongButton);
        panel.add(viewAllButton);
        panel.add(addToFavoritesButton);
        panel.add(viewFavoritesButton);
        panel.add(removeFromFavoritesButton);
        panel.add(addAlbumButton);
        panel.add(addSongToAlbumButton);
        panel.add(deleteAlbumButton);
        panel.add(scrollPane);

        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(frame, "Enter song title:");
                String artist = JOptionPane.showInputDialog(frame, "Enter artist name:");
                String album = JOptionPane.showInputDialog(frame, "Enter album name (Leave blank for 'Каталог песен'):");

                if (title != null && artist != null && !title.trim().isEmpty() && !artist.trim().isEmpty()) {
                    musicManager.addSong(title, artist, album);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all required fields.");
                }
            }
        });


        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                musicManager.viewAllSongs(textArea);
            }
        });

        addSongToAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songTitle = JOptionPane.showInputDialog(frame, "Enter song title:");
                String newAlbumName = JOptionPane.showInputDialog(frame, "Enter new album name:");

                if (songTitle != null && !songTitle.trim().isEmpty() && newAlbumName != null && !newAlbumName.trim().isEmpty()) {
                    musicManager.moveSongToAlbum(songTitle, newAlbumName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter valid song and album names.");
                }
            }
        });

        deleteSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songTitle = JOptionPane.showInputDialog(frame, "Enter song title to delete:");
                if (songTitle != null && !songTitle.trim().isEmpty()) {
                    musicManager.deleteSong(songTitle);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid song title.");
                }
            }
        });

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songTitle = JOptionPane.showInputDialog(frame, "Enter song title to add to favorites:");
                if (songTitle != null && !songTitle.trim().isEmpty()) {
                    musicManager.addToFavorites(songTitle);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid song title.");
                }
            }
        });

        removeFromFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songTitle = JOptionPane.showInputDialog(frame, "Enter song title to remove from favorites:");
                if (songTitle != null && !songTitle.trim().isEmpty()) {
                    musicManager.removeFromFavorites(songTitle);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid song title.");
                }
            }
        });

        viewFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                musicManager.viewFavorites(textArea);
            }
        });

        addAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String albumName = JOptionPane.showInputDialog(frame, "Enter album name:");
                if (albumName != null && !albumName.trim().isEmpty()) {
                    musicManager.addAlbum(albumName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid album name.");
                }
            }
        });

        deleteAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String albumName = JOptionPane.showInputDialog(frame, "Enter album name to delete:");
                if (albumName != null && !albumName.trim().isEmpty()) {
                    musicManager.deleteAlbumAndSongs(albumName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid album name.");
                }
            }
        });


        frame.getContentPane().add(panel);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
