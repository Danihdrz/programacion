package view;

import model.ClienteOtaku;
import model.ProductoOtaku;
import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    private Scanner scanner;

    public InterfazConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n=== AKINABARA MARKET ===");
        System.out.println("1. Gestión de Productos");
        System.out.println("2. Gestión de Clientes");
        System.out.println("3. Asistente IA");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarMenuProductos() {
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Añadir Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar Producto por ID");
        System.out.println("4. Actualizar Producto");
        System.out.println("5. Eliminar Producto");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarMenuClientes() {
        System.out.println("\n--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Añadir Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Buscar Cliente por ID");
        System.out.println("4. Actualizar Cliente");
        System.out.println("5. Eliminar Cliente");
        System.out.println("0. Volver");
        System.out.print("Seleccione una opción: ");
    }

    public ProductoOtaku solicitarDatosProducto() {
        System.out.println("\n--- Añadir Nuevo Producto ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();
        
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());
        
        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    public void mostrarProductos(List<ProductoOtaku> productos) {
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (ProductoOtaku producto : productos) {
                System.out.println(producto);
            }
        }
    }

    public int solicitarIdProducto() {
        System.out.print("\nIngrese el ID del producto: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void mostrarProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.println("\n--- DETALLES DEL PRODUCTO ---");
            System.out.println(producto);
        } else {
            System.out.println("Producto no encontrado.");
        }
    }


    // Métodos nuevos para clientes
    public ClienteOtaku solicitarDatosCliente() {
        System.out.println("\n--- Añadir Nuevo Cliente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        return new ClienteOtaku(nombre, email, telefono);
    }

    public void mostrarClientes(List<ClienteOtaku> clientes) {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (ClienteOtaku cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    public int solicitarIdCliente() {
        System.out.print("\nIngrese el ID del cliente: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void mostrarCliente(ClienteOtaku cliente) {
        if (cliente != null) {
            System.out.println("\n--- DETALLES DEL CLIENTE ---");
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
    
    public void mostrarMenuIA() {
        System.out.println("\n--- ASISTENTE DE IA ---");
        System.out.println("1. Generar descripción de producto");
        System.out.println("2. Sugerir categoría para producto");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    // Métodos comunes
    public void mostrarMensaje(String mensaje) {}
    public void mostrarError(String error) {}
    public Scanner getScanner() { return scanner; }
    public void cerrar() { scanner.close(); }
}