-- 1. Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS akinabara_db;

-- 2. Usar la base de datos
USE akinabara_db;

-- 3. Crear tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Crear tabla de clientes (para el bonus)
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Insertar datos iniciales de productos
INSERT INTO productos (nombre, categoria, precio, stock) VALUES
('Figura de Anya Forger', 'Figura', 59.95, 8),
('Manga Chainsaw Man Vol.1', 'Manga', 9.99, 20),
('Póster Studio Ghibli Colección', 'Póster', 15.50, 15);

-- 6. Insertar datos iniciales de clientes (para el bonus)
INSERT INTO clientes (nombre, email, telefono) VALUES
('Hinata Shoyo', 'hinata@karasuno.com', '123456789'),
('Kageyama Tobio', 'kageyama@karasuno.com', '987654321');

-- 7. Crear usuario para la aplicación (opcional pero recomendado)
CREATE USER IF NOT EXISTS 'akinabara_app'@'localhost' IDENTIFIED BY 'password_seguro';
GRANT ALL PRIVILEGES ON akinabara_db.* TO 'akinabara_app'@'localhost';
FLUSH PRIVILEGES;
