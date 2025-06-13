package view.swing;

import controller.SwingController;
import model.ProductoOtaku;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductosPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final SwingController controller;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActualizar;

    public ProductosPanel(SwingController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        modeloTabla = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        btnAgregar.addActionListener(this::agregarProducto);
        btnEditar.addActionListener(this::editarProducto);
        btnEliminar.addActionListener(this::eliminarProducto);
        btnActualizar.addActionListener(this::actualizarLista);
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        cargarDatos();
    }

    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        try {
            List<ProductoOtaku> productos = controller.obtenerTodosLosProductos();
            for (ProductoOtaku producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto(ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Agregar Nuevo Producto");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        
        JTextField txtNombre = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Categoría:"));
        dialog.add(txtCategoria);
        dialog.add(new JLabel("Precio:"));
        dialog.add(txtPrecio);
        dialog.add(new JLabel("Stock:"));
        dialog.add(txtStock);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            String nombre = txtNombre.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String precioText = txtPrecio.getText().trim();
            String stockText = txtStock.getText().trim();
            
            if (nombre.isEmpty() || categoria.isEmpty() || precioText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double precio = Double.parseDouble(precioText);
                int stock = Integer.parseInt(stockText);
                if (precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(dialog, "Precio y stock deben ser valores no negativos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                ProductoOtaku nuevo = new ProductoOtaku(nombre, categoria, precio, stock);
                controller.agregarProducto(nuevo);
                cargarDatos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Producto agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Precio y Stock deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editarProducto(ActionEvent e) {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        ProductoOtaku producto = controller.obtenerProductoPorId(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Producto");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        
        JTextField txtNombre = new JTextField(producto.getNombre());
        JTextField txtCategoria = new JTextField(producto.getCategoria());
        JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        JTextField txtStock = new JTextField(String.valueOf(producto.getStock()));
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Categoría:"));
        dialog.add(txtCategoria);
        dialog.add(new JLabel("Precio:"));
        dialog.add(txtPrecio);
        dialog.add(new JLabel("Stock:"));
        dialog.add(txtStock);
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            String nombre = txtNombre.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String precioText = txtPrecio.getText().trim();
            String stockText = txtStock.getText().trim();
            
            if (nombre.isEmpty() || categoria.isEmpty() || precioText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                double precio = Double.parseDouble(precioText);
                int stock = Integer.parseInt(stockText);
                if (precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(dialog, "Precio y stock deben ser valores no negativos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                producto.setNombre(nombre);
                producto.setCategoria(categoria);
                producto.setPrecio(precio);
                producto.setStock(stock);
                
                controller.actualizarProducto(producto);
                cargarDatos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Producto actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Precio y Stock deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void eliminarProducto(ActionEvent e) {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar este producto?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarProducto(id);
            cargarDatos();
            JOptionPane.showMessageDialog(this, "Producto eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarLista(ActionEvent e) {
        cargarDatos();
        JOptionPane.showMessageDialog(this, "Lista de productos actualizada", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}