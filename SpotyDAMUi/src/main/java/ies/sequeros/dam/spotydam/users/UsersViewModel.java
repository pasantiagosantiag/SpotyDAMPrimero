package ies.sequeros.dam.spotydam.users;




import ies.sequeros.dam.spotydam.application.user.*;
import ies.sequeros.dam.spotydam.domain.model.User;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class UsersViewModel {
    //listado observable
    private ListProperty<User> users;
    //User actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<User> current;
    //modo edicion o no
    private BooleanProperty editMode;
    //casos de uso
    private AddUserUseCase addUserUseCase;
    private ChangeStateUserUseCase changeStateUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private ListAllUserUseCase listAllUserUseCase;
    private SetRoleUserUseCase setRoleUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    


    public UsersViewModel(AddUserUseCase addUserUseCase, UpdateUserUseCase updateUserUseCase,ChangeStateUserUseCase changeStateUserUseCase,DeleteUserUseCase deleteUserUseCase, GetUserByIdUseCase getUserByIdUseCase
    , ListAllUserUseCase listAllUserUseCase, SetRoleUserUseCase setRoleUserUseCase) {
       

        this.users = new SimpleListProperty<>(FXCollections.observableArrayList());
this.editMode= new SimpleBooleanProperty(false);
        this.current = new SimpleObjectProperty<>(new User());
        this.addUserUseCase = addUserUseCase;
        this.changeStateUserUseCase = changeStateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.listAllUserUseCase = listAllUserUseCase;
        this.setRoleUserUseCase = setRoleUserUseCase;
        this.updateUserUseCase = updateUserUseCase;

        this.load();


    }
    public void load() {
        if (this.listAllUserUseCase != null) {
            this.users.clear();
            this.users.addAll(this.listAllUserUseCase.execute());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.users.clear();
        this.load();
    }

    public void addUser(User item) throws NoSuchFieldException, IOException {
        if (this.addUserUseCase != null) {
            this.addUserUseCase.execute(item);
            this.users.add(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeUser(User item) throws NoSuchFieldException, IOException {
        if (this.deleteUserUseCase != null) {
            this.deleteUserUseCase.execute(item);
            this.users.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");


    }

    public boolean isEditMode() {
        return editMode.get();
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }

    public ListProperty<User> getUsersProperty() {
        return this.users;
    }

    public User getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<User> currentProperty() {
        return this.current;
    }

    public void setCurrent(User item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new User());
    }

    /**
     * si el id es 0 significa que es nuevo
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && this.current.get().getId()==null) {
            this.current.get().setId(UUID.randomUUID());
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addUser(this.current.get());

        } else {
            //para indicar que se ha actualizado
            if (this.updateUserUseCase != null) {
                this.updateUserUseCase.execute(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            }
        }
    }
    public void setEmptyCurrent() {
        this.current.set(new User());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new User());
        ObservableList<User> oldList = FXCollections.observableArrayList(this.users);
        this.users.clear();
        this.users.addAll(oldList);
    }

}
