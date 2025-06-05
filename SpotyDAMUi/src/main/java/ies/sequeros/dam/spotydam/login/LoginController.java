package ies.sequeros.dam.spotydam.login;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.principal.PrincipalController;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    private AppViewModel appViewModel;
    @FXML
    public void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        if(appViewModel!=null) {
            boolean validation=appViewModel.login(user, pass);
            if (validation) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/principal.fxml"));
                    Parent root = loader.load();
                    PrincipalController pc=loader.getController();
                    //se le pasa el viewmodel
                    pc.setAppViewModel(appViewModel);
                    Stage stage = new Stage();
                    stage.setTitle("SpotyDAM");
                    // stage.setMaximized(true);
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Cerrar ventana de login
                    ((Stage) usernameField.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Invalid credentials");
            }
        }
    }

    public AppViewModel getAppViewModel() {
        return appViewModel;
    }

    public void setAppViewModel(AppViewModel appViewModel) {
        this.appViewModel = appViewModel;
    }

    public void handleExit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}