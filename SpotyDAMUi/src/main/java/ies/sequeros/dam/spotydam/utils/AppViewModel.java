package ies.sequeros.dam.spotydam.utils;

import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.MediaPlayer;

import java.util.UUID;

public class AppViewModel {
    private ObjectProperty<User> user;
    private IUserRepository userRepository;
    public static String rootName="root";
    public AppViewModel(IUserRepository userRepository) {
        user = new SimpleObjectProperty<>();
        this.userRepository = userRepository;


    }
    public boolean isRoot(){
        return user!=null && user.get()!=null && user.get().getId().equals(new UUID(0L, 0L));
    }
    public boolean isAdmin(){
        return isRoot() || (user!=null && user.get()!=null && user.get().getRole().equals(User.Role.ADMIN));
    }
    public boolean login(String username, String password) {
        Configuration c=Configuration.getInstancia();
        if(c.getAdminName().equals(username) && c.getAdminPassword().equals(password)) {
            User u= new User();
            u.setId(new UUID(0L, 0L));
            u.setName(rootName);
            u.setPassword("");
            u.setRole(User.Role.ADMIN);
            user.set(u);
            return true;
        }else{
            User u= userRepository.findByNickAndPassword(username, password);
            if(u != null) {
                user.set(u);
                return true;
            }
            return false;
        }
    }
    public ReadOnlyObjectProperty<User> getUser() {
        return user;
    }
    public void logout(){
        user.set(null);
    }

}
