<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Página de Inicio</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
    <div class="container">
        <h1>Bienvenido al Sistema de Gestión de Tareas</h1>
        <p>Por favor, regístrate o inicia sesión para continuar.</p>
        <a href="register.php">Registrarse</a> | <a href="login.php">Iniciar Sesión</a>

        <?php
        session_start();
        if (isset($_SESSION['user_id'])) {
            header("Location: dashboard.php");
            exit;
        }
        ?>
    </div>
</body>
</html>

