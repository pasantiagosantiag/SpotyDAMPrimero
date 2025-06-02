package ies.sequeros.dam.spotydam.users;



import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ies.sequeros.dam.spotydam.domain.model.User;
import org.controlsfx.control.GridCell;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;


public class UserCell extends GridCell<User> {

    private Label label;
    private VBox vbox;
    private ImageView imageView;
    private Label titleLabel;
    private Consumer<User> onView;
    private Consumer<User> ondelete;
    private Consumer<User> onedit;

    Label descLabel;
    MFXButton viewBtn;
    MFXFontIcon deleteIcon, viewIcon, editIcon;

    public UserCell() {

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
        viewIcon=new MFXFontIcon("fas-eye", 24);
        viewBtn.setGraphic(viewIcon);


        MFXButton editBtn = new MFXButton("");
        editIcon=new MFXFontIcon("fas-pencil", 24);
        editBtn.setGraphic(editIcon);


        MFXButton deleteBtn = new MFXButton("");
        deleteIcon = new MFXFontIcon("fas-trash", 24);

        deleteBtn.setGraphic(deleteIcon);


        deleteBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        deleteBtn.setMouseTransparent(false);
        deleteBtn.setStyle(null);
        HBox buttonBox = new HBox(5, viewBtn, editBtn, deleteBtn);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(imageView, titleLabel, descLabel, buttonBox);
        //esto es una chapuza, pero no funciona de ninguna otra forma
        this.setOnMouseClicked(mouseEvent -> {

            var t = mouseEvent.getPickResult().getIntersectedNode();
            if (t instanceof MFXFontIcon) {
                MFXFontIcon tempoicon = (MFXFontIcon) t;
                User c=getItem();
                if (tempoicon == this.deleteIcon && this.ondelete != null) {

                    this.ondelete.accept(getItem());
                }
                if (tempoicon == this.editIcon && this.onedit != null) {
                    this.onedit.accept(getItem());
                }
                if (tempoicon == this.viewIcon && this.onView != null) {
                    this.onView.accept(c);
                }
            }
        });

    }


    public void setOnView(Consumer<User> onView) {
        this.onView = onView;

    }

    public void setOnDelete(Consumer<User> onDelete) {
        this.ondelete = onDelete;
    }

    public void setOnEdit(Consumer<User> onEdit) {
        this.onedit = onEdit;
    }
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            vbox.setAlignment(Pos.CENTER);
            this.descLabel.setText( item.getNickname());
            this.titleLabel.setText(item.getNickname() + "("+item.getId()+")");
            if(Files.exists(Path.of(item.getImage())))
                this.imageView.setImage(new Image(Path.of(item.getImage()).toUri().toString(),180, 120, true, true));
            setGraphic(this.vbox); // Mostrar la imagen en la celda
        }
    }
}