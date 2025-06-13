package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.DatabaseConnection;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;
import service.LLmService;
import util.SetupDatos;
import view.InterfazConsola;

public class MainApp {
    private InterfazConsola vista;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private LLmService llmService;

    public MainApp() {
        this.vista = new InterfazConsola();
        this.productoDAO =new ProductoDAO();
        this.clienteDAO = new ClienteDAO();
        this.llmService = new LLmService();
        SetupDatos.cargarDatosIniciales();
    }

    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            vista.mostrarMenuPrincipal();
            int opcion = Integer.parseInt(vista.getScanner().nextLine());
            
            try {
                switch (opcion) {
                    case 1:
                        gestionarProductos();
                        break;
                    case 2:
                        gestionarClientes();
                        break;
                    case 3:
                        usarAsistenteIA();
                        break;
                    case 0:
                        salir = true;
                        DatabaseConnection.closeConnection();
                        vista.mostrarMensaje("Saliendo del sistema...");
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
        
        vista.cerrar();
    }

    private void gestionarProductos() throws SQLException {
        boolean volver = false;
        Scanner scanner = vista.getScanner();
        
        while (!volver) {
            vista.mostrarMenuProductos();
            String input = scanner.nextLine().trim();
            
            try {
                int opcion = Integer.parseInt(input);
                
                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        listarProductos();
                        break;
                    case 3:
                        buscarProductoPorId();
                        break;
                    case 4:
                        actualizarProducto();
                        break;
                    case 5:
                        eliminarProducto();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        vista.mostrarError("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                vista.mostrarError("Por favor ingrese un número válido.");
            }
        }
    }

    private void gestionarClientes() throws SQLException {
        boolean volver = false;
        
        while (!volver) {
            vista.mostrarMenuClientes();
            int opcion = Integer.parseInt(vista.getScanner().nextLine());
            
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorId();
                    break;
                case 4:
                    actualizarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarError("Opción no válida");
            }
        }
    }
    
    private void usarAsistenteIA() {
        boolean volver = false;
        
        while (!volver) {
            vista.mostrarMenuIA();
            String input = vista.getScanner().nextLine().trim();
            
            try {
                int opcion = Integer.parseInt(input);
                
                switch (opcion) {
                    case 1:
                        generarDescripcionIA();
                        break;
                    case 2:
                        sugerirCategoriaIA();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            } catch (NumberFormatException e) {
                vista.mostrarError("Por favor ingrese un número válido (0, 1 o 2)");
            } catch (SQLException e) {
                vista.mostrarError("Error de base de datos: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarError("Error inesperado: " + e.getMessage());
            }
        }
    }

    private void generarDescripcionIA() throws SQLException {
        int id = vista.solicitarIdProducto();
        ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
        
        if (producto != null) {
            vista.mostrarMensaje("\nConsultando a la IA... Por favor espere.");
            
            // Mostrar feedback de que la consulta está en progreso
            System.out.print("Procesando");
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                try {
                    Thread.sleep(500); // Pequeña pausa para efecto visual
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println();
            
            String descripcion = llmService.generarDescripcionProducto(producto);
            
            // Mostrar la respuesta con formato
            vista.mostrarMensaje("\n╔════════════════════════════════════╗");
            vista.mostrarMensaje("║       DESCRIPCIÓN GENERADA       ║");
            vista.mostrarMensaje("╚════════════════════════════════════╝");
            vista.mostrarMensaje(descripcion);
            vista.mostrarMensaje("────────────────────────────────────");
            
            // Opción para guardar 
            System.out.print("\n¿Desea guardar esta descripción? (s/n): ");
            String respuesta = vista.getScanner().nextLine().toLowerCase();
            
            if (respuesta.equals("s")) {
                // Aquí podrías implementar la lógica para guardar
                vista.mostrarMensaje("(Función de guardado no implementada aún)");
            }
        } else {
            vista.mostrarError("Producto no encontrado");
        }
    }

    private void sugerirCategoriaIA() {
        System.out.print("\nIngrese el nombre del producto: ");
        String nombreProducto = vista.getScanner().nextLine();
        
        vista.mostrarMensaje("\nConsultando sugerencia de categoría...");
        
        String categoriaSugerida = llmService.sugerirCategoria(nombreProducto);
        
        // Mostrar la respuesta con formato destacado
        vista.mostrarMensaje("\n┌──────────────────────────────┐");
        vista.mostrarMensaje("│  CATEGORÍA RECOMENDADA:     │");
        vista.mostrarMensaje("├──────────────────────────────┤");
        vista.mostrarMensaje("│ " + String.format("%-28s", categoriaSugerida) + " │");
        vista.mostrarMensaje("└──────────────────────────────┘");
    }

    private void agregarProducto() throws SQLException {
        ProductoOtaku producto = vista.solicitarDatosProducto();
        productoDAO.agregarProducto(producto);
        vista.mostrarMensaje("Producto añadido con éxito. ID: " + producto.getId());
    }

    private void listarProductos() throws SQLException {
        List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();
        vista.mostrarProductos(productos);
    }

    private void buscarProductoPorId() throws SQLException {
        int id = vista.solicitarIdProducto();
        ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
        vista.mostrarProducto(producto);
    }

    private void actualizarProducto() throws SQLException {
        int id = vista.solicitarIdProducto();
        ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
        
        if (producto != null) {
            vista.mostrarMensaje("Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");
            
            System.out.print("Nombre [" + producto.getNombre() + "]: ");
            String nombre = vista.getScanner().nextLine();
            if (!nombre.isEmpty()) producto.setNombre(nombre);
            
            System.out.print("Categoría [" + producto.getCategoria() + "]: ");
            String categoria = vista.getScanner().nextLine();
            if (!categoria.isEmpty()) producto.setCategoria(categoria);
            
            System.out.print("Precio [" + producto.getPrecio() + "]: ");
            String precioStr = vista.getScanner().nextLine();
            if (!precioStr.isEmpty()) producto.setPrecio(Double.parseDouble(precioStr));
            
            System.out.print("Stock [" + producto.getStock() + "]: ");
            String stockStr = vista.getScanner().nextLine();
            if (!stockStr.isEmpty()) producto.setStock(Integer.parseInt(stockStr));
            
            if (productoDAO.actualizarProducto(producto)) {
                vista.mostrarMensaje("Producto actualizado con éxito");
            } else {
                vista.mostrarError("No se pudo actualizar el producto");
            }
        } else {
            vista.mostrarError("Producto no encontrado");
        }
    }

    private void eliminarProducto() throws SQLException {
        int id = vista.solicitarIdProducto();
        if (productoDAO.eliminarProducto(id)) {
            vista.mostrarMensaje("Producto eliminado con éxito");
        } else {
            vista.mostrarError("No se pudo eliminar el producto");
        }
    }

    // Métodos nuevos para clientes
    private void agregarCliente() throws SQLException {
        ClienteOtaku cliente = vista.solicitarDatosCliente();
        clienteDAO.agregarCliente(cliente);
        vista.mostrarMensaje("Cliente añadido con éxito. ID: " + cliente.getId());
    }

    private void listarClientes() throws SQLException {
        List<ClienteOtaku> clientes = clienteDAO.obtenerTodosLosClientes();
        vista.mostrarClientes(clientes);
    }

    private void buscarClientePorId() throws SQLException {
        int id = vista.solicitarIdCliente();
        ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
        vista.mostrarCliente(cliente);
    }

    private void actualizarCliente() throws SQLException {
        int id = vista.solicitarIdCliente();
        ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
        
        if (cliente != null) {
            vista.mostrarMensaje("Ingrese los nuevos datos (deje en blanco para mantener el valor actual):");
            
            System.out.print("Nombre [" + cliente.getNombre() + "]: ");
            String nombre = vista.getScanner().nextLine();
            if (!nombre.isEmpty()) cliente.setNombre(nombre);
            
            System.out.print("Email [" + cliente.getEmail() + "]: ");
            String email = vista.getScanner().nextLine();
            if (!email.isEmpty()) cliente.setEmail(email);
            
            System.out.print("Teléfono [" + cliente.getTelefono() + "]: ");
            String telefono = vista.getScanner().nextLine();
            if (!telefono.isEmpty()) cliente.setTelefono(telefono);
            
            if (clienteDAO.actualizarCliente(cliente)) {
                vista.mostrarMensaje("Cliente actualizado con éxito");
            } else {
                vista.mostrarError("No se pudo actualizar el cliente");
            }
        } else {
            vista.mostrarError("Cliente no encontrado");
        }
    }

    private void eliminarCliente() throws SQLException {
        int id = vista.solicitarIdCliente();
        if (clienteDAO.eliminarCliente(id)) {
            vista.mostrarMensaje("Cliente eliminado con éxito");
        } else {
            vista.mostrarError("No se pudo eliminar el cliente");
        }
    }

    public static void main(String[] args) {
        new MainApp().iniciar();
    }

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}
}