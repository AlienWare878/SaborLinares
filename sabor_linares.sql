-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.4.3 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando datos para la tabla sabor_linares.lugares: ~1 rows (aproximadamente)
INSERT INTO `lugares` (`id`, `nombre`, `descripcion`, `categoria`, `direccion`, `latitud`, `longitud`, `horario`, `precio`, `imagen`, `estado`) VALUES
	(5, 'Sabor Linares Demo', 'Lugar de prueba para comprobar la conexión entre MySQL, DAO, Servlet y JSP.', 'OTRO', 'Linares, Chile', -35.8467000, -71.5931000, '10:00 - 20:00', '$$$', NULL, 'APROBADO');

-- Volcando datos para la tabla sabor_linares.resenas: ~0 rows (aproximadamente)

-- Volcando datos para la tabla sabor_linares.usuarios: ~3 rows (aproximadamente)
INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `contrasena`, `rol`) VALUES
	(2, 'Juan', 'Juan.prueba@SaborLinares.cl', '122345', 'ADMIN'),
	(3, 'Manuel', 'M.guerrero@Gmail.com', '123123', 'USUARIO'),
	(4, 'Luis Morales', 'Luis.Morales@luis.com', '123123', 'USUARIO');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
