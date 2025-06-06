package ies.sequeros.dam.spotydam.playlist;


import ies.sequeros.dam.spotydam.application.playlist.model.PlayListWithSongs;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.navegacion.AWindows;
import ies.sequeros.dam.spotydam.songs.SongsViewModel;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import ies.sequeros.dam.spotydam.utils.MusicPlayerViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class PlayListController extends AWindows {
    @FXML
    private
     HBox galleria;

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField;

    @FXML
    private TextField description;
    @FXML
    private TextField pathImagenField;
    @FXML
    private Button imagenBtn;
    @FXML
    private CheckBox isPublic;
    @FXML
    private Text titulo;
    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;
    private ValidationSupport validationSupport;
    private PlayListsViewModel playListsViewModel;
    private AppViewModel appViewModel;
    private MusicPlayerViewModel playerViewModel;
    private ChangeListener<PlayListWithSongs> escuchadorViewModel;
    private ListProperty<Song> songList;
    private SongsViewModel songsViewModel;

    public PlayListController() {
        songList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public void setViewModels(PlayListsViewModel playListsViewModel, 
                              SongsViewModel songsViewModel,
                              AppViewModel appViewModel,
                              MusicPlayerViewModel musicPlayerViewModel) {
        this.playListsViewModel = playListsViewModel;
        this.appViewModel = appViewModel;
        this.playerViewModel = musicPlayerViewModel;
        this.songsViewModel= songsViewModel;
        this.setEditionMode();
        this.configValidation();
        this.escuchadorViewModel = (observableValue, item, newValue) -> {

            //reset para campos especiales
            this.pathImagenField.setText("");
            //dar valores
            if (this.playListsViewModel.currentProperty().get().getId() == null) {
                this.titulo.setText("New PlayList");
            } else {
                this.titulo.setText("Update PlayList");
            }
            this.nameField.setText(newValue.getName());
            this.description.setText(newValue.getDescription());

            this.isPublic.setSelected(newValue.isPublic());
            if (newValue.getImage()!=null && !newValue.getImage().isBlank() && Files.exists(Path.of(newValue.getImage()))) {
                this.pathImagenField.setText(newValue.getImage());
                this.imageView.setImage(new Image(String.valueOf(Path.of(newValue.getImage()).toUri().toString()), 180, 120, true, true));

            } else {
                this.imageView.setImage(new Image(getClass().getResourceAsStream("/images/No_Image_Available.jpg"), 180, 120, true, true));
            }
            this.pathImagenField.setText(newValue.getImage());
            this.songList.clear();;
            this.songList.addAll(FXCollections.observableArrayList(newValue.getSongs()));
            this.galleria.getChildren().clear();
            for(Song song : songList) {
                SongCard sc= new SongCard(song);
                sc.setOnPlay(song1 -> {
                    this.playerViewModel.stop();

                        if( this.playerViewModel.currentTrackProperty().get()==null || !this.playerViewModel.currentTrackProperty().get().equals(song1.getPath()))
                            this.playerViewModel.setSong(song.getPath());
                        var status=this.playerViewModel.getStatus();
                       // disablePlayButtonfromGrid();
                        if(this.playerViewModel.getStatus()== MediaPlayer.Status.READY || this.playerViewModel.getStatus()== MediaPlayer.Status.UNKNOWN || this.playerViewModel.getStatus()== MediaPlayer.Status.PAUSED || this.playerViewModel.getStatus()== MediaPlayer.Status.STOPPED)
                            this.playerViewModel.play();
                        else
                            this.playerViewModel.pause();
                    });
                   // musicPlayerViewModel.play(song1.getPath());
                //});
                sc.setOnView(song1 -> {
                    this.songsViewModel.setCurrent(song1);
                    this.router.push("song");
                });
                sc.setOnDelete(song1 -> {
                   // this.playListsViewModel.
                });
                this.galleria.getChildren().add(sc);
                
            }

        };
        this.playListsViewModel.currentProperty().addListener(this.escuchadorViewModel);
    }

    private void setEditionMode() {
        this.imagenBtn.visibleProperty().bind(this.playListsViewModel.editModeProperty());
        this.pathImagenField.visibleProperty().bind(this.playListsViewModel.editModeProperty());
        this.guardarBtn.visibleProperty().bind(this.playListsViewModel.editModeProperty());
        this.nameField.editableProperty().bind(this.playListsViewModel.editModeProperty());
        this.description.editableProperty().bind(this.playListsViewModel.editModeProperty());
        this.pathImagenField.visibleProperty().bind(this.playListsViewModel.editModeProperty());
        this.isPublic.disableProperty().bind(this.playListsViewModel.editModeProperty().not());
    }

    @FXML
    public void initialize() {

        this.configValidation();

        imagenBtn.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("images", "*.jpg", "*.jpeg", "*.png")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // this.imageView.setImage(new Image(file.toURI().toString()));
                this.pathImagenField.textProperty().set(file.getAbsolutePath());
                this.imageView.setImage(new Image(file.toURI().toString()));
            }
        });



        // Desactivar botón hasta que sea válido
        guardarBtn.disableProperty().bind(validationSupport.invalidProperty());
        guardarBtn.setOnAction(e -> {
            this.save();

        });
        this.cancelarBtn.setOnMouseClicked(event -> {

            this.router.pop();
        });







    }

    private void configValidation() {
        validationSupport = new ValidationSupport();
        validationSupport.redecorate();
        validationSupport.registerValidator(nameField,
                Validator.createEmptyValidator("Name is required"));
        validationSupport.registerValidator(description,
                Validator.createEmptyValidator("Description is required"));
        validationSupport.registerValidator(this.pathImagenField,
                Validator.createEmptyValidator("Image is required"));



    }

    private void save() {
        //si es nuevo se asigna a usuario actual
        if (this.playListsViewModel.currentProperty().get().getOwnerId() == null)
            this.playListsViewModel.currentProperty().get().setOwnerId(this.appViewModel.getUser().get().getId());
        //solo se puede modificar si eres el propietario
       // if (this.playListsViewModel.currentProperty().get().getOwnerId().equals(this.appViewModel.getUser().get().getId())) {
            this.playListsViewModel.currentProperty().get().setName(this.nameField.getText());
            this.playListsViewModel.currentProperty().get().setDescription(this.description.getText());
            this.playListsViewModel.currentProperty().get().setImage(this.pathImagenField.getText());
            this.playListsViewModel.currentProperty().get().setPublic(this.isPublic.isSelected());
            try {
                this.playListsViewModel.saveCurrent();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());

            }
            this.router.pop();

        }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }


}


