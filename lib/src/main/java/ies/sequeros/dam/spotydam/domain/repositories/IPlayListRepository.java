package ies.sequeros.dam.spotydam.domain.repositories;

import ies.sequeros.dam.spotydam.domain.model.PlayList;
import ies.sequeros.dam.spotydam.domain.model.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface  IPlayListRepository {
    public void add(PlayList PlayList);
    public void delete(PlayList PlayList);
    public void update(PlayList PlayList);
    public List<PlayList> findAll();
    public List<PlayList> findByUser(User id);
    //public List<PlayList> findByUserIdorAdmin(User id);
    public List<PlayList> findByTitle(String title);
    public PlayList findById(UUID id);
    public void close();
    public void removeAll() ;
}
