package ies.sequeros.dam.spotydam.users;


import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.navegacion.AWindows;
import ies.sequeros.dam.spotydam.navegacion.Router;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class UserController extends AWindows {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField aliasField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField retrypasswordField;
    @FXML
    private TextField pathImagenField;
    @FXML
    private Button imagenBtn;
    @FXML
    private CheckBox admnistradorCheck;
    @FXML
    private Text titulo;
    @FXML
    private TextField nombreField;

    @FXML
    private CheckBox activoCheck;

    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;
    private ValidationSupport validationSupport;
    private UsersViewModel usersViewModel;
    private ChangeListener<User> escuchadorViewModel;

    public UserController() {


    }

    public void setViewModel(UsersViewModel usersViewModel) {

        this.usersViewModel = usersViewModel;
        this.setEditionMode();
      //  this.configValidationMode();
        this.escuchadorViewModel = (observableValue, item, newValue) -> {

            //reset para campos especiales
            this.passwordField.setText("");
            this.retrypasswordField.setText("");
            this.pathImagenField.setText("");
            //dar valores
            if (this.usersViewModel.currentProperty().get().getId() == null) {
                this.titulo.setText("Add user");
            } else {
                if(this.usersViewModel.editModeProperty().get())
                    this.titulo.setText("Update User");
                else
                    this.titulo.setText("");
            }
            this.nombreField.setText(newValue.getName());

            this.aliasField.setText(newValue.getNickname());
            if (newValue.getRole() == User.Role.USER) {
                this.admnistradorCheck.setSelected(false);
            } else {
                this.admnistradorCheck.setSelected(true);
            }
            if (newValue.getStatus() == User.Status.ACTIVE) {
                this.activoCheck.setSelected(true);
            } else {
                this.activoCheck.setSelected(false);
            }
            if (!newValue.getImage().isBlank() && Files.exists(Path.of(newValue.getImage()))) {
                this.pathImagenField.setText(newValue.getImage());
                this.imageView.setImage(new Image(String.valueOf(Path.of(newValue.getImage()).toUri().toString()), 180, 120, true, true));


            } else {
                this.imageView.setImage(new Image(getClass().getResourceAsStream("/images/No_Image_Available.jpg"), 180, 120, true, true));
            }


        };
        this.usersViewModel.currentProperty().addListener(this.escuchadorViewModel);
    }

    private void setEditionMode() {
        this.imagenBtn.visibleProperty().bind(this.usersViewModel.editModeProperty());
        this.pathImagenField.visibleProperty().bind(this.usersViewModel.editModeProperty());
        this.guardarBtn.visibleProperty().bind(this.usersViewModel.editModeProperty());
        this.nombreField.editableProperty().bind(this.usersViewModel.editModeProperty());
        this.aliasField.editableProperty().bind(this.usersViewModel.editModeProperty());
        this.passwordField.editableProperty().bind(this.usersViewModel.editModeProperty());
        this.retrypasswordField.editableProperty().bind(this.usersViewModel.editModeProperty());
        this.admnistradorCheck.disableProperty().bind(this.usersViewModel.editModeProperty().not());
        this.activoCheck.disableProperty().bind(this.usersViewModel.editModeProperty().not());
       /* this.usersViewModel.editModeProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                //se desactiva la validación para el modo vista, solo efectos visuales
                validationSupport.setValidationDecorator(new ValidationDecoration() {
                    @Override
                    public void removeDecorations(Control target) {}
                    @Override
                    public void applyValidationDecoration(ValidationMessage message) {}
                    @Override
                    public void applyRequiredDecoration(Control target) {}
                });
            }else{
                var t=validationSupport.getValidationDecorator();
                //se vuelven a activar los efectos visuales
                validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
            }
        });*/

    }

    @FXML
    public void initialize() {

        this.configValidation();

        imagenBtn.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("imágenes", "*.jpg", "*.jpeg", "*.png")
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
        validationSupport.registerValidator(nombreField,
                Validator.createEmptyValidator("El nombre es obligatorio"));
        validationSupport.registerValidator(this.aliasField,
                Validator.createEmptyValidator("El nick es obligatorio"));
        validationSupport.registerValidator(passwordField,
                Validator.createEmptyValidator("El password es obligatorio"));
        validationSupport.registerValidator(this.pathImagenField,
                Validator.createEmptyValidator("Se ha de seleccioanr una imagen"));
        validationSupport.registerValidator(this.retrypasswordField, Validator.createPredicateValidator(t -> {
            return passwordField.getText().equals(retrypasswordField.getText());
        }, "Los password no coinciden", Severity.ERROR));

    }

    private void save() {
        this.usersViewModel.currentProperty().get().setName(this.nombreField.getText());
        this.usersViewModel.currentProperty().get().setNickname(this.aliasField.getText());
        this.usersViewModel.currentProperty().get().setPassword(this.passwordField.getText());
        this.usersViewModel.currentProperty().get().setImage(this.pathImagenField.getText());
        if (this.activoCheck.isSelected()) {
            this.usersViewModel.currentProperty().get().setStatus(User.Status.ACTIVE);
        } else {
            this.usersViewModel.currentProperty().get().setStatus(User.Status.INACTIVE);
        }
        if (this.admnistradorCheck.isSelected()) {
            this.usersViewModel.currentProperty().get().setRole(User.Role.ADMIN);
        } else {
            this.usersViewModel.currentProperty().get().setRole(User.Role.USER);
        }
        try {
            this.usersViewModel.saveCurrent();
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
        //se
        //this.UsersViewModel.currentProperty().removeListener(this.escuchadorViewModel);
    }

    @Override
    public void reset() {

    }
}