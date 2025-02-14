
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar y Ver Tareas</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
    <h1>Agregar Nueva Tarea</h1>
    <form action="add_task.php" method="post">
        <label for="titulo">Título de la Tarea:</label>
        <input type="text" id="titulo" name="titulo" required><br>
        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="descripcion"></textarea><br>
        <button type="submit">Agregar Tarea</button>
    </form>

    <?php
    session_start();
    if (!isset($_SESSION['user_id'])) {
        header("Location: login.php");
        exit;
    }

    require 'config.php';

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $titulo = $_POST['titulo'];
        $descripcion = $_POST['descripcion'];
        $usuario_id = $_SESSION['user_id'];

        $stmt = $conn->prepare("INSERT INTO tareas (usuario_id, titulo, descripcion) VALUES (?, ?, ?)");
        $stmt->bind_param("iss", $usuario_id, $titulo, $descripcion);
        $stmt->execute();

        echo "Tarea agregada exitosamente.<br>";
    }

    echo "<h2>Tareas Pendientes</h2>";
    $usuario_id = $_SESSION['user_id'];
    $stmt = $conn->prepare("SELECT * FROM tareas WHERE usuario_id = ? AND completada = FALSE");
    $stmt->bind_param("i", $usuario_id);
    $stmt->execute();
    $result = $stmt->get_result();

    while ($row = $result->fetch_assoc()) {
        echo $row['titulo'] . " - " . $row['descripcion'] . "<br>";
    }
    ?>

    <a href="dashboard.php">Volver al Panel de Control</a>
</body>
</html>



