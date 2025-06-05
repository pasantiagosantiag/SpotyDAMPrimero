package ies.sequeros.dam.spotydam.songs;


import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.navegacion.AWindows;

import ies.sequeros.dam.spotydam.playlist.PlayListsViewModel;
import ies.sequeros.dam.spotydam.utils.AppViewModel;
import ies.sequeros.dam.spotydam.utils.MusicPlayerViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.GridView;

import java.util.Set;

public class SongsController extends AWindows {
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
    private GridView<Song> grid;

    private FilteredList<Song> filtrados;
    private SongsViewModel viewModel;
    private PlayListsViewModel playListsViewModel;
    private AppViewModel appViewModel;
    private MusicPlayerViewModel playerViewModel;
    public SongsController() {
        super();
    }

    public void setViewModels(SongsViewModel viewModel, PlayListsViewModel playListsViewModel,AppViewModel appViewModel, MusicPlayerViewModel musicPlayerViewModel) {
        this.viewModel = viewModel;
        this.appViewModel = appViewModel;
        this.playerViewModel = musicPlayerViewModel;
        this.playListsViewModel = playListsViewModel;
        filtrados = new FilteredList<>(this.viewModel.getSongsProperty());
        // El predicado se actualiza cada vez que cambia el texto del filtro
        this.searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrados.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return item.getName().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        this.grid.setItems(filtrados);
        this.up.setOnMouseClicked(event -> {
            //se va hacia atras
            if (this.router != null) {
                this.playerViewModel.stop();
                this.router.pop();
            }
        });
        this.add.setOnMouseClicked(event -> {
            if (this.router != null) {
                this.viewModel.setEmptyCurrent();
                this.viewModel.setEditMode(true);
                this.playerViewModel.stop();
                this.router.push("song");
            }
        });
        this.initGrid();
    }

    @FXML
    public void initialize() {

    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            SongCell c = new SongCell(this.viewModel,this.playListsViewModel);
            c.setOnDelete(item -> {
                boolean resultado = this.showMessageBooleano("¿Está seguro/a de borrar el elemento?", "Confirmar borrado");
                if (resultado) {
                    try {
                        this.viewModel.removeSong(item);
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
                this.router.push("song");
            });
            c.setOnEdit(item -> {
                //modod edición

                //se coloca el actual y se pasa
                this.viewModel.setCurrent(item);
                this.viewModel.setEditMode(true);
                this.router.push("song");
            });
            c.setOnPlay( item->{
                if( this.playerViewModel.currentTrackProperty().get()==null || !this.playerViewModel.currentTrackProperty().get().equals(item.getPath()))
                    this.playerViewModel.setSong(item.getPath());
                var status=this.playerViewModel.getStatus();
                disablePlayButtonfromGrid();
                if(this.playerViewModel.getStatus()== MediaPlayer.Status.READY || this.playerViewModel.getStatus()== MediaPlayer.Status.UNKNOWN || this.playerViewModel.getStatus()== MediaPlayer.Status.PAUSED || this.playerViewModel.getStatus()== MediaPlayer.Status.STOPPED)
                    this.playerViewModel.play();
                else
                    this.playerViewModel.pause();
            });
            c.setOnAddSongToPlayList((song, playList) -> {
                this.playListsViewModel.addSongToPlayList(playList,song);
            });
            return c;
        });


    }

    /**
     * para desactivar todos los botonoes de play
     */
    private void disablePlayButtonfromGrid(){
        Set<Node> nodos = this.grid.lookupAll(".grid-cell");
        for (Node n : nodos) {
            SongCell c = (SongCell) n;
            c.stop();

        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        this.playerViewModel.stop();
    }

    @Override
    public void reset() {

    }
}
