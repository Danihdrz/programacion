package util;

import dao.ClienteDAO;
import dao.ProductoDAO;
import model.ClienteOtaku;
import model.ProductoOtaku;
import java.sql.SQLException;

public class SetupDatos {
    public static void cargarDatosIniciales() {
        try {
            ProductoDAO productoDAO = new ProductoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            
            // Verificar si la base de datos está vacía
            if (productoDAO.obtenerTodosLosProductos().isEmpty()) {
                // Insertar productos de ejemplo
                productoDAO.agregarProducto(new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8));
                productoDAO.agregarProducto(new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20));
                productoDAO.agregarProducto(new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15));
            }
            
            if (clienteDAO.obtenerTodosLosClientes().isEmpty()) {
                // Insertar clientes de ejemplo
                clienteDAO.agregarCliente(new ClienteOtaku("Hinata Shoyo", "hinata@karasuno.com", "123456789"));
                clienteDAO.agregarCliente(new ClienteOtaku("Kageyama Tobio", "kageyama@karasuno.com", "987654321"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
        }
    }
}