package edu.upvictoria.fpoo;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class comandos {

    //Comando USE: ayuda a seleccionar una ruta donde guardar los archivos
    public File USE(String rutaString) throws RuntimeException {
        File ruta = new File(rutaString);

        //Si ruta no existe, regresa una excepción
        if (!ruta.exists())
        {
            throw new RuntimeException("La ruta escrita no existe");
        }

        //Si la ruta no es un directorio
        if (!ruta.isDirectory())
        {
            throw new RuntimeException("La ruta escrita no es una carpeta");
        }
        return ruta;
    }

    //Comando SHOW TABLE: muestra los archivos dentro de la carpeta
    public void SHOWTABLE(File ruta) throws RuntimeException
    {
        try {
            //Si hay archivos manda los nombres de los archivos
            for (File archivos : ruta.listFiles())
            {
                System.out.println(archivos.getName());
            }
        }
        catch (NullPointerException e)
        {
            //Arroja una excepción si no hay archivos en la carpeta
            throw new RuntimeException("La ruta actual no contiene archivos");
        }
    }

    //Comando CREATE TABLE
    public void CREATETABLE(String comando) throws RuntimeException{

        //Se define el patron a detectar
        String detectar = "CREATE TABLE \\w+ \\((.*?)\\);";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(comando);

        // Comprobar que la sintais este bien escrita
        if (matcher.matches())
        {
            System.out.println(comando);
        }
        else
        {
            throw new RuntimeException("La instruccion esta mal escrita");
        }
    }
}