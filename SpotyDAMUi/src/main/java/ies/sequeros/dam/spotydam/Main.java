package ies.sequeros.dam.spotydam;

import ies.sequeros.dam.spotydam.infraestructure.files.UserRepositoryInFile;
import ies.sequeros.dam.spotydam.login.LoginController;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import ies.sequeros.dam.spotydam.utils.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent load=loader.load();
        LoginController controller = loader.getController();
        Scene scene = new Scene(load);
        AppViewModel appViewModel= new AppViewModel(new UserRepositoryInFile(Configuration.getInstancia().getUserJsonPath()));
        LoginController lc=loader.getController();
        lc.setAppViewModel(appViewModel);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //se carga la configuración
        Configuration.getInstancia("configuracion.json");
        launch(args);
    }
}