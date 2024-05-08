package edu.upvictoria.fpoo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws RuntimeException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String instruccion;
        String[] separacion;

        comandos prueba = new comandos();

        //Pruebas (en el orden presentado)
        try
        {
            instruccion = br.readLine();
            separacion = instruccion.split(" ");
            File rutaUtilizada = new File("");

            //Prueba del comando USE
            if((separacion[0].toUpperCase().equals("USE")) && (separacion.length == 2))
            {
                rutaUtilizada = prueba.USE(separacion[separacion.length-1]);
                System.out.println(rutaUtilizada.getAbsolutePath());
            }

            //Prueba del comando SHOW TABLES
            instruccion = br.readLine();
            separacion = instruccion.split(" ");

            if ((separacion[0].toUpperCase().equals("SHOW")) && (separacion[1].toUpperCase().equals("TABLES;")) && (separacion.length == 2))
            {
                prueba.SHOWTABLE(rutaUtilizada);
            }

            //Prueba de comandos CREATE TABLE
            instruccion = br.readLine();
            prueba.CREATETABLE(instruccion);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}