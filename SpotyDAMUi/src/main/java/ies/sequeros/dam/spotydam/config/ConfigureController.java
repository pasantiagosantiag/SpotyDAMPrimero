package ies.sequeros.dam.spotydam.config;




import ies.sequeros.dam.spotydam.navegacion.AWindows;
import ies.sequeros.dam.spotydam.utils.Configuration;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigureController extends AWindows  {


    @FXML
    private TextField userjsonpathField;
    @FXML
    private TextField sonsjsonpathField;
    @FXML
    private TextField playlistsjsonpathField;
    @FXML
    private
     TextField rutaImagenesField;
    @FXML
    private TextField rutaCancionesField;
    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;


    public ConfigureController() {

    }


    @FXML
    public void initialize() {
        this.rutaCancionesField.textProperty().bindBidirectional(Configuration.getInstancia().songsPathProperty());
        this.rutaImagenesField.textProperty().bindBidirectional(Configuration.getInstancia().imagesPathProperty());
        this.playlistsjsonpathField.textProperty().bindBidirectional(Configuration.getInstancia().playListJsonPathProperty());
        this.sonsjsonpathField.textProperty().bindBidirectional(Configuration.getInstancia().sonsJsongsPathProperty());
        this.userjsonpathField.textProperty().bindBidirectional(Configuration.getInstancia().userJsonPathProperty());
        this.guardarBtn.setOnMouseClicked(mouseEvent -> {
            Configuration.getInstancia().guardar();
            this.router.pop();
        });
        this.cancelarBtn.setOnMouseClicked(mouseEvent -> {
            this.router.pop();
        });


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