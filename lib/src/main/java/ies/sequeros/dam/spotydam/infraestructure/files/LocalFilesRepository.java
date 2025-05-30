package ies.sequeros.dam.spotydam.infraestructure.files;



import ies.sequeros.dam.spotydam.domain.repositories.IFilesRepository;
import ies.sequeros.dam.spotydam.infraestructure.utils.FileSystem;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFilesRepository implements IFilesRepository {
    private String ruta;
    public LocalFilesRepository(String path) {
        this.ruta = path;
    }
    @Override
    public String save(String matricula, String path) {
        String nuevaRuta= this.ruta+"/"+matricula+"."+ FileSystem.getExtension(path);
        Path p=Path.of(this.ruta);
        //en caso de que no exista el directorio
        if(!Files.exists(p)){
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        FileSystem.copiar(path,nuevaRuta);
        return nuevaRuta;
    }

    @Override
    public void delete(String path) {
        FileSystem.borrar(path);
    }

    /**
     * Remplaza un fichero por otro
     * @param nuevoNombre  nombre que tomará el fichero
     * @param path  ruta en que se encuentra el fichero a mover
     * @param original ruta del original, que se borrará
     * @return
     */
    @Override
    public String replace(String nuevoNombre, String path, String original) {
        String nuevaRuta= this.ruta+"/"+nuevoNombre+"."+FileSystem.getExtension(path);
        //se borra el archivo anterior
        FileSystem.borrar(original);
        //se copia el nuevo path
        FileSystem.copiar(path,nuevaRuta);
        return nuevaRuta;
    }
    public void close(){

    }
}
