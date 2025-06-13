package view.swing;

import controller.SwingController;
import model.ClienteOtaku;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClientesPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final SwingController controller;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActualizar;

    public ClientesPanel(SwingController controller) {
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
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Fecha Registro");
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        btnAgregar.addActionListener(this::agregarCliente);
        btnEditar.addActionListener(this::editarCliente);
        btnEliminar.addActionListener(this::eliminarCliente);
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
            List<ClienteOtaku> clientes = controller.obtenerTodosLosClientes();
            for (ClienteOtaku cliente : clientes) {
                modeloTabla.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getEmail(),
                    cliente.getTelefono(),
                    cliente.getFechaRegistro()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCliente(ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Agregar Nuevo Cliente");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            
            if (nombre.isEmpty() || email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(dialog, "Por favor, ingrese un nombre y un email válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ClienteOtaku nuevo = new ClienteOtaku(nombre, email, telefono);
            controller.agregarCliente(nuevo);
            cargarDatos();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Cliente agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editarCliente(ActionEvent e) {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        ClienteOtaku cliente = controller.obtenerClientePorId(id);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Cliente");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        
        JTextField txtNombre = new JTextField(cliente.getNombre());
        JTextField txtEmail = new JTextField(cliente.getEmail());
        JTextField txtTelefono = new JTextField(cliente.getTelefono());
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            
            if (nombre.isEmpty() || email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(dialog, "Por favor, ingrese un nombre y un email válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            
            controller.actualizarCliente(cliente);
            cargarDatos();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Cliente actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void eliminarCliente(ActionEvent e) {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar este cliente?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarCliente(id);
            cargarDatos();
            JOptionPane.showMessageDialog(this, "Cliente eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarLista(ActionEvent e) {
        cargarDatos();
        JOptionPane.showMessageDialog(this, "Lista de clientes actualizada", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}