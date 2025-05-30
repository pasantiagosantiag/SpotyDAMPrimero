package ies.sequeros.dam.spotydam.domain.model;

import java.time.LocalDate;
import java.util.*;

public class PlayList {
    private  UUID id;
    private String name;
    private UUID ownerId;
    private String description;
    private List<UUID> songIds = new ArrayList<>();
    private LocalDate creationDate;
    private List<Reaction> likes = new ArrayList<>();
    private List<Reaction> dislikes = new ArrayList<>();

    public PlayList(String name, UUID ownerId, String description, LocalDate creationDate) {
        this.id = UUID.randomUUID();
        setName(name);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.description = description;
        this.creationDate = Objects.requireNonNull(creationDate);
    }
    public PlayList(){

    }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Playlist name cannot be null or blank");
        this.name = name;
    }
    public void addSong(UUID songId) {
        songIds.add(Objects.requireNonNull(songId));
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

    public List<UUID> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<UUID> songIds) {
        this.songIds = songIds;
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
}
