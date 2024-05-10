package edu.upvictoria.fpoo;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;

public class tabla {
    public void crearTabla(String nombre, File ruta) throws FileAlreadyExistsException, SecurityException, IOException {
        File tabla;
        if (!ruta.exists()) {
            throw new RuntimeException("La ruta no existe");
        } else {
            tabla = new File(ruta + "/" + nombre + ".csv");
        }

        try {
            tabla.createNewFile();
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsException("Esta tabla ya existe");
        } catch (SecurityException e) {
            throw new SecurityException("No tienes permiso para modificar este directorio");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void insertTabla(String nombre, String[] nombrecolumna, String[] datos, File ruta) {
        File tabla = new File(ruta + "/" + nombre + ".csv");
        String[] columnasRecibidas = nombrecolumna;
        for (int i = 0; i < columnasRecibidas.length; i++)
        {
            columnasRecibidas[i] = columnasRecibidas[i].trim();
        }

        //EN caso de que la tabla no exista
        if (!tabla.exists()) {
            throw new RuntimeException("Excepción: El archivo donde quiere guardar datos no existe");
        }

        try {
            FileWriter fw = new FileWriter(tabla, true);
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedReader br = new BufferedReader(new FileReader(tabla));

            //Para Primera ejecución
            if (tabla.length() == 0) {
                for (int i = 0; i < columnasRecibidas.length; i++) {
                    bw.write(columnasRecibidas[i] + "\t");
                    if (i < columnasRecibidas.length - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();

                for (int i = 0; i < datos.length; i++) {
                    bw.write(datos[i] + "\t");
                    if (i < datos.length - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();

                bw.close();
            } else {
                //Recuperar el nombre de las columnas guardadas en el archivo
                String columnasGuardadas = br.readLine();
                String[] columnas = columnasGuardadas.split(",");
                //Borrar los espacios antes y despues de los nombres de las columnas en el archivo
                for (int i = 0; i < columnas.length; i++) {
                    columnas[i] = columnas[i].trim();
                }
                //Borrar los espacios antes y despues de los nombres de las columnas recibidas
                for (int i = 0; i < columnasRecibidas.length; i++) {
                    columnasRecibidas[i] = columnasRecibidas[i].trim();
                    System.out.println(columnasRecibidas[i]);
                }

                //En caso de que el usuario trate de ingresar más datos de los que requiere
                if (columnas.length != columnasRecibidas.length) {
                    bw.close();
                    throw new RuntimeException("Excepción: Esta tratando de ingresar más datos de los que soporta la tabla");
                }
                //En caso de que trate de insertar datos a columnas que no existen en la tabla
                else if (!Arrays.asList(columnas).containsAll(Arrays.asList(columnasRecibidas))) {
                    bw.close();
                    throw new RuntimeException("Excepción: Esta tratando de ingresar datos que no se encuentran originalmente en la tabla");
                }

                //Registros subsecuentes
                if (columnasRecibidas.length == datos.length) {
                    for (int i = 0; i < datos.length; i++) {
                        bw.write(datos[i] + "\t");
                        if (i < datos.length - 1) {
                            bw.write(",");
                        }
                    }
                    bw.newLine();

                    System.out.println("Datos guardados exitosamente");
                    bw.close();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SELECTTodo(String nombre, File ruta) {
        File tabla = new File(ruta + "/" + nombre + ".csv");

        if (!tabla.exists()) {
            throw new RuntimeException("Excepción: El archivo que deseas leer esta vacio");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(tabla));
            String columnas = br.readLine();

            //Comprobar que el archivo no este vacio
            if (columnas == null) {
                throw new IOException("Excepción: el archivo a leer se encuentra vacio");
            } else {
                String[] columnasRecibidas = columnas.split(",");

                for (int i = 0; i < columnasRecibidas.length; i++) {
                    System.out.print(columnasRecibidas[i].trim() + " ");
                }
                System.out.println();

                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length == columnasRecibidas.length) {
                        for (int i = 0; i < datos.length; i++) {
                            System.out.print(datos[i].trim() + " ");
                        }
                        System.out.println();
                    } else {
                        br.close();
                        throw new RuntimeException("Excepción: la tabla tiene más datos de los que puede almacenar");
                    }
                }

                br.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void dropTabla(String nombre, File ruta) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int eleccion;
        File tabla;
        if (!ruta.exists()) {
            throw new RuntimeException("La ruta no existe");
        } else {
            tabla = new File(ruta + "/" + nombre + ".csv");
        }

        try {
            if (tabla.exists()) {
                System.out.println("Realmente quiere borrar la tabla " + nombre + "\n1.-Si 2.-No");
                do {
                    eleccion = br.read();
                    //Debido a que br.read esta regresando el valor del 1 del codigo ASCII
                    if (eleccion == 49) {
                        tabla.delete();
                        break;
                    }
                    //Debido a que br.read esta regresando el valor del 2 del codigo ASCII
                    else if (eleccion == 50) {
                        System.out.println("Se detuvo el proceso de borrado");
                        break;
                    }
                } while (true);
            } else {
                throw new FileNotFoundException("Excepción: La tabla " + nombre + " no existe");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String nombre, File ruta, String condicion) throws IOException {
        File tabla = new File(ruta + "/" + nombre + ".csv");

        if (!tabla.exists()) {
            throw new RuntimeException("Excepción: La tabla donde quiere borrar datos no existe");
        }

        try {

            File copia = new File(ruta + "/copia.csv");
            //Para guardar cambios en la copia
            BufferedWriter bw = new BufferedWriter(new FileWriter(copia));

            //Para leer los datos de la tabla
            BufferedReader br = new BufferedReader(new FileReader(tabla));

            String columnas = br.readLine();
            bw.write(columnas);
            bw.newLine();

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.trim().split(",");

                if (!cumpleCondicion(datos, condicion))
                {
                    bw.write(linea);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();

            if (!tabla.delete()) {
                throw new RuntimeException("Excepción: No puede elminiar el archivo original");
            }
            if (!copia.renameTo(tabla)) {
                throw new RuntimeException("Excepción: No se logro renombrar el archivo copia");
            }

            System.out.println("Filas eliminadas exitosamente");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    //Solo maneja iguales (id = 5)
    private boolean cumpleCondicion(String[] datos, String condicion) {
        String[] partesCondicion = condicion.split("=");

        if (partesCondicion.length != 2) {
            throw new RuntimeException("Excepción: Por el momento solo puede usar una condición y esta debe ser =");
        }

        String columna = partesCondicion[0].trim();
        String valor = partesCondicion[1].trim().replaceAll("'", "");

        //Encontrar los valores que concuerdan
        for (String dato : datos) {
            String[] partesDato = dato.trim().split(",");
            System.out.println(partesDato.length);
            if (partesDato.length == 2) {
                String nombreColuman = partesDato[0];
                String valorColuman = partesDato[1];
                if (nombreColuman.equalsIgnoreCase(columna) && valorColuman.equalsIgnoreCase(valor)) {
                    return true;

                }
            }
        }
        return false;
    }
}
