package ies.sequeros.dam.spotydam.infraestructure.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.sequeros.dam.spotydam.domain.model.Song;
import ies.sequeros.dam.spotydam.domain.repositories.ISongRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SongRepositoryInFile implements ISongRepository {
    protected final ObjectMapper mapper;
    protected  final File file;

    public SongRepositoryInFile(String path) {
        this.file = new File(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }
    @Override
    public void add(Song item) {
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
    public void delete(Song item)  {
        HashMap<UUID,Song> items= load();
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
    public void update(Song item) {
        HashMap<UUID,Song> items= load();
        Song original= items.get(item.getId());
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
    public List<Song> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }

    @Override
    public List<Song> findByOwnerId(UUID userId) {
        var items=this.load().values();
        var filteritems=items.stream().filter(s -> {
            return s.getOwnerId().equals(userId);
        }).toList();
        return filteritems;
    }
    public List<Song> findByPublicByGenre(Song.Genre genere){
        var items=this.load().values();
        return items.stream().filter(s -> {
            return s.getGenres().stream().anyMatch(genre -> genre.equals(genere));
        }).toList();
    }
    public List<Song> findByPublic(){
        var items=this.load().values();
        return items.stream().filter(Song::isPublic).toList();
    }
    @Override
    public Song findById(UUID id) {
        var items=this.load().values();
        var item=items.stream().filter(pl -> {
            return pl.getId().equals(id);
        }).findFirst();


        return item.orElse(null);
    }

    public void close(){

    }

    private HashMap<UUID, Song> load() {
        var items=new HashMap<UUID,Song>();
        if (file.exists()) {
            try {
                items=mapper.readValue(file,   new TypeReference<HashMap<UUID, Song>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<UUID,Song> items) throws IOException {

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
