package ies.sequeros.dam.spotydam.infraestructure.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.sequeros.dam.spotydam.domain.model.PlayList;
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
    public void add(PlayList item) throws NoSuchFieldException {
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
            throw  new  NoSuchFieldException("Item  exists");
        }
    }



    @Override
    public void delete(PlayList item) throws NoSuchFieldException {
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
            throw  new  NoSuchFieldException("Item don´t exists");

    }

    @Override
    public void update(PlayList item) throws NoSuchFieldException {
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
            throw  new NoSuchFieldException("Item don't exists");

    }

    @Override
    public List<PlayList> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }

    @Override
    public List<PlayList> findByUserId(UUID userId) {
        var items=this.load().values();
        var filteritems=items.stream().filter(pl -> {
            return pl.getOwnerId().equals(userId);
        }).toList();
        return filteritems;
    }

    @Override
    public PlayList findById(UUID id) {
        var items=this.load().values();
        var item=items.stream().filter(pl -> {
            return pl.getId().equals(id);
        }).findFirst();

        return item.get();
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
