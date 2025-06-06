package ies.sequeros.dam.spotydam.playlist;


import ies.sequeros.dam.spotydam.application.playlist.*;
import ies.sequeros.dam.spotydam.application.playlist.model.PlayListWithSongs;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.IOException;
import java.util.UUID;

public class PlayListsViewModel {
    private final GetPlayListWithtSongsUseCase getPlayListWithSongsUseCase;
    //listado observable
    private ListProperty<PlayList> items;
    //PlayList actual, para los borrados, altas y modificaciones
    private final SimpleObjectProperty<PlayListWithSongs> current;
    //modo edicion o no
    private final BooleanProperty editMode;
    //casos de uso
    private final AddPlayListUseCase addPlayListUseCase;
    private final DeletePlayListUseCase deletePlayListUseCase;
    private final ListAllPlayListByUserUseCase listAllPlayListByUserUseCase;
    private AddSongToPlayListUseCase addSongToPlayListUseCase;
    private final UpdatePlayListUseCase updatePlayListUseCase;
    private AppViewModel appViewModel;

    public PlayListsViewModel(AddPlayListUseCase addPlayListUseCase, UpdatePlayListUseCase updatePlayListUseCase,
                              DeletePlayListUseCase deletePlayListUseCase, AddSongToPlayListUseCase addSongToPlayListUseCase
            , ListAllPlayListByUserUseCase listAllPlayListByUserUseCase,AppViewModel appViewModel,GetPlayListWithtSongsUseCase getPlayListWithtSongsUseCase) {

this.appViewModel=appViewModel;
        this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.editMode = new SimpleBooleanProperty(false);
        this.current = new SimpleObjectProperty<>(new PlayListWithSongs());
        this.addPlayListUseCase = addPlayListUseCase;
        this.getPlayListWithSongsUseCase= getPlayListWithtSongsUseCase;
        this.deletePlayListUseCase = deletePlayListUseCase;
        this.addSongToPlayListUseCase = addSongToPlayListUseCase;
        this.listAllPlayListByUserUseCase = listAllPlayListByUserUseCase;
        this.updatePlayListUseCase = updatePlayListUseCase;

        this.load();


    }

    public void load() {
        if (this.listAllPlayListByUserUseCase != null) {
            this.items.clear();
            this.items.addAll(this.listAllPlayListByUserUseCase.execute(appViewModel.getUser().get()));
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.items.clear();
        this.load();
    }
    public void addSongToPlayList(PlayList playList,Song song) {
        this.addSongToPlayListUseCase.execute(playList,song);
    }

    public void addPlayList(PlayListWithSongs item) throws NoSuchFieldException, IOException {
        if (this.addPlayListUseCase != null) {
            this.addPlayListUseCase.execute(item);
            this.items.add(item.toPlayList());
            this.current.set(new PlayListWithSongs());
            this.current.set(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removePlayList(PlayList item) throws NoSuchFieldException, IOException {
        if (this.deletePlayListUseCase != null) {
            this.deletePlayListUseCase.execute(item);
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

    public ListProperty<PlayList> getPlayListsProperty() {
        return this.items;
    }

    public PlayListWithSongs getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<PlayListWithSongs> currentProperty() {
        return this.current;
    }

    public void setCurrent(PlayList item) {
        var playlistwithsong=this.getPlayListWithSongsUseCase.execute(item.getId());
        this.current.set(playlistwithsong);
    }

    public void clearCurrent() {
        this.current.set(new PlayListWithSongs());
    }

    /**
     * si el id es 0 significa que es nuevo
     *
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && this.current.get().getId() == null) {
            this.current.get().setId(UUID.randomUUID());
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addPlayList(this.current.get());

        } else {
            //para indicar que se ha actualizado
            if (this.updatePlayListUseCase != null) {
                this.updatePlayListUseCase.execute(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            }
        }
    }

    public void setEmptyCurrent() {
        this.current.set(new PlayListWithSongs());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new PlayListWithSongs());
        ObservableList<PlayList> oldList = FXCollections.observableArrayList(this.items);
        this.items.clear();
        this.items.addAll(oldList);
    }
    public FilteredList<PlayList> getPlayListwithNoSongAndIsProperty(Song s, User u) {
        return new FilteredList<>(this.items, playList -> {
            //falta el usuario
            return !playList.getSongIds().contains(s.getId());
        });
    }

}
