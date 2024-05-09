package edu.upvictoria.fpoo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App
{
    public static void main( String[] args ) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String instruccion;
        String[] separacion;
        boolean continuar = true;

        comandos prueba = new comandos();
        File rutaUtilizada = new File("");
        String detectarCreate = "CREATE TABLE \\w+ \\((.*?)\\);";
        Pattern patternCreate = Pattern.compile(detectarCreate);
        Matcher matcher;

        //Pruebas (en el orden presentado)
        try
        {
            do {
                instruccion = br.readLine();
                separacion = instruccion.split(" ");
                matcher = patternCreate.matcher(instruccion);
                System.out.println(instruccion);

                //Prueba del comando USE
                if((separacion[0].toUpperCase().equals("USE")) && (separacion.length == 2))
                {
                    rutaUtilizada = prueba.USE(separacion[separacion.length-1]);
                    System.out.println("ruta a utilizar: " + rutaUtilizada.getAbsolutePath());
                }

                //Prueba del comando SHOW TABLES
                if ((separacion[0].toUpperCase().equals("SHOW")) && (separacion[1].toUpperCase().equals("TABLES;")) && (separacion.length == 2))
                {
                    prueba.SHOWTABLE(rutaUtilizada);
                }

                //Prueba del comando CREATE TABLE
                if (matcher.matches())
                {
                    prueba.CREATETABLE(instruccion, rutaUtilizada);
                }
            }while (continuar);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}