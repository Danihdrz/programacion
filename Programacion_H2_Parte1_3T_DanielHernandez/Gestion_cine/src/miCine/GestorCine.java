package miCine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GestorCine {

    static final String url = "jdbc:mysql://localhost:3306/cine_DanielHernandez";
    static final String usuario = "root";
    static final String contraseña = "1234";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            
            System.out.println("1. Ver películas");
            System.out.println("2. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    verPeliculas();
                    break;
                case 2:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 2);

        scanner.close();
    }

    public static void verPeliculas() {
        String query = "SELECT p.codigo, p.titulo, p.director, p.genero, p.duracion, c.descripcion " +
                       "FROM peliculas p JOIN clasificaciones c ON p.id_clasificacion = c.id";

        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-6s %-30s %-25s %-15s %-10s %-25s%n",
                    "Código", "Título", "Director", "Género", "Duración", "Clasificación");

            while (rs.next()) {
                System.out.printf("%-6s %-30s %-25s %-15s %-10d %-25s%n",
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getString("descripcion"));
            }

        } catch (Exception e) {
            System.out.println("Error al mostrar películas: " + e.getMessage());
        }
    }
}
