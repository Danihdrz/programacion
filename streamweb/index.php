<?php
include 'conexion.php';
$result = $conn->query("SELECT * FROM usuarios");
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StreamWeb - Gestión de Usuarios</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h2>Gestión de Usuarios</h2>
        <nav>
            <a href="agregar.php" class="btn">Agregar Usuario</a>
        </nav>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Correo</th>
                <th>Edad</th>
                <th>Plan Base</th>
                <th>Duración</th>
                <th>Acciones</th>
            </tr>
            <?php while ($row = $result->fetch_assoc()) { ?>
            <tr>
                <td><?php echo $row['id']; ?></td>
                <td><?php echo $row['nombre']; ?></td>
                <td><?php echo $row['apellidos']; ?></td>
                <td><?php echo $row['correo']; ?></td>
                <td><?php echo $row['edad']; ?></td>
                <td><?php echo $row['plan_base']; ?></td>
                <td><?php echo $row['duracion']; ?></td>
                <td>
                    <a href="editar.php?id=<?php echo $row['id']; ?>" class="btn edit">Editar</a>
                    <a href="eliminar.php?id=<?php echo $row['id']; ?>" class="btn delete">Eliminar</a>
                </td>
            </tr>
            <?php } ?>
        </table>
    </div>
</body>
</html>
