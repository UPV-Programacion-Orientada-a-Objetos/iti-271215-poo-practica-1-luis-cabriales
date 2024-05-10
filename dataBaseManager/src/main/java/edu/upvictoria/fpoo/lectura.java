package edu.upvictoria.fpoo;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lectura {
    public void leer() throws FileAlreadyExistsException, IOException, RuntimeException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String instruccion;
        String[] separacion;
        boolean continuar = true;

        comandos prueba = new comandos();
        File rutaUtilizada = new File("");
        String detectarCreate = "CREATE TABLE \\w+ \\((.*?)\\);";
        String detectarDrop = "DROP TABLE \\w+;";
        String detectarInsert = "INSERT INTO \\w+ \\((.*?)\\) VALUES \\((.*?)\\);";
        String detectarSelectTodo = "SELECT \\* FROM \\w+;";
        String detectarDelete = "DELETE FROM \\w+ WHERE \\((.*?)\\);";

        Pattern patternDrop = Pattern.compile(detectarDrop);
        Pattern patternCreate = Pattern.compile(detectarCreate);
        Pattern patternInsert = Pattern.compile(detectarInsert);
        Pattern patternSelectTodo = Pattern.compile(detectarSelectTodo);
        Pattern patternDelete = Pattern.compile(detectarDelete);

        Matcher matcherCreate;
        Matcher matcherDrop;
        Matcher matcherInsert;
        Matcher matcherSelectTodo;
        Matcher matcherDelete;

        //Pruebas (en el orden presentado)
        try
        {
            do
            {
                instruccion = br.readLine();

                separacion = instruccion.split(" ");
                matcherCreate = patternCreate.matcher(instruccion);
                matcherDrop = patternDrop.matcher(instruccion);
                matcherInsert = patternInsert.matcher(instruccion);
                matcherSelectTodo = patternSelectTodo.matcher(instruccion);
                matcherDelete = patternDelete.matcher(instruccion);
                System.out.println(instruccion);

                switch (separacion[0].toUpperCase())
                {
                    //Pruba del comando USE $PATH (path asignado por el usuario)
                    case "USE":
                        if (separacion.length < 2)
                        {
                            throw new RuntimeException("Excepción: No coloco una ruta a utiliza");
                        }

                        if (separacion.length == 2)
                        {
                            rutaUtilizada = prueba.USE(separacion[separacion.length-1]);
                            System.out.println("ruta a utilizar: " + rutaUtilizada.getAbsolutePath());
                        }
                        break;

                    //Prueba del comando SHOW TABLES;
                    case "SHOW":
                        if (separacion.length < 2)
                        {
                            throw new IndexOutOfBoundsException("Excepción: Falta lo siguiente en la sentencia: TABLES;");
                        }

                        if ((separacion[1].toUpperCase().equals("TABLES;")) && (separacion.length == 2))
                        {
                            System.out.println("Tablas en la ruta seleccionada: ");
                            prueba.SHOWTABLE(rutaUtilizada);
                            break;
                        }
                        else
                        {
                            if (separacion[1].toUpperCase().equals("TABLES"))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar un ; al final de la instrucción");
                            }
                            else
                            {
                                throw new RuntimeException("Excepción: Coloco algo diferente de 'TABLES;'");
                            }
                        }


                    //Prueba del comando CREATE TABLE
                    case "CREATE":
                        if (matcherCreate.matches())
                        {
                            prueba.CREATETABLE(instruccion, rutaUtilizada);
                            System.out.println("Tabla creada exitosamente");
                            break;
                        }
                        else
                        {
                            if (separacion[0].toUpperCase().equals("CREATE") && (separacion.length < 2))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar el resto de sintaxis: TABLE nombre_tabla");
                            }
                            if (!separacion[2].contains(";"))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar ; al final de la instrucción");
                            }
                        }
                        break;

                    //Prueba del comando INSERT TABLE
                    case "INSERT":
                        if (matcherInsert.matches())
                        {
                            prueba.INSERTTABLE(instruccion, rutaUtilizada);
                            System.out.println("Valores agregados correctamente");
                            break;
                        }
                        else
                        {
                            throw new RuntimeException("Excepción: Error de sintaxis en la función INSERT");
                        }

                    //Prueba del comando DROP TABLE
                    case "DROP":
                        if (matcherDrop.matches())
                        {
                            prueba.DROPTABLE(instruccion, rutaUtilizada);
                            break;
                        }
                        else
                        {
                            if (separacion[0].toUpperCase().equals("DROP") && (separacion.length < 2))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar el resto de sintaxis: TABLE nombre_tabla");
                            }
                            if (!separacion[2].contains(";"))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar ; al final de la instrucción");
                            }
                        }
                        break;

                    //Prueba del comando SELECT
                    case "SELECT":
                        if (matcherSelectTodo.matches())
                        {
                            prueba.SELECTTodo(instruccion, rutaUtilizada);
                            break;
                        }
                        else
                        {
                            if (separacion[0].toUpperCase().equals("SELECT") && (separacion.length < 2))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar el resto de sintaxis: * FROM nombre_tabla;");
                            }
                            if (!separacion[3].contains(";"))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar ; al final de la instrucción");
                            }
                        }

                        throw new RuntimeException("Error de sintaxis");

                    case "DELETE":
                        if (matcherDelete.matches())
                        {
                            prueba.DELETE(instruccion, rutaUtilizada);
                            break;
                        }
                        else
                        {
                            if (separacion[0].toUpperCase().equals("DELETE") && (separacion.length < 2))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar el resto de sintaxis: FROM nombre_tabla WHERE condicion;");
                            }
                            if (!separacion[3].contains(";"))
                            {
                                throw new RuntimeException("Excepción: Olvido colocar ; al final de la instrucción");
                            }
                        }

                    //Salir del modo lectura
                    case "EXIT":
                        continuar = false;
                        break;

                    default:
                        throw new RuntimeException("Excepción: Comando escrito no identificado");
                }
            }while (continuar);
        }
        catch (FileNotFoundException e)
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
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
