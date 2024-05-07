package edu.upvictoria.fpoo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String salida;
        String[] separacion;

        comandos prueba = new comandos();

        //Pruebas (en el orden presentado)
        try
        {
            salida = br.readLine();
            separacion = salida.split(" ");
            File rutaUtilizada = new File("");

            //Prueba del comando USE
            if((separacion[0].toUpperCase().equals("USE")) && (separacion.length == 2))
            {
                rutaUtilizada = prueba.USE(separacion[separacion.length-1]);
                System.out.println(rutaUtilizada.getAbsolutePath());
            }

            //Prueba del comando SHOW TABLES
            salida = br.readLine();
            separacion = salida.split(" ");

            if ((separacion[0].toUpperCase().equals("SHOW")) && (separacion[1].toUpperCase().equals("TABLES")) && (separacion.length == 2))
            {
                prueba.SHOWTABLE(rutaUtilizada);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}