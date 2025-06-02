package ies.sequeros.dam.spotydam.users;


import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.navegacion.AWindows;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.controlsfx.control.GridView;

public class UsersController extends AWindows {
    @FXML
    private Label titulo;
    @FXML
    private MFXButton add;
    @FXML
    private MFXButton up;
    @FXML
    private MFXTextField searchField;
    @FXML
    private MFXButton search;
    @FXML
    private GridView<User> grid;

    private FilteredList<User> filtrados;
    private UsersViewModel viewModel;

    public UsersController() {
        super();
    }

    public void setViewModel(UsersViewModel viewModel) {
        this.viewModel = viewModel;
        filtrados = new FilteredList<>(this.viewModel.getUsersProperty());
        // El predicado se actualiza cada vez que cambia el texto del filtro
        this.searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrados.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return item.getNickname().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        this.grid.setItems(filtrados);
        this.up.setOnMouseClicked(event -> {
            //se va hacia atras
            if (this.router != null) {
                this.router.pop();
            }
        });
        this.add.setOnMouseClicked(event -> {
            if (this.router != null) {
                this.viewModel.setEmptyCurrent();
                this.viewModel.setEditMode(true);
                this.router.push("user");
            }
        });
        this.initGrid();
    }

    @FXML
    public void initialize() {

    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            UserCell c = new UserCell();
            c.setOnDelete(item -> {
                boolean resultado = this.showMessageBooleano("¿Está seguro/a de borrar el elemento?", "Confirmar borrado");
                if (resultado) {
                    try {
                        this.viewModel.removeUser(item);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Error al borrar el elemento:" + e.getMessage());
                    }
                }
            });

            c.setOnView(item -> {
                //solo ver
                this.viewModel.setCurrent(item);
                this.viewModel.setEditMode(false);
                this.router.push("user");
            });
            c.setOnEdit(item -> {
                //modod edición

                //se coloca el actual y se pasa
                this.viewModel.setCurrent(item);
                this.viewModel.setEditMode(true);
                this.router.push("user");
            });
            return c;
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
