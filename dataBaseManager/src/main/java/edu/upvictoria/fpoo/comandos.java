package edu.upvictoria.fpoo;
import java.io.File;

public class comandos {

    //Comando USE: ayuda a seleccionar una ruta donde guardar los archivos
    public File USE(String rutaString){
        File ruta = new File(rutaString);

        //Si ruta no existe, regresa una excepción
        if (!ruta.exists())
        {
            throw new RuntimeException("La ruta escrita no existe");
        }else
        {
            return ruta;
        }
    }

    //Comando SHOW TABLE: muestra los archivos dentro de la carpeta
    public void SHOWTABLE(File ruta)
    {
        for (File archivos : ruta.listFiles()){
            System.out.println(archivos.getName());
        }
    }
}