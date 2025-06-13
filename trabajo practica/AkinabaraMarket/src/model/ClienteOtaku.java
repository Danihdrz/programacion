package model;

import java.time.LocalDate;

public class ClienteOtaku {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;

    public ClienteOtaku() {}

    public ClienteOtaku(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = LocalDate.now();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Email: %s | Teléfono: %s | Registro: %s", 
                            id, nombre, email, telefono, fechaRegistro);
    }
}