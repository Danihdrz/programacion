<?php
include 'conexion.php';
$id = $_GET['id'];
$result = $conn->query("SELECT * FROM usuarios WHERE id=$id");
$row = $result->fetch_assoc();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Usuario</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h2>Editar Usuario</h2>
        <form method="POST" action="procesar_editar.php" class="form-box">
            <input type="hidden" name="id" value="<?php echo $row['id']; ?>">
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<?php echo $row['nombre']; ?>" required>
            
            <label>Apellidos:</label>
            <input type="text" name="apellidos" value="<?php echo $row['apellidos']; ?>" required>
            
            <label>Correo:</label>
            <input type="email" name="correo" value="<?php echo $row['correo']; ?>" required>
            
            <label>Edad:</label>
            <input type="number" name="edad" value="<?php echo $row['edad']; ?>" required>
            
            <label>Plan Base:</label>
            <select name="plan_base">
                <option value="Básico" <?php if($row['plan_base']=='Básico') echo 'selected'; ?>>Básico</option>
                <option value="Estándar" <?php if($row['plan_base']=='Estándar') echo 'selected'; ?>>Estándar</option>
                <option value="Premium" <?php if($row['plan_base']=='Premium') echo 'selected'; ?>>Premium</option>
            </select>
            
            <label>Duración:</label>
            <select name="duracion">
                <option value="Mensual" <?php if($row['duracion']=='Mensual') echo 'selected'; ?>>Mensual</option>
                <option value="Anual" <?php if($row['duracion']=='Anual') echo 'selected'; ?>>Anual</option>
            </select>
            
            <button type="submit" name="editar" class="btn edit">Actualizar Usuario</button>
        </form>
        <a href="index.php" class="btn">Volver</a>
    </div>
</body>
</html>

