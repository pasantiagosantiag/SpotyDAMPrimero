package ies.sequeros.dam.spotydam.application.playlist.model;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Reaction;
import ies.sequeros.dam.spotydam.domain.model.Song;

import java.time.LocalDate;
import java.util.*;

public class PlayListWithSongs{
    private  UUID id;
    private String name;
    private UUID ownerId;
    private String description;
    private String image;
    private boolean isPublic;
    private List<Song> songs = new ArrayList<>();
    private LocalDate creationDate;
    private List<Reaction> likes = new ArrayList<>();
    private List<Reaction> dislikes = new ArrayList<>();

    public PlayListWithSongs(String name, UUID ownerId, String description, LocalDate creationDate, String image, Boolean isPublic) {
        this.id = UUID.randomUUID();
        setName(name);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.description = description;
        this.creationDate = Objects.requireNonNull(creationDate);
        this.image = image;
        this.isPublic = isPublic;
    }
    public PlayListWithSongs(){

    }
    public PlayListWithSongs(PlayList pl){
        this.id = UUID.randomUUID();
        setName(pl.getName());
        this.ownerId = pl.getOwnerId();
        this.description = pl.getDescription();
        this.creationDate= pl.getCreationDate();
        this.isPublic= pl.isPublic();
        this.image=pl.getImage();
       this.dislikes.addAll(pl.getDislikes());
       this.likes.addAll(pl.getLikes());
    }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Playlist name cannot be null or blank");
        this.name = name;
    }
    public void addSong(Song song) {
        songs.add(Objects.requireNonNull(song));
    }

    public void like(UUID userId) { likes.add(new Reaction(userId, LocalDate.now())); }
    public void dislike(UUID userId) { dislikes.add(new Reaction(userId, LocalDate.now())); }
    public UUID getId() { return id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(this.songs);
    }

    public void setSongs(List<Song> songs) {
        this.songs.clear();
        this.songs.addAll(songs);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

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

    public String getImage() {
        return image;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public PlayList toPlayList(){
        PlayList pl = new PlayList();
        pl.setName(getName());
        pl.setOwnerId(getOwnerId());
        pl.setDescription(getDescription());
        pl.setCreationDate(getCreationDate());
        pl.setImage(getImage());
        pl.setPublic(isPublic());
        pl.setDislikes(getDislikes());
        pl.setLikes(getLikes());
        for(Song s: getSongs()){
            pl.addSong(s.getId());
        }
        return pl;
    }
}
