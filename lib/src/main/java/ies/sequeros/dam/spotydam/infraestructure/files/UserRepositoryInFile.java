package ies.sequeros.dam.spotydam.infraestructure.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IUserRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserRepositoryInFile implements IUserRepository {
    protected final ObjectMapper mapper;
    protected  final File file;

    public UserRepositoryInFile(String path) {
        this.file = new File(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }
    @Override
    public void add(User item)  {
        var items=this.load();
        //se comprueba que el mecanico no existe
        if(!items.containsKey(item.getId() )
                && !items.containsValue(item)
        ) {
            items.put(item.getId(),item);
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw  new IllegalArgumentException("Item  exists");
        }
    }

    @Override
    public User findByNickAndPassword(String nickName, String password) {
        var items=this.load().values();
        var item=items.stream().filter(user -> {
            return user.getNickname().equals(nickName) && user.getPassword().equals(password);
        }).findFirst();
        return item.orElse(null);

    }

    @Override
    public void delete(User item) {
        HashMap<UUID,User> items= load();
        if(items.containsKey(item.getId())){
            items.remove(item.getId());
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        //else
        //    throw  new IllegalArgumentException("Item don´t exists");

    }

    @Override
    public void update(User item) {
        HashMap<UUID,User> items= load();
        User original= items.get(item.getId());
        if(original != null) {
            items.remove(original.getId());
            items.put(item.getId(),item);
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else

        throw  new IllegalArgumentException("Item don't exists");

    }

    @Override
    public List<User> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }
    public void close(){

    }
    private HashMap<UUID, User> load() {
        var items=new HashMap<UUID,User>();
        if (file.exists()) {
            try {
                items=mapper.readValue(file,   new TypeReference<HashMap<UUID, User>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<UUID,User> items) throws IOException {

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, items);

    }
    public void removeAll()  {
        var items=this.load();
        items.clear();
        try {
            this.save(items);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(UUID id) {
        var items=this.load().values();
        var item=items.stream().filter(user -> {
            return user.getId().equals(id);
        }).findFirst();
        return item.orElse(null);
    }

    @Override
    public User findByNick(String nickName) {
        var items=this.load().values();
        var item=items.stream().filter(user -> {
            return user.getNickname().equals(nickName) ;
        }).findFirst();
        return item.orElse(null);
    }
}
