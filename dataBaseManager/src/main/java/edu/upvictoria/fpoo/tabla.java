package edu.upvictoria.fpoo;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

public class tabla {
    public void crearTabla(String nombre, File ruta) throws FileAlreadyExistsException
    {
        File tabla = new File(ruta + "/" +  nombre + ".csv");

        try
        {
            if (!tabla.exists())
            {
                tabla.createNewFile();
            }
            else
            {
                throw new FileAlreadyExistsException("La tabla " + nombre + " ya existe" );
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
