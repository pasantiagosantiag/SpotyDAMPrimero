package ies.sequeros.dam.spotydam.songs;


import ies.sequeros.dam.spotydam.application.song.*;
import ies.sequeros.dam.spotydam.domain.model.Song;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.UUID;

public class SongsViewModel {
    //listado observable
    private ListProperty<Song> items;
    //Song actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Song> current;
    //modo edicion o no
    private BooleanProperty editMode;
    //casos de uso
    private AddSongUseCase addSongUseCase;
    private DeleteSongUseCase deleteSongUseCase;
    private GetSongByIdUseCase getSongByIdUseCase;
    private ListAllSongsUseCase listAllSongUseCase;

    private UpdateSongUseCase updateSongUseCase;


    public SongsViewModel(AddSongUseCase addSongUseCase, UpdateSongUseCase updateSongUseCase,
                          DeleteSongUseCase deleteSongUseCase, GetSongByIdUseCase getSongByIdUseCase
            , ListAllSongsUseCase listAllSongUseCase) {


        this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.editMode = new SimpleBooleanProperty(false);
        this.current = new SimpleObjectProperty<>(new Song());
        this.addSongUseCase = addSongUseCase;
        this.getSongByIdUseCase = getSongByIdUseCase;
        this.deleteSongUseCase = deleteSongUseCase;
        this.getSongByIdUseCase = getSongByIdUseCase;
        this.listAllSongUseCase = listAllSongUseCase;
        this.updateSongUseCase = updateSongUseCase;

        this.load();


    }

    public void load() {
        if (this.listAllSongUseCase != null) {
            this.items.clear();
            this.items.addAll(this.listAllSongUseCase.execute());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.items.clear();
        this.load();
    }

    public void addSong(Song item) throws NoSuchFieldException, IOException {
        if (this.addSongUseCase != null) {
            this.addSongUseCase.execute(item);
            this.items.add(item);
            this.current.set(new Song());
            this.current.set(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeSong(Song item) throws NoSuchFieldException, IOException {
        if (this.deleteSongUseCase != null) {
            this.deleteSongUseCase.execute(item);
            this.items.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");


    }

    public boolean isEditMode() {
        return editMode.get();
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }

    public ListProperty<Song> getSongsProperty() {
        return this.items;
    }

    public Song getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<Song> currentProperty() {
        return this.current;
    }

    public void setCurrent(Song item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new Song());
    }

    /**
     * si el id es 0 significa que es nuevo
     *
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && this.current.get().getId() == null) {
            this.current.get().setId(UUID.randomUUID());
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addSong(this.current.get());

        } else {
            //para indicar que se ha actualizado
            if (this.updateSongUseCase != null) {
                this.updateSongUseCase.execute(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            }
        }
    }

    public void setEmptyCurrent() {
        this.current.set(new Song());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new Song());
        ObservableList<Song> oldList = FXCollections.observableArrayList(this.items);
        this.items.clear();
        this.items.addAll(oldList);
    }

}
