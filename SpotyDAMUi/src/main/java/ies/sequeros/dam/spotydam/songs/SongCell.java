package ies.sequeros.dam.spotydam.songs;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.playlist.PlayListsViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.beans.property.ListProperty;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class SongCell extends GridCell<Song> {

    private Label label;
    private VBox vbox;
    private ImageView imageView;
    private Label titleLabel;
    private Consumer<Song> onView;
    private Consumer<Song> ondelete;
    private Consumer<Song> onedit;
    private Consumer<Song> onplay;
    private BiConsumer<Song,PlayList> onAddSongToPlayList;
    private PopOver popAddList;
    private JFXPopup popup;
    private Label descLabel;
    private ComboBox<PlayList> combobox;
    private FontIcon deleteIcon, viewIcon, editIcon,addToListIcon;
    private FontIcon playIcon;
    private MFXButton playBtn,editBtn,viewBtn,deleteBtn, addToListBtn;
    private ListProperty<PlayList> playLists;
    private SongsViewModel viewModel;
    private PlayListsViewModel playListsModel;
    public SongCell(SongsViewModel songsViewModel, PlayListsViewModel playListsViewModel) {
        super();
        this.viewModel = songsViewModel;
        this.playListsModel = playListsViewModel;
        vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(200);
        setStyle("-fx-background-color: #ffffff;");
        vbox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

        // Imagen
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/No_image.png"), 1120, 120, true, true));
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        // Título
        titleLabel = new Label("");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Descripción
        descLabel = new Label("");
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #666666;");

        // Botones
        viewBtn = new MFXButton("");
        viewIcon = new FontIcon("fa-eye");//new MFXFontIcon("fas-eye", 12);
        viewIcon.setIconSize(12);
        viewBtn.setGraphic(viewIcon);


        editBtn = new MFXButton("");
        editIcon = new FontIcon("fa-pencil");//;new MFXFontIcon("fas-pencil", 12);
        editIcon.setIconSize(12);
        editBtn.setGraphic(editIcon);


        addToListBtn = new MFXButton("");
        addToListIcon = new FontIcon("fa-chain");
        addToListIcon.setIconSize(12);//new MFXFontIcon("fas-trash", 12);
        addToListBtn.setGraphic(addToListIcon);
        addToListBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        addToListBtn.setMouseTransparent(false);
        addToListBtn.setStyle(null);

        deleteBtn = new MFXButton("");
        deleteIcon = new FontIcon("fa-trash");
        deleteIcon.setIconSize(12);//new MFXFontIcon("fas-trash", 12);
        deleteBtn.setGraphic(deleteIcon);


        deleteBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        deleteBtn.setMouseTransparent(false);
        deleteBtn.setStyle(null);


        playBtn = new MFXButton("");
        playIcon = new FontIcon("fa-play");
        playIcon.setIconSize(12);
        playBtn.setGraphic(new FontIcon("fa-play"));


        playBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        playBtn.setMouseTransparent(false);
        playBtn.setStyle(null);
        HBox buttonBox = new HBox(5, playBtn, viewBtn, editBtn, deleteBtn, addToListBtn);

        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(imageView, titleLabel, descLabel, buttonBox);
        //esto es una chapuza, pero no funciona de ninguna otra forma

        this.setOnMouseClicked(mouseEvent -> {

            var t = mouseEvent.getPickResult().getIntersectedNode();
            if (t instanceof FontIcon) {
                FontIcon tempoicon = (FontIcon) t;
                Song c = getItem();
                if (tempoicon == this.deleteIcon && this.ondelete != null) {

                    this.ondelete.accept(getItem());
                }
                if (tempoicon == this.editIcon && this.onedit != null) {
                    this.onedit.accept(getItem());
                }
                if (tempoicon == this.viewIcon && this.onView != null) {
                    this.onView.accept(c);
                }
                if(tempoicon==this.addToListIcon ){
                    popup.show(this.addToListBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
                   // this.popAddList.show (this.addToListBtn);
                }
                if (tempoicon.getIconLiteral().equals("fa-play") && this.onplay != null) {
                    this.onplay.accept(c);
                    ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-stop-circle:12");

                } else if (tempoicon.getIconLiteral().equals("fa-stop-circle") && this.onplay != null) {
                    this.onplay.accept(c);

                    ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");

                }


            }
        });
this.createPopup();
    }

    public void stop(){
        ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");
    }
    public void setOnView(Consumer<Song> onView) {
        this.onView = onView;

    }

    public BiConsumer<Song, PlayList> getOnAddSongToPlayList() {
        return onAddSongToPlayList;
    }

    public void setOnAddSongToPlayList(BiConsumer<Song, PlayList> onAddSongToPlayList) {
        this.onAddSongToPlayList = onAddSongToPlayList;
    }

    public void setOnDelete(Consumer<Song> onDelete) {
        this.ondelete = onDelete;
    }

    public void setOnPlay(Consumer<Song> onPlay) {
        this.onplay = onPlay;
    }

    public void setOnEdit(Consumer<Song> onEdit) {
        this.onedit = onEdit;
    }
    public void setOnAddPlayList(BiConsumer<Song, PlayList> onAddPlayList) {
        this.onAddSongToPlayList=onAddPlayList;
    }
    public void setPlayLists(ListProperty<PlayList> playLists) {
        this.playLists=playLists;
    }
    protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            vbox.setAlignment(Pos.CENTER);
            this.descLabel.setText(item.getAuthor());
            this.titleLabel.setFont(Font.font("Segoe UI Emoji", 16));
            this.titleLabel.setText(item.getName() );
            if (Files.exists(Path.of(item.getPathImage())))
                this.imageView.setImage(new Image(Path.of(item.getPathImage()).toUri().toString(), 1120, 120, true, true));
            setGraphic(this.vbox); // Mostrar la imagen en la celda
            combobox.setItems(new FilteredList<PlayList>(
                    //falta mirar que es el propietario
                    this.playListsModel.getPlayListsProperty(), playlist->{
                if(!playlist.getSongIds().contains(getItem().getId()))
                    return true;
                else
                    return false;
            }));
        }
    }
    protected void createPopup(){
        JFXButton button = new JFXButton("Formulario");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));


        combobox=new ComboBox<>();
        //factorias para el combobox
        combobox.setCellFactory( lv -> new ListCell<>() {
            @Override
            protected void updateItem(PlayList item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PlayList item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        combobox.setItems(new FilteredList<PlayList>(
                //falta mirar que es el propietario
                this.playListsModel.getPlayListsProperty(), playlist->{
                    if(!playlist.getSongIds().contains(getItem().getId()))
                        return true;
                    else
                        return false;
        }));


        JFXButton submitButton = new JFXButton("Add");
        submitButton.setOnAction(e -> {
            if(this.onAddSongToPlayList!=null) {
                this.onAddSongToPlayList.accept(getItem(), combobox.getValue());
            }
            popup.hide();
        });
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setOnAction(e -> {

            popup.hide();
        });
submitButton.disableProperty().bind(combobox.getSelectionModel().selectedItemProperty().isNull());
        form.getChildren().addAll(new Label("Formulario"), combobox, submitButton,cancelButton);

        popup = new JFXPopup();
        popup.setPopupContent(form);
    }
}