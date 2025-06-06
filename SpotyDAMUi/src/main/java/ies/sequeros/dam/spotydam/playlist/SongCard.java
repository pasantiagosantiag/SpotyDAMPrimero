package ies.sequeros.dam.spotydam.playlist;

import com.jfoenix.controls.JFXPopup;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.songs.SongsViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.ListProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SongCard extends HBox {
    private Song song;
    private Label label;

    private ImageView imageView;
    private Label titleLabel;
    private Consumer<Song> onView;
    private Consumer<Song> ondelete;
    private Consumer<Song> onedit;
    private Consumer<Song> onplay;

    private Label descLabel;

    private FontIcon deleteIcon, viewIcon, editIcon,playIcon;
    private MFXButton playBtn,editBtn,viewBtn,deleteBtn;

   public SongCard(Song song) {
       super();
       this.config();
       this.song = song;
       this.setValueItem();
   }
   public SongCard(){
       super();
       this.config();


   }
   private void config(){

       this.setPadding(new Insets(10, 10, 10, 10));
       this.setSpacing(10);
       this.setAlignment(Pos.TOP_CENTER);
       this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
               BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
       this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

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


      /* playBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
       playBtn.setMouseTransparent(false);
       playBtn.setStyle(null);*/
       playBtn.setOnMouseClicked(mouseEvent -> {
           if(this.onplay!=null){
               if (playIcon.getIconLiteral().equals("fa-play")) {

                   ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-stop-circle:12");

               } else if (playIcon.getIconLiteral().equals("fa-stop-circle") ) {


                   ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");

               }
               this.onplay.accept(this.song);

           }
       });
       HBox buttonBox = new HBox(5, playBtn, viewBtn, editBtn, deleteBtn);

       buttonBox.setAlignment(Pos.CENTER);

       this.getChildren().addAll(imageView, titleLabel, descLabel, buttonBox);
   }
   public void setSong(Song song){
       this.song = song;
       this.setValueItem();
   }
   public Song getSong(){
       return song;
   }

    public void stop(){
        ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");
    }
    public void setOnView(Consumer<Song> onView) {
        this.onView = onView;

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


    private void setValueItem() {
        if(this.song!=null) {
            this.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            setAlignment(Pos.CENTER);
           descLabel.setText(this.song.getAuthor());
            titleLabel.setFont(Font.font("Segoe UI Emoji", 16));
            titleLabel.setText(this.song.getName());
            if (Files.exists(Path.of(this.song.getPathImage())))
                imageView.setImage(new Image(Path.of(this.song.getPathImage()).toUri().toString(), 1120, 120, true, true));
        }


    }

}
