package edu.upvictoria.fpoo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    //Comprobando que una ruta existente no provoque excepciones
    public void testExisteRuta()
    {
        comandos comando = new comandos();
        String rutaPrueba = "/home/luis";

        try
        {
            File ruta = comando.USE(rutaPrueba);
            assertNotNull("La ruta no puede estar en blanco", ruta);
            assertTrue("La ruta existe", ruta.exists());
        }
        catch (Exception e)
        {
            System.out.println("Ocurrio una excepcion inesperada: " + e.getMessage());
        }
    }

    //Comprobando que rutas inexistentes provoquen excepciones
    public void testNotExisteRuta()
    {
        comandos comando = new comandos();
        String rutaPrueba = "/home/luis/excepcion";

        try
        {
            comando.USE(rutaPrueba);
            fail("Esta ruta es inexistente");
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
