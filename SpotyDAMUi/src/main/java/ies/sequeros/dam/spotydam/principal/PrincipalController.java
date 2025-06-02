package ies.sequeros.dam.spotydam.principal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import ies.sequeros.dam.spotydam.application.song.*;
import ies.sequeros.dam.spotydam.application.user.*;
import ies.sequeros.dam.spotydam.config.ConfigureController;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;
import ies.sequeros.dam.spotydam.infraestructure.files.LocalFilesRepository;
import ies.sequeros.dam.spotydam.infraestructure.files.SongRepositoryInFile;
import ies.sequeros.dam.spotydam.infraestructure.files.UserRepositoryInFile;
import ies.sequeros.dam.spotydam.navegacion.AWindows;
import ies.sequeros.dam.spotydam.navegacion.Router;
import ies.sequeros.dam.spotydam.songs.SongController;
import ies.sequeros.dam.spotydam.songs.SongsController;
import ies.sequeros.dam.spotydam.songs.SongsViewModel;
import ies.sequeros.dam.spotydam.users.UserController;
import ies.sequeros.dam.spotydam.users.UsersController;
import ies.sequeros.dam.spotydam.users.UsersViewModel;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import ies.sequeros.dam.spotydam.utils.Configuration;
import ies.sequeros.dam.spotydam.utils.MusicPlayerViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class PrincipalController {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private StackPane contentPane;
    private IUserRepository userRepository;
    private IFilesRepository filesRepository;
    private IFilesRepository songsFilesRepository;
    private ISongRepository songsRepository;
    //casos de uso general
    //caso de uso de user
    private AddUserUseCase addUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private ChangeStateUserUseCase changeStateUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private ListAllUserUseCase listAllUserUseCase;
    private SetRoleUserUseCase setRoleUserUseCase;
    //casos de uso de cancion
    private AddSongUseCase addSongUseCase;
    private DeleteSongUseCase deleteSongUseCase;
    private GetSongByIdUseCase getSongByIdUseCase;
    private ListAllSongsUseCase listAllSongsUseCase;
    private RemoveSongToPlayListUseCase removeSongToPlayListUseCase;
    private UpdateSongUseCase updateSongUseCase;
    //viewmodel
    private UsersViewModel usersViewModel;
    private SongsViewModel songsViewModel;
    private AppViewModel appViewModel;
    private MusicPlayerViewModel musicPlayerViewModel;
    //varios
    private VBox listaOpciones;
    private Router router;
    @FXML
    public void initialize() {

        try {
            this.configRouter();

            this.listaOpciones = new VBox();
            this.listaOpciones.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.listaOpciones.setSpacing(10);

            Region region = new Region();

            region.setMinHeight(40);
            this.listaOpciones.getChildren().add(region);
            drawer.setSidePane(listaOpciones);

            drawer.open();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initRepositories(){
        var c=Configuration.getInstancia();
        this.userRepository= new UserRepositoryInFile(c.getUserJsonPath());
        this.filesRepository= new LocalFilesRepository(c.getImagesPath());
        this.songsRepository=new SongRepositoryInFile(c.getSonsJsongsPath());
        this.songsFilesRepository= new LocalFilesRepository(c.getSongsPath());

    }
    private void initUseCases(){
        //users
        this.addUserUseCase = new AddUserUseCase(userRepository, this.filesRepository);
        this.updateUserUseCase=new UpdateUserUseCase(userRepository, this.filesRepository);
        this.changeStateUserUseCase = new ChangeStateUserUseCase(userRepository);
        this.deleteUserUseCase= new DeleteUserUseCase(userRepository,this.filesRepository,null,null);
        this.getUserByIdUseCase= new GetUserByIdUseCase(userRepository,null,null);
        this.listAllUserUseCase= new ListAllUserUseCase(userRepository);
        this.setRoleUserUseCase= new SetRoleUserUseCase(userRepository);

        //song
        this.addSongUseCase= new AddSongUseCase(this.songsRepository,this.filesRepository,this.songsFilesRepository);
       this.deleteSongUseCase= new DeleteSongUseCase(songsRepository,null,this.filesRepository,this.songsFilesRepository);
       this.getSongByIdUseCase= new GetSongByIdUseCase(songsRepository,null);
       this.listAllSongsUseCase= new ListAllSongsUseCase(songsRepository);
       this.updateSongUseCase= new UpdateSongUseCase(this.songsRepository,null,this.filesRepository,this.songsFilesRepository);


    }
    private void initViewModels(){
        this.usersViewModel= new UsersViewModel(this.addUserUseCase,this.updateUserUseCase,this.
                changeStateUserUseCase,
                this.deleteUserUseCase,
                this.getUserByIdUseCase,
                this.listAllUserUseCase,
                this.setRoleUserUseCase);
        this.songsViewModel =new SongsViewModel(this.addSongUseCase,
                this.updateSongUseCase,
                this.deleteSongUseCase,
                this.getSongByIdUseCase,
                this.listAllSongsUseCase);
        this.musicPlayerViewModel= new MusicPlayerViewModel();
    }
    private void initHome() {
        JFXButton homeButton = new JFXButton("Home");
        FontIcon icon = new FontIcon("fa-home"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);

        homeButton.setGraphic(icon);
        homeButton.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(homeButton, Priority.ALWAYS);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(homeButton, new Insets(10, 10, 10, 10));
        homeButton.setOnMouseClicked(mouseEvent -> {
            this.router.clear();
        });
        this.listaOpciones.getChildren().add(homeButton);
    }

    private void initSongsList() {
        JFXButton button = new JFXButton("Songs");
        FontIcon icon = new FontIcon("fa-music"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);

        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));

        this.listaOpciones.getChildren().add(button);
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songs/list.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("songs", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongsController cc= loader.getController();
        cc.setRouter(this.router);
        cc.setViewModels(this.songsViewModel,this.appViewModel,this.musicPlayerViewModel);
        button.setOnMouseClicked(mouseEvent -> {
            this.router.push("songs");
        });
    }

    private void initUsersList() {
        JFXButton button = new JFXButton("Users");
        FontIcon icon = new FontIcon("fa-users"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));
        this.listaOpciones.getChildren().add(3,button);
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/list.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("users", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsersController cc= loader.getController();
        cc.setRouter(this.router);
        cc.setViewModel(this.usersViewModel);
        button.setOnMouseClicked(mouseEvent -> {
            this.router.push("users");
        });


    }
    private void initExit(){
        JFXButton button = new JFXButton("Exit");
        FontIcon icon = new FontIcon("fa-window-close"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));
        this.listaOpciones.getChildren().add(button);
        button.setOnMouseClicked(mouseEvent -> {
            //se cierran
           if(this.musicPlayerViewModel!=null){
               this.musicPlayerViewModel.dispose();
           }
           if(this.songsRepository!=null){
               this.songsRepository.close();
           }
           if(this.userRepository!=null){
               this.userRepository.close();
           }

            javafx.application.Platform.exit();
        });
    }
    private void initUserForm() {

        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users/form.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("user", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserController cc= loader.getController();
        cc.setRouter(this.router);
        cc.setViewModel(this.usersViewModel);
    }

    private void initSongForm() {

        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songs/form.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("song", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongController cc= loader.getController();
        cc.setRouter(this.router);
        cc.setViewModels(this.songsViewModel,this.appViewModel,this.musicPlayerViewModel);
    }
    private void initPlayList() {
        JFXButton homeButton = new JFXButton("PlayList");
        FontIcon icon = new FontIcon("fa-list"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        homeButton.setGraphic(icon);
        homeButton.setMaxWidth(Double.MAX_VALUE);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(homeButton, new Insets(10, 10, 10, 10));
        this.listaOpciones.getChildren().add(homeButton);
    }
    private void initConfig() {
        JFXButton button = new JFXButton("Settings");
        FontIcon icon = new FontIcon("fa-cogs"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));

        this.listaOpciones.getChildren().add(button);
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/config/form.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("config", loader);
       try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var cc= (AWindows)loader.getController();
        cc.setRouter(this.router);

        button.setOnMouseClicked(mouseEvent -> {
            this.router.push("config");
        });
    }
    private void configRouter(){
        this.router= new Router();
        this.router.setMain(this.contentPane);
    }


    public AppViewModel getAppViewModel() {
        return appViewModel;
    }
    public void setAppViewModel(AppViewModel appViewModel){
        this.appViewModel=appViewModel;
        this.initRepositories();
        this.initUseCases();
        this.initViewModels();
        this.initHome();
        this.initSongsList();
        this.initSongForm();

        this.initPlayList();
        this.initConfig();
        this.initExit();
        //solo si es administrador se le permitegestionar los usuarios
        if(this.appViewModel!=null && this.appViewModel.isRoot()) {
            this.initUsersList();
            this.initUserForm();
        }
    }
}
