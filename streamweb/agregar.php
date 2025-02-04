<?php
include 'conexion.php';
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Usuario</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h2>Agregar Usuario</h2>
        <form method="POST" action="procesar_agregar.php" class="form-box">
            <label>Nombre:</label>
            <input type="text" name="nombre" required>
            
            <label>Apellidos:</label>
            <input type="text" name="apellidos" required>
            
            <label>Correo:</label>
            <input type="email" name="correo" required>
            
            <label>Edad:</label>
            <input type="number" name="edad" required>
            
            <label>Plan Base:</label>
            <select name="plan_base">
                <option value="Básico">Básico</option>
                <option value="Estándar">Estándar</option>
                <option value="Premium">Premium</option>
            </select>
            
            <label>Duración:</label>
            <select name="duracion">
                <option value="Mensual">Mensual</option>
                <option value="Anual">Anual</option>
            </select>
            
            <button type="submit" name="agregar" class="btn">Agregar Usuario</button>
        </form>
        <a href="index.php" class="btn">Volver</a>
    </div>
</body>
</html>
