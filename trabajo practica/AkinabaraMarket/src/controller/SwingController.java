package controller;

import dao.ClienteDAO;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;
import view.swing.MainFrame;
import service.LLmService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SwingController {
    private final MainFrame mainFrame;
    private final ProductoDAO productoDAO;
    private final ClienteDAO clienteDAO;
    private final LLmService llmService;

    public SwingController() {
        ProductoDAO tempProductoDAO = null;
        ClienteDAO tempClienteDAO = null;
        MainFrame tempMainFrame = null;

        try {
            System.out.println("Attempting to initialize ProductoDAO...");
            tempProductoDAO = new ProductoDAO();
            System.out.println("ProductoDAO initialized successfully. Reference: " + tempProductoDAO);

            System.out.println("Attempting to initialize ClienteDAO...");
            tempClienteDAO = new ClienteDAO();
            System.out.println("ClienteDAO initialized successfully. Reference: " + tempClienteDAO);

            System.out.println("Attempting to initialize MainFrame...");
            tempMainFrame = new MainFrame(this);
            System.out.println("MainFrame initialized successfully. Reference: " + tempMainFrame);
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
            this.productoDAO = tempProductoDAO;
            this.clienteDAO = tempClienteDAO;
            this.mainFrame = tempMainFrame;
            throw new RuntimeException("Initialization failed due to: " + e.getMessage(), e);
        }

        this.productoDAO = tempProductoDAO;
        this.clienteDAO = tempClienteDAO;
        this.mainFrame = tempMainFrame;

        System.out.println("Final productoDAO reference: " + this.productoDAO);
        System.out.println("Final clienteDAO reference: " + this.clienteDAO);
        System.out.println("Final mainFrame reference: " + this.mainFrame);
		this.llmService = new LLmService();
    }

    public void iniciar() {
        if (mainFrame != null) {
            mainFrame.mostrar();
        } else {
            throw new IllegalStateException("MainFrame is not initialized");
        }
    }

    public static void main(String[] args) {
        try {
            new SwingController().iniciar();
        } catch (Exception e) {
            System.err.println("Application startup failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Métodos para productos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        if (productoDAO == null) {
            System.err.println("productoDAO is null");
            return new ArrayList<>();
        }
        try {
            return productoDAO.obtenerTodosLosProductos();
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void agregarProducto(ProductoOtaku nuevo) {
        if (productoDAO == null) {
            System.err.println("productoDAO is null");
            return;
        }
        try {
            productoDAO.agregarProducto(nuevo);
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
        }
    }

    public ProductoOtaku obtenerProductoPorId(int id) {
        if (productoDAO == null) {
            System.err.println("productoDAO is null");
            return null;
        }
        try {
            return productoDAO.obtenerProductoPorId(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            return null;
        }
    }

    public void actualizarProducto(ProductoOtaku producto) {
        if (productoDAO == null) {
            System.err.println("productoDAO is null");
            return;
        }
        try {
            productoDAO.actualizarProducto(producto);
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(int id) {
        if (productoDAO == null) {
            System.err.println("productoDAO is null");
            return;
        }
        try {
            productoDAO.eliminarProducto(id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    // Métodos para clientes
    public List<ClienteOtaku> obtenerTodosLosClientes() {
        if (clienteDAO == null) {
            System.err.println("clienteDAO is null");
            return new ArrayList<>();
        }
        try {
            return clienteDAO.obtenerTodosLosClientes();
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void agregarCliente(ClienteOtaku nuevo) {
        if (clienteDAO == null) {
            System.err.println("clienteDAO is null");
            return;
        }
        try {
            clienteDAO.agregarCliente(nuevo);
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    public ClienteOtaku obtenerClientePorId(int id) {
        if (clienteDAO == null) {
            System.err.println("clienteDAO is null");
            return null;
        }
        try {
            return clienteDAO.obtenerClientePorId(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
            return null;
        }
    }

    public void actualizarCliente(ClienteOtaku cliente) {
        if (clienteDAO == null) {
            System.err.println("clienteDAO is null");
            return;
        }
        try {
            clienteDAO.actualizarCliente(cliente);
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminarCliente(int id) {
        if (clienteDAO == null) {
            System.err.println("clienteDAO is null");
            return;
        }
        try {
            clienteDAO.eliminarCliente(id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
    public String generarDescripcionIA(ProductoOtaku producto) throws Exception {
        return llmService.generarDescripcionProducto(producto);
    }

    public String sugerirCategoriaIA(String nombreProducto) throws Exception {
        return llmService.sugerirCategoria(nombreProducto);
    }

    public List<ProductoOtaku> obtenerTodosLosProductos1() throws SQLException {
        return productoDAO.obtenerTodosLosProductos();
    }

	public LLmService getLlmservice() {
		return llmService;
	}
}