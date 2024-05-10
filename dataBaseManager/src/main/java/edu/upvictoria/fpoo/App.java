package edu.upvictoria.fpoo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class App
{
    public static void main( String[] args ) {
        try
        {
            lectura Lectura = new lectura();

            Lectura.leer();
        }catch (FileAlreadyExistsException e)
        {
            System.out.println(e.getMessage());
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}