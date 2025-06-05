package ies.sequeros.dam.spotydam.songs;


import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.navegacion.AWindows;

import ies.sequeros.dam.spotydam.utils.AppViewModel;
import ies.sequeros.dam.spotydam.utils.MusicPlayerViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class SongController extends AWindows {
    public Button playBtn;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField;
    @FXML
    private Button songBtn;
    @FXML
    private TextField pathSongField;
    @FXML
    private TextField autorField;
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
    private SongsViewModel songsViewModel;
    private AppViewModel appViewModel;
    private MusicPlayerViewModel playerViewModel;
    private ChangeListener<Song> escuchadorViewModel;

    public SongController() {

    }

    public void setViewModels(SongsViewModel songsViewModel, AppViewModel appViewModel, MusicPlayerViewModel musicPlayerViewModel) {
        this.songsViewModel = songsViewModel;
        this.appViewModel = appViewModel;
        this.playerViewModel = musicPlayerViewModel;
        this.setEditionMode();
          this.configValidation();
        this.escuchadorViewModel = (observableValue, item, newValue) -> {

            //reset para campos especiales
            this.pathImagenField.setText("");
            this.pathSongField.setText("");
            //dar valores
            if (this.songsViewModel.currentProperty().get().getId() == null) {
                this.titulo.setText("New Song");
            } else {
                this.titulo.setText("Update Song");
            }
            this.nameField.setText(newValue.getName());
            this.description.setText(newValue.getDescription());
            this.autorField.setText(newValue.getAuthor());
            this.isPublic.setSelected(newValue.isPublic());
            if (!newValue.getPathImage().isBlank() && Files.exists(Path.of(newValue.getPathImage()))) {
                this.pathImagenField.setText(newValue.getPathImage());
                this.imageView.setImage(new Image(String.valueOf(Path.of(newValue.getPathImage()).toUri().toString()), 180, 120, true, true));

            } else {
                this.imageView.setImage(new Image(getClass().getResourceAsStream("/images/No_Image_Available.jpg"), 180, 120, true, true));
            }
            this.pathSongField.setText(newValue.getPath());

        };
        this.songsViewModel.currentProperty().addListener(this.escuchadorViewModel);
    }

    private void setEditionMode() {
        this.imagenBtn.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.songBtn.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.pathImagenField.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.guardarBtn.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.nameField.editableProperty().bind(this.songsViewModel.editModeProperty());
        this.description.editableProperty().bind(this.songsViewModel.editModeProperty());
        this.autorField.editableProperty().bind(this.songsViewModel.editModeProperty());
        this.pathSongField.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.pathImagenField.visibleProperty().bind(this.songsViewModel.editModeProperty());
        this.isPublic.disableProperty().bind(this.songsViewModel.editModeProperty().not());
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

        songBtn.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select song");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("songs", "*.mp3")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {

                this.pathSongField.textProperty().set(file.getAbsolutePath());
                this.playerViewModel.setSong(file.getAbsolutePath());
                //por si se está reproduciendo uno
                ((FontIcon)this.playBtn.getGraphic()).setIconLiteral("fa-play:24");

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
        playBtn.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> this.pathSongField.getText().isBlank(),
                        this.pathSongField.textProperty()
                )
        );
        playBtn.setOnMouseClicked(mouseEvent -> {
            if(this.playerViewModel.getStatus()== MediaPlayer.Status.PLAYING) {
                this.playerViewModel.pause();
                ((FontIcon)this.playBtn.getGraphic()).setIconLiteral("fa-play:24");
            }
            else {
                this.playerViewModel.play();
                ((FontIcon)this.playBtn.getGraphic()).setIconLiteral("fa-stop-circle:24");
            }
        });


    }

    private void configValidation() {
        validationSupport = new ValidationSupport();
        validationSupport.redecorate();
        validationSupport.registerValidator(nameField,
                Validator.createEmptyValidator("Name is required"));
        validationSupport.registerValidator(description,
                Validator.createEmptyValidator("Description is required"));
        validationSupport.registerValidator(autorField,
                Validator.createEmptyValidator("Author is requiered"));
        validationSupport.registerValidator(this.pathImagenField,
                Validator.createEmptyValidator("Image is required"));
        validationSupport.registerValidator(this.pathSongField,
                Validator.createEmptyValidator("Song is required"));


    }

    private void save() {
        //si es nuevo se asigna a usuario actual
        if(this.songsViewModel.currentProperty().get().getOwnerId()==null)
            this.songsViewModel.currentProperty().get().setOwnerId(this.appViewModel.getUser().get().getId());
        //solo se puede modificar si eres el propietario
        if( this.songsViewModel.currentProperty().get().getOwnerId().equals(this.appViewModel.getUser().get().getId())) {
            this.songsViewModel.currentProperty().get().setName(this.nameField.getText());
            this.songsViewModel.currentProperty().get().setDescription(this.description.getText());
            this.songsViewModel.currentProperty().get().setPathImage(this.pathImagenField.getText());
            this.songsViewModel.currentProperty().get().setPath(this.pathSongField.getText());
            this.songsViewModel.currentProperty().get().setPublic(this.isPublic.isSelected());
            this.songsViewModel.currentProperty().get().setAuthor(this.autorField.getText());


            try {
                this.songsViewModel.saveCurrent();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());

            }
            //se para la música
            this.playerViewModel.stop();
            this.router.pop();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        if(this.playerViewModel!=null)
            this.playerViewModel.stop();

    }

    @Override
    public void reset() {

    }
}