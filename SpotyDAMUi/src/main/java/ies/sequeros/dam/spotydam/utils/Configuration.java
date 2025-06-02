package ies.sequeros.dam.spotydam.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**

 * Clase singleton con la configuración de la aplicación

 * los datos se encuentran en Json, cada atributo es de tipo StringProperty

 * con lo que la aplicación es reactiva a los cambios

 * además los cambios se almacenan directamente en el fichero

 */
public class Configuration {

    private static Configuration instancia;

    //private final Map<String, StringProperty> configuraciones = new HashMap<>();
    private StringProperty songsPath;
    private StringProperty imagesPath;
    private StringProperty sonsJsongsPath;
    private StringProperty userJsonPath;
    private StringProperty playListJsonPath;

    private String adminName;
    private String adminPassword;
    private  ObjectMapper mapper = new ObjectMapper();
    private  File archivo;

    private Configuration(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        cargarConfiguraciones();
        //cuando cambia se guarda
       /* endPointAnalsisiCancion.addListener((observableValue, s, t1) -> {
            this.guardar();
        });
        directorioCanciones.addListener((observableValue, s, t1) -> {
            this.guardar();
        });
        tiempoDevida.addListener((observableValue, s, t1) -> {
            this.guardar();
        });*/

    }

    public static synchronized Configuration getInstancia(String rutaArchivo) {
        if (instancia == null) {
            instancia = new Configuration(rutaArchivo);
        }
        return instancia;
    }
    public static synchronized Configuration getInstancia() {
        if (instancia == null) {
            throw  new IllegalArgumentException("Se ha de llamar la primera vez a getInstance(path) para obtener el fichero");
        }
        return instancia;
    }
    private void cargarConfiguraciones() {
        // if (!archivo.exists()) return;
        try {
            Map<String, Object> datos = mapper.readValue(archivo, new TypeReference<Map<String, Object>>() {});
            this.songsPath= new SimpleStringProperty(datos.get("songspath").toString());
            this.imagesPath= new SimpleStringProperty(datos.get("imagespath").toString());
            this.playListJsonPath= new SimpleStringProperty(datos.get("playlistsjsonpath").toString());
            this.sonsJsongsPath= new SimpleStringProperty(datos.get("sonsjsonpath").toString());
            this.userJsonPath= new SimpleStringProperty(datos.get("userjsonpath").toString());
            this.adminName= datos.get("adminname").toString();
            this.adminPassword= datos.get("adminpassword").toString();

        } catch (IOException e) {
            System.err.println("Error al cargar configuraciones: " + e.getMessage());
            throw new RuntimeException("Error al cargar configuraciones: " + e.getMessage());
        }
    }
    public void guardar() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("songspath", this.songsPath.getValue());
        datos.put("imagespath", this.imagesPath.getValue());
        datos.put("adminname", this.adminName);
        datos.put("adminpassword", this.adminPassword);

        datos.put("playlistsjsonpath",this.playListJsonPath.getValue());
        datos.put("sonsjsonpath",this.sonsJsongsPath.getValue());
        datos.put("userjsonpath",this.userJsonPath.getValue());

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, datos);
        } catch (IOException e) {
            System.err.println("Error al guardar configuraciones: " + e.getMessage());
        }
    }

    public String getSongsPath() {
        return songsPath.get();
    }

    public StringProperty songsPathProperty() {
        return songsPath;
    }

    public void setSongsPath(String songsPath) {
        this.songsPath.set(songsPath);
    }

    public String getImagesPath() {
        return imagesPath.get();
    }

    public StringProperty imagesPathProperty() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath.set(imagesPath);
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getSonsJsongsPath() {
        return sonsJsongsPath.get();
    }

    public StringProperty sonsJsongsPathProperty() {
        return sonsJsongsPath;
    }

    public void setSonsJsongsPath(String sonsJsongsPath) {
        this.sonsJsongsPath.set(sonsJsongsPath);
    }

    public String getUserJsonPath() {
        return userJsonPath.get();
    }

    public StringProperty userJsonPathProperty() {
        return userJsonPath;
    }

    public void setUserJsonPath(String userJsonPath) {
        this.userJsonPath.set(userJsonPath);
    }

    public String getPlayListJsonPath() {
        return playListJsonPath.get();
    }

    public StringProperty playListJsonPathProperty() {
        return playListJsonPath;
    }

    public void setPlayListJsonPath(String playListJsonPath) {
        this.playListJsonPath.set(playListJsonPath);
    }
}