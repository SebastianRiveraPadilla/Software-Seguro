# Proyecto RRHH
# Desarrollo de Software Seguro

Este proyecto es una aplicación de escritorio desarrollada en Java Swing para la gestión de funcionarios y sus relaciones.

# Autores
* Ximena Restrepo Grisales
* Sebastian Andres Rivera Padilla

## Tecnologías utilizadas
* Java (Swing)
* MySQL
* Patrón DAO y manejo de Excepciones

## Cómo ejecutar el proyecto

1. **Base de Datos:** Abrir MySQL y ejecutar el script `BaseDatos_RRHH.sql` que se encuentra en la raíz de este repositorio. Esto creará la base de datos `rrhh_institucion` con sus tablas y datos de prueba.
2. **Configurar Credenciales:** Abrir el archivo `src/connection/ConnectionDB.java` y en la línea 11, cambiar el texto `"CLAVE_AQUI"` por su contraseña real de MySQL.
3. **Ejecutar:** Abrir el archivo `src/Main.java` y dale clic al botón de "Run" en VS Code.