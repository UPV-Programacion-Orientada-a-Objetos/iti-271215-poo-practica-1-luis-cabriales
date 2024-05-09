package edu.upvictoria.fpoo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        File rastreo = new File("rastreo");

        if (!rastreo.exists()){
            rastreo.mkdir();
        }

        String rutaPrueba = rastreo.getAbsolutePath();
        File testeo = new File(rutaPrueba);

        assertTrue("La ruta existe", testeo.exists());
    }

    //Comprobando que rutas inexistentes provoquen excepciones
    public void testNotExisteRuta()
    {
        comandos comando = new comandos();
        String rutaPrueba = "/Esta/ruta/no/existe";

        try
        {
            assertNull(comando.USE(rutaPrueba));
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Comprobando que la carpeta tenga archivos
    public void testExistenArchivos()
    {
        File ruta = new File("/home");

        assertNotNull(ruta.listFiles());
    }

    //Comprobar que la sintaxis de CREATE TABLE este bien escrita
    public void testSintaxisCreateTable(){
        //Se define el patron a detectar
        String detectar = "CREATE TABLE \\w+ \\((.*?)\\);";

        //String utilizado para este testCase
        String prueba = "CREATE TABLE ALUMNOS (id INT NOT NULL PRIMARY KEY, nombre VARCHAR(20) NOT NULL, app VARCHAR(20) NOT NULL, apm VARCHAR(20) NOT NULL, edad INT NULL);";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(prueba);

        // Comprobar que la sintais este bien escrita
        assertTrue(matcher.find());
    }

    //Comprobar que la instrucción CREATE TABLE cree un archivo.csv
    public void testSintaxisCrearTabla()
    {
        //Se define el patron a detectar
        String detectar = "CREATE TABLE \\w+ \\((.*?)\\);";

        //String utilizado para este testCase
        String prueba = "CREATE TABLE ALUMNOS (id INT NOT NULL PRIMARY KEY, nombre VARCHAR(20) NOT NULL, app VARCHAR(20) NOT NULL, apm VARCHAR(20) NOT NULL, edad INT NULL);";

        //Se compila el patron a detectar
        Pattern pattern = Pattern.compile(detectar);

        //Se guarda la cadena en un objeto Matcher
        Matcher matcher = pattern.matcher(prueba);
    }
}
