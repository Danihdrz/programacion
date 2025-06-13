package view.swing;

import controller.SwingController;
import model.ProductoOtaku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class IAPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final SwingController controller;
    private JTextArea txtRespuesta;
    private JTextField txtIdProducto;
    private JTextArea txtProductoSeleccionado;
    private ProductoOtaku productoActual;

    public IAPanel(SwingController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // Panel superior con botones
        JPanel panelControles = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton btnGenerarDesc = new JButton("Generar Descripción");
        btnGenerarDesc.addActionListener(this::generarDescripcion);

        JButton btnSugerirCategoria = new JButton("Sugerir Categoría");
        btnSugerirCategoria.addActionListener(this::sugerirCategoria);

        panelControles.add(btnGenerarDesc);
        panelControles.add(btnSugerirCategoria);

        add(panelControles, BorderLayout.NORTH);

        // Área de texto principal
        txtRespuesta = new JTextArea();
        txtRespuesta.setEditable(false);
        txtRespuesta.setLineWrap(true);
        txtRespuesta.setWrapStyleWord(true);
        txtRespuesta.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(txtRespuesta), BorderLayout.CENTER);

        // Panel inferior con campo de ID
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        JPanel panelID = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelID.add(new JLabel("Ingrese ID del producto:"));

        txtIdProducto = new JTextField(10);
        panelID.add(txtIdProducto);

        JButton btnBuscar = new JButton("Cargar por ID");
        btnBuscar.addActionListener(this::cargarProductoPorID);
        panelID.add(btnBuscar);

        panelInferior.add(panelID, BorderLayout.NORTH);

        // Muestra del producto cargado
        txtProductoSeleccionado = new JTextArea(3, 40);
        txtProductoSeleccionado.setEditable(false);
        txtProductoSeleccionado.setLineWrap(true);
        txtProductoSeleccionado.setWrapStyleWord(true);
        txtProductoSeleccionado.setFont(new Font("Arial", Font.ITALIC, 13));
        txtProductoSeleccionado.setBorder(BorderFactory.createTitledBorder("Producto Seleccionado"));

        panelInferior.add(txtProductoSeleccionado, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarProductoPorID(ActionEvent e) {
        String idTexto = txtIdProducto.getText().trim();
        if (idTexto.isEmpty()) {            	
            mostrarError("Debe ingresar un ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            productoActual = controller.obtenerProductoPorId(id);  
            if (productoActual != null) {
                txtProductoSeleccionado.setText(productoActual.toString());
            } else {
                txtProductoSeleccionado.setText("No se encontró un producto con ID: " + id);
            }
        } catch (NumberFormatException ex) {
            mostrarError("ID inválido. Ingrese un número.");
        } catch (Exception ex) {
            mostrarError("Error al buscar producto: " + ex.getMessage());
        }
    }

    private void generarDescripcion(ActionEvent e) {
        if (productoActual == null) {
            mostrarError("Primero seleccione un producto por ID.");
            return;
        }

        new Thread(() -> {
            try {
                actualizarUI("Consultando a la IA... Por favor espere.", false);
                String descripcion = controller.generarDescripcionIA(productoActual);
                SwingUtilities.invokeLater(() -> {
                    txtRespuesta.setText("=== DESCRIPCIÓN GENERADA ===\n\n" + descripcion);
                    int opcion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea guardar esta descripción en el producto?",
                        "Guardar Descripción",
                        JOptionPane.YES_NO_OPTION);

                    if (opcion == JOptionPane.YES_OPTION) {
                        mostrarMensaje("Descripción guardada con éxito");
                    }
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    mostrarError("Error al generar descripción: " + ex.getMessage()));
            }
        }).start();
    }

    private void sugerirCategoria(ActionEvent e) {
        String nombreProducto = JOptionPane.showInputDialog(
            this,
            "Ingrese el nombre del producto:",
            "Sugerir Categoría",
            JOptionPane.QUESTION_MESSAGE);

        if (nombreProducto == null || nombreProducto.trim().isEmpty()) {
            return;
        }

        new Thread(() -> {
            try {
                actualizarUI("Consultando sugerencia de categoría...", false);
                String categoria = controller.sugerirCategoriaIA(nombreProducto.trim());
                SwingUtilities.invokeLater(() -> {
                    txtRespuesta.setText("=== CATEGORÍA SUGERIDA ===\n\n" +
                        "Para el producto: " + nombreProducto + "\n" +
                        "Categoría recomendada: " + categoria);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    mostrarError("Error al obtener sugerencia: " + ex.getMessage()));
            }
        }).start();
    }

    private void actualizarUI(String mensaje, boolean limpiar) {
        SwingUtilities.invokeLater(() -> {
            if (limpiar) {
                txtRespuesta.setText("");
            }
            txtRespuesta.setText(mensaje);
        });
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
