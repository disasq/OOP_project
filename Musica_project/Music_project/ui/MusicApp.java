package ui;

import models.Album;
import models.Song;
import services.MusicService;
import services.MusicServiceImpl;

import java.util.Scanner;

public class MusicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MusicService musicService = new MusicServiceImpl();

        while (true) {
            System.out.println("1. Добавить песню");
            System.out.println("2. Добавить альбом");
            System.out.println("3. Найти песню");
            System.out.println("4. Показать альбомы");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Считываем перевод строки

            switch (choice) {
                case 1 -> {
                    System.out.print("Введите название песни: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите исполнителя: ");
                    String artist = scanner.nextLine();
                    System.out.print("Введите альбом: ");
                    String album = scanner.nextLine();
                    System.out.print("Введите длительность (в секундах): ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    Song song = new Song(title, artist, album, duration);
                    musicService.addSong(song);
                    System.out.println("Песня добавлена!");
                }
                case 2 -> {
                    System.out.print("Введите название альбома: ");
                    String albumName = scanner.nextLine();
                    System.out.print("Введите исполнителя: ");
                    String artist = scanner.nextLine();

                    Album album = new Album(albumName, artist);
                    musicService.addAlbum(album);
                    System.out.println("Альбом добавлен!");
                }
                case 3 -> {
                    System.out.print("Введите название песни для поиска: ");
                    String query = scanner.nextLine();
                    var foundSongs = musicService.searchSong(query);
                    if (foundSongs.isEmpty()) {
                        System.out.println("Песни не найдены.");
                    } else {
                        System.out.println("Найденные песни:");
                        foundSongs.forEach(song -> System.out.println(song.getTitle()));
                    }
                }
                case 4 -> {
                    var albums = musicService.getAlbums();
                    if (albums.isEmpty()) {
                        System.out.println("Нет альбомов.");
                    } else {
                        System.out.println("Список альбомов:");
                        albums.forEach(album -> System.out.println(album.getName()));
                    }
                }
                case 5 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте ещё раз.");
            }
        }
    }
}
