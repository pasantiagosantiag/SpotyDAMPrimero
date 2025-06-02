package ies.sequeros.dam.spotydam.songs;


import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.model.Song;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import org.controlsfx.control.GridCell;
import org.kordamp.ikonli.javafx.FontIcon;

import java.nio.file.Files;
import java.nio.file.Path;
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

    Label descLabel;
    MFXButton viewBtn;
    FontIcon deleteIcon, viewIcon, editIcon;
    FontIcon playIcon;

    public SongCell() {

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
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/No_image.png"), 180, 120, true, true));
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
        viewIcon=new FontIcon("fa-eye");//new MFXFontIcon("fas-eye", 24);
        viewIcon.setIconSize(24);
        viewBtn.setGraphic(viewIcon);


        MFXButton editBtn = new MFXButton("");
        editIcon=new FontIcon("fa-pencil");//;new MFXFontIcon("fas-pencil", 24);
        editIcon.setIconSize(24);
        editBtn.setGraphic(editIcon);


        MFXButton deleteBtn = new MFXButton("");
        deleteIcon = new FontIcon("fa-trash");
        deleteIcon.setIconSize(24);//new MFXFontIcon("fas-trash", 24);

        deleteBtn.setGraphic(deleteIcon);


        deleteBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        deleteBtn.setMouseTransparent(false);
        deleteBtn.setStyle(null);



        MFXButton playBtn = new MFXButton("");
        playIcon =new FontIcon("fa-play");
        playIcon.setIconSize(24);

        playBtn.setGraphic(new FontIcon("fa-play"));


        playBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        playBtn.setMouseTransparent(false);
        playBtn.setStyle(null);
        HBox buttonBox = new HBox(5, playBtn,viewBtn, editBtn, deleteBtn);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(imageView, titleLabel, descLabel, buttonBox);
        //esto es una chapuza, pero no funciona de ninguna otra forma
        this.setOnMouseClicked(mouseEvent -> {

            var t = mouseEvent.getPickResult().getIntersectedNode();
            if (t instanceof FontIcon) {
               FontIcon tempoicon = (FontIcon) t;
                Song c=getItem();
                if (tempoicon == this.deleteIcon && this.ondelete != null) {

                    this.ondelete.accept(getItem());
                }
                if (tempoicon == this.editIcon && this.onedit != null) {
                    this.onedit.accept(getItem());
                }
                if (tempoicon == this.viewIcon && this.onView != null) {
                    this.onView.accept(c);
                }
                if(tempoicon == this.playIcon && this.onplay != null) {

                        FontIcon icon = ((FontIcon) playBtn.getGraphic());
                        if (icon.getIconLiteral().equals("fa-stop-circle:24"))
                            ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:24");
                        else
                            ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-stop-circle:24");

                        this.onplay.accept(c);

                }
            }
        });

    }


    public void setOnView(Consumer<Song> onView) {
        this.onView = onView;

    }

    public void setOnDelete(Consumer<Song> onDelete) {
        this.ondelete = onDelete;
    }
    public void setOnPlay(Consumer<Song> onPlay){
        this.onplay=onPlay;
    }

    public void setOnEdit(Consumer<Song> onEdit) {
        this.onedit = onEdit;
    }
    protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            vbox.setAlignment(Pos.CENTER);
            this.descLabel.setText( item.getName());
            this.titleLabel.setText(item.getLikes() + "("+item.getDislikes()+")");
            if(Files.exists(Path.of(item.getPathImage())))
                this.imageView.setImage(new Image(Path.of(item.getPathImage()).toUri().toString(),180, 120, true, true));
            setGraphic(this.vbox); // Mostrar la imagen en la celda
        }
    }
}