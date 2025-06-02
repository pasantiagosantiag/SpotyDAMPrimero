package ies.sequeros.dam.spotydam.domain.model;

import java.time.LocalDate;
import java.util.*;

public class Song {
    private  UUID id;
    private String name;
    private List<Genre> genres;
    private String path;



    private String pathImage;
    private UUID ownerId;
    private String author;
    private String description;
    private LocalDate registrationDate;
    private boolean isPublic;
    private int playCount;
    private List<Reaction> likes = new ArrayList<>();
    private List<Reaction> dislikes = new ArrayList<>();

    public enum Genre {
        ROCK, POP, JAZZ, REGGAETON, CLASSICAL, ELECTRONIC, BLUES, SALSA, METAL
    }

    public Song(String name, List<Genre> genres, String path,String pathImage, UUID ownerId, String author, String description, LocalDate registrationDate, boolean isPublic) {
        this.id = UUID.randomUUID();
        setName(name);
        setGenres(genres);
        setPath(path);
        setPathImage(pathImage);
        this.ownerId = Objects.requireNonNull(ownerId);
        setAuthor(author);
        this.description = description;
        this.registrationDate = Objects.requireNonNull(registrationDate);
        this.isPublic = isPublic;
        this.playCount = 0;
    }
    public Song(){
        //this.id = UUID.randomUUID();
        this.id =null;
        this.name="";
        this.genres = new ArrayList<>();
        this.registrationDate= LocalDate.now();
        this.path="";
        this.pathImage="";
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Song name cannot be null or blank");
        this.name = name;
    }

    public void setGenres(List<Genre> genres) {
        if (genres == null ) throw new IllegalArgumentException("Genres cannot be null or empty");
        this.genres = genres;
    }

    public void setPath(String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be null or blank");
        this.path = path;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) throw new IllegalArgumentException("Author cannot be null or blank");
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getPath() {
        return path;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
    public void setId(UUID id){
        this.id=id;
    }
    public UUID getId() { return id; }
    public void play() { playCount++; }
    public void like(UUID userId) { likes.add(new Reaction(userId, LocalDate.now())); }
    public void dislike(UUID userId) { dislikes.add(new Reaction(userId, LocalDate.now())); }
    public boolean isPublic() { return isPublic; }

    public List<Reaction> getLikes() {
        return Collections.unmodifiableList(likes);
    }

    public void setLikes(List<Reaction> likes) {
        this.likes = likes;
    }

    public List<Reaction> getDislikes() {
        return Collections.unmodifiableList(dislikes);
    }

    public void setDislikes(List<Reaction> dislikes) {
        this.dislikes = dislikes;
    }
}