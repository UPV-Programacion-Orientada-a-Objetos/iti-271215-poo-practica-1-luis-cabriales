package edu.upvictoria.fpoo;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class comandos {

    //Comando USE: ayuda a seleccionar una ruta donde guardar los archivos
    public File USE(String rutaString) throws RuntimeException
    {
        File ruta = new File(rutaString);

        //Si ruta no existe, regresa una excepción
        if (!ruta.exists())
        {
            throw new RuntimeException("Excepción: La ruta escrita no existe");
        }

        //Si la ruta no es un directorio
        if (!ruta.isDirectory())
        {
            throw new RuntimeException("Excepción: La ruta escrita no es una carpeta");
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
            throw new RuntimeException("Excepción: La ruta actual no contiene archivos");
        }
    }

    //Comando CREATE TABLE
    public void CREATETABLE(String comando, File ruta) throws RuntimeException, IOException, SecurityException
    {
        tabla nuevaTabla = new tabla();

        //Se define el patron a detectar
        // \\w+ es el nombre de la tabla y (.*?) son los parametros de la tabla
        String detectar = "CREATE TABLE \\w+ \\((.*?)\\);";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(comando);

        //Comprobar que la sintaxis este bien escrita
        if (matcher.matches())
        {
            String[] extraerNombre = matcher.group(0).split(" ");
            String nombreTabla = extraerNombre[2];
            String[] datos = matcher.group(1).split(",");
            for (int i = 0; i < datos.length; i++)
            {
                System.out.println(datos[i]);
            }

            nuevaTabla.crearTabla(nombreTabla, ruta);
        }
        else
        {
            throw new RuntimeException("Excepción: La instruccion esta mal escrita");
        }
    }

    public void INSERTTABLE(String comando, File ruta)
    {
        tabla Tabla = new tabla();

        // \\w+ es el nombre de la tabla y (.*?) son los parametros de la tabla
        String detectar = "INSERT INTO \\w+ \\((.*?)\\) VALUES \\((.*?)\\);";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(comando);

        //Comprobar que la sintaxis este bien escrita
        if (matcher.matches())
        {
            String[] extraerNombre = matcher.group(0).split(" ");
            String nombreTabla = extraerNombre[2];
            String[] nombreColumna = matcher.group(1).split(",");
            String[] datos = matcher.group(2).split(",");

            Tabla.insertTabla(nombreTabla, nombreColumna, datos,ruta);
        }
        else
        {
            throw new RuntimeException("Excepción: La instruccion esta mal escrita");
        }
    }

    //Comando DROP TABLE
    public void DROPTABLE(String comando, File ruta) throws RuntimeException
    {
        tabla Tabla = new tabla();
        // \\w+ es el nombre de la tabla y (.*?) son los parametros de la tabla
        String detectar = "DROP TABLE \\w+;";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(comando);

        // Comprobar que la sintaxis este bien escrita
        if (matcher.matches())
        {
            String[] extraerNombre = matcher.group(0).split(" ");
            String nombreTabla = extraerNombre[2].substring(extraerNombre[2].length() - extraerNombre[2].length(), extraerNombre[2].length() - 1);
            Tabla.dropTabla(nombreTabla, ruta);
        }else
        {
            throw new RuntimeException("Excepción: La instruccion esta mal escrita");
        }
    }

    public void SELECTTodo(String comando, File ruta) throws RuntimeException
    {
        tabla Tabla = new tabla();

        //Se define el patron a detectar
        // \\w+ es el nombre de la tabla y (.*?) son los parametros de la tabla
        String detectar = "SELECT \\* FROM \\w+;";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(comando);

        //Comprobar que la sintaxis este bien escrita
        if (matcher.matches())
        {
            String[] partir = matcher.group(0).split(" ");
            String nombreTabla = partir[3].substring(partir[3].length() - partir[3].length(), partir[3].length() - 1) ;
            Tabla.SELECTTodo(nombreTabla, ruta);
        }
        else
        {
            throw new RuntimeException("Excepción: La instruccion esta mal escrita");
        }
    }

    public void DELETE(String comando, File ruta) throws RuntimeException, IOException {
        tabla Tabla = new tabla();

        String detectar = "DELETE FROM \\w+ WHERE \\((.*?)\\);";

        Pattern pattern = Pattern.compile(detectar);

        Matcher matcher = pattern.matcher(comando);

        if (matcher.matches())
        {
            String[] partir = matcher.group(0).split(" ");
            String nombreTabla = partir[2];
            String condicion = matcher.group(1);

            Tabla.delete(nombreTabla, ruta, condicion);
        }
    }
}