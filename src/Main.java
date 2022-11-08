import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        System.out.print("Indica la ruta de un directorio: ");
        String temp = sc.next();

        String ruta = temp.replace("\\", "/");

        System.out.println(ruta);

        try {
            File file = new File(ruta);

            encabezado(file);
            numeroArchivos(file);
            numeroCarpetas(file);
            listarCarpetas(file);
            listarArchivos(file);

            File resumen = new File(ruta + "/resumen.txt");

            if (resumen.delete()) System.gc();
            Files.write(resumen.toPath(), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Imposible escribir en el directorio.");
        }
    }

    static void encabezado(File file) {
        sb.append(file.getName()).append(" | ").append(new Date(file.lastModified()));
        sb.append(System.lineSeparator()).append(System.lineSeparator());
    }

    static void numeroArchivos(File file) {
        File[] archivos = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if (archivos == null || archivos.length == 0) {
            sb.append("No se ha encontrado archivos.").append(System.lineSeparator());
        } else {
            sb.append("El total de archivos es: ").append(archivos.length).append(System.lineSeparator());
        }
    }

    static void numeroCarpetas(File file) {
        File[] carpetas = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (carpetas == null || carpetas.length == 0) {
            sb.append("No se ha encontrado carpetas.").append(System.lineSeparator());
        } else {
            sb.append("El total de carpetas es: ").append(carpetas.length).append(System.lineSeparator());
        }
    }

    static void listarCarpetas(File file) {
        File[] carpetas = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (carpetas == null || carpetas.length == 0) {
            sb.append("Imposible listar carpetas").append(System.lineSeparator());
        } else {
            sb.append(System.lineSeparator());
            sb.append("CARPETAS:").append(System.lineSeparator());
            for (File carpeta : carpetas) {
                sb.append(carpeta.getName()).append(" | ");
                sb.append(carpeta.getPath()).append(" | ");
                File[] archivosSubCarpeta = carpeta.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile();
                    }
                });
                if (archivosSubCarpeta == null || archivosSubCarpeta.length == 0) {
                    sb.append("No hay archivos.");
                } else {
                    sb.append("Archivos: ").append(archivosSubCarpeta.length);
                }
                sb.append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }
    }

    static void listarArchivos(File file) {
        File[] archivos = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if (archivos == null || archivos.length == 0) {
            sb.append("Imposible listar archivos").append(System.lineSeparator());
        } else {
            sb.append("ARCHIVOS:").append(System.lineSeparator());
            for (File archivo : archivos) {
                sb.append(archivo.getName()).append(" | ");
                sb.append(archivo.getPath());
                sb.append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }
    }
}