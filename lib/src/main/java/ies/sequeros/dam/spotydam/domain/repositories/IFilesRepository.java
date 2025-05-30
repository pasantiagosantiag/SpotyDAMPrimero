package ies.sequeros.dam.spotydam.domain.repositories;

public interface IFilesRepository {
    public String save(String nuevoNombre,String path);
    public void delete(String path);

    /*
    sustituye la imagen del vehiculo, se le pasa la matricula, el path en que
    se almacenará y el original para ser borrado
     */
    public String replace(String nuevoNombre,String path,String original);
    public void close();
}
