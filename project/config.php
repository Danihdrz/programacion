<?php
$servername = "localhost";
$username = "root";
$password = "1234"; 
$dbname = "gestion_tareas"; 

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}
?>

