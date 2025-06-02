package ies.sequeros.dam.spotydam.domain.model;

import java.time.LocalDate;
import java.util.*;

public class User {
    private UUID id;
    private String name;
    private String nickname;
    private String image;
    private Status status;
    private LocalDate registrationDate;
    private Role role;
    private String password;
    private List<UUID> ownPlaylists = new ArrayList<>();
    private List<UUID> otherPlaylists = new ArrayList<>();
    private List<UUID> lastPlayedSongs = new ArrayList<>();

    public enum Status { ACTIVE, INACTIVE }
    public enum Role { ADMIN, USER }

    public User(String name, String nickname, String image, Status status, LocalDate registrationDate, Role role) {
        this.id = UUID.randomUUID();
        setName(name);
        setNickname(nickname);
        this.image = image;
        //Objects.requireNonNull(status, "Status cannot be null"),  lanza una excepcion si el valor es nulo de  tipo excepción NullPointerException inmediatamente.
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.registrationDate = Objects.requireNonNull(registrationDate, "Registration date cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
    }
    public User(){
        this.name="";
        this.nickname="";
        this.password="";
        this.image="";
        this.status=Status.INACTIVE;
        this.role=Role.USER;
        this.registrationDate=LocalDate.now();

    }

    public void addOwnPlaylist(UUID playlistId) {
        ownPlaylists.add(Objects.requireNonNull(playlistId));
    }

    public void addOtherPlaylist(UUID playlistId) {
        otherPlaylists.add(Objects.requireNonNull(playlistId));
    }

    public void addLastPlayedSong(UUID songId) {
        lastPlayedSongs.add(0, Objects.requireNonNull(songId));
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) throw new IllegalArgumentException("Nickname cannot be null or blank");
        this.nickname = nickname;
    }

    public String getImage() {return image;}

    public void setOtherPlaylists(List<UUID> otherPlaylists) {this.otherPlaylists = otherPlaylists;}

    public void setLastPlayedSongs(List<UUID> lastPlayedSongs) {this.lastPlayedSongs = lastPlayedSongs;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public void setId(UUID id) {this.id = id;}

    public void setRole(Role role) {this.role = role;}

    public void setRegistrationDate(LocalDate registrationDate) {this.registrationDate = registrationDate;}

    public void setStatus(Status status) {this.status = status;}

    public void setOwnPlaylists(List<UUID> ownPlaylists) {this.ownPlaylists = ownPlaylists;}

    public void setImage(String image) {this.image = image;}

    public LocalDate getRegistrationDate() {return registrationDate;}

    public List<UUID> getOwnPlaylists() {return Collections.unmodifiableList(ownPlaylists);}

    public List<UUID> getOtherPlaylists() {return Collections.unmodifiableList(otherPlaylists);}

    public List<UUID> getLastPlayedSongs() {return Collections.unmodifiableList(lastPlayedSongs);}

    public String getName() { return name; }
    public String getNickname() { return nickname; }
    public Status getStatus() { return status; }
    public Role getRole() { return role; }
    public UUID getId() { return id; }
}