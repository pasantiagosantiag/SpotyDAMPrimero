package ies.sequeros.dam.spotydam.infraestructure.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.User;
import ies.sequeros.dam.spotydam.domain.repositories.IPlayListRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayListRepositoryInFile implements IPlayListRepository {
    protected final ObjectMapper mapper;
    protected  final File file;

    public PlayListRepositoryInFile(String path) {
        this.file = new File(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }
    @Override
    public void add(PlayList item)  {
        if(item==null){

            throw  new IllegalArgumentException("Item is null");
        }
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
    public void delete(PlayList item) {
        HashMap<UUID,PlayList> items= load();
        if(items.containsKey(item.getId())){
            items.remove(item.getId());
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else
            throw  new IllegalArgumentException("Item don´t exists");

    }

    @Override
    public void update(PlayList item)  {
        if(item==null){
            throw  new IllegalArgumentException("Item is null");
        }
        HashMap<UUID,PlayList> items= load();
        PlayList original= items.get(item.getId());
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
    public List<PlayList> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }

    @Override
    public List<PlayList> findByUser(User user) {
        var items=this.load().values();
        var filteritems=items.stream().filter(pl -> {
            return pl.getOwnerId().equals(user.getId());
        }).toList();
        return filteritems;
    }

   /* @Override
    public List<PlayList> findByUserIdorAdmin(User user) {
        var items=this.load().values();
        var filteritems=items.stream().filter(pl -> {
            return pl.getOwnerId().equals(user.getId() )|| user.getRole().equals(User.Role.ADMIN);
        }).toList();
        return filteritems;
    }*/

    @Override
    public List<PlayList> findByTitle(String title) {
        return List.of();
    }

    @Override
    public PlayList findById(UUID id) {
        var items=this.load().values();
        var item=items.stream().filter(pl -> {
            return pl.getId().equals(id);
        }).findFirst();
return item.orElse(null);
     //   return item.get();
    }

    public void close(){

    }

    private HashMap<UUID, PlayList> load() {
        var items=new HashMap<UUID,PlayList>();
        if (file.exists()) {
            try {
                items=mapper.readValue(file,   new TypeReference<HashMap<UUID, PlayList>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<UUID,PlayList> items) throws IOException {

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

}
