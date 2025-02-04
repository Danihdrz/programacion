<?php
include 'conexion.php';

if (isset($_POST['agregar'])) {
    $nombre = $_POST['nombre'];
    $apellidos = $_POST['apellidos'];
    $correo = $_POST['correo'];
    $edad = $_POST['edad'];
    $plan_base = $_POST['plan_base'];
    $duracion = $_POST['duracion'];
    
    $sql = "INSERT INTO usuarios (nombre, apellidos, correo, edad, plan_base, duracion) VALUES ('$nombre', '$apellidos', '$correo', '$edad', '$plan_base', '$duracion')";
    if ($conn->query($sql) === TRUE) {
        header("Location: index.php");
    } else {
        echo "Error: " . $conn->error;
    }
}
?>