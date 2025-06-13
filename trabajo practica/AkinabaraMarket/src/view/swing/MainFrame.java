package view.swing;

import controller.SwingController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final SwingController controller;
    private JTabbedPane tabbedPane;

    public MainFrame(SwingController controller) {
        this.controller = controller;
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        setTitle("Akinabara Market - Gestión de Tienda Otaku");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        // Panel de productos
        ProductosPanel productosPanel = new ProductosPanel(controller);
        tabbedPane.addTab("Productos", productosPanel);
        
        // Panel de clientes
        ClientesPanel clientesPanel = new ClientesPanel(controller);
        tabbedPane.addTab("Clientes", clientesPanel);
        
        // Panel para el asistente IA
        IAPanel iaPanel = new IAPanel(controller);
        tabbedPane.addTab("Asistente IA", iaPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Menú superior
        crearMenuBar();
    }

    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, 
                "Akinabara Market v1.0\nCon integración de IA", 
                "Acerca de", 
                JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    public void mostrar() {
        setVisible(true);
    }
}