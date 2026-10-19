# CumbreApp - Sistema de Gestión de Préstamos

Este repositorio contiene el código fuente de **CumbreApp**, un aplicativo desarrollado en Java para la empresa **Cumbre Colombia**. El sistema está diseñado para optimizar el control, inventario y seguimiento de los préstamos y ventas de equipos de seguridad para trabajo en alturas.

---

## 🎯 Alcance del Proyecto

- **Gestión de Inventario:** Control de disponibilidad, estado y actualización de existencias de los equipos de seguridad en tiempo real.
- **Control de Préstamos:** Registro de asignación de elementos a clientes, gestionando tiempos de entrega y validación de stock.
- **Devoluciones e Historial:** Seguimiento de entregas pendientes, procesamiento de devoluciones y registro detallado de los movimientos durante la jornada.

---

## 👥 Integrantes del Grupo

| Nombre Completo | Documento | Correo Electrónico |
|-----------------|-----------|--------------------|
| Julian Camilo Zamora Escobar | TI 1013123929 | jzamorae@unal.edu.co |
| María José Infante Avellaneda | CC 1000574372 | mainfantea@unal.edu.co |
| Juan David Cuvides Garzon | CC 1013101334 | jcuvides@unal.edu.co |
| Juan Sebastian Junco Gonzalez | TI 1031817684 | jjuncog@unal.edu.co |
| Sara Alejandra Calderón Santafé | CC 1034662634 | sacalderons@unal.edu.co |
| Andrés Felipe Romero Aguiar | TI 1076742013 | aromeroag@unal.edu.co |

---

## 📌 Estado del Proyecto

El proyecto ha completado satisfactoriamente su **Entrega 1**. Durante esta fase, se estructuró el modelo organizando el código en los paquetes `app` y `models`, con el fin de establecer una estructura MVC en futuras entregas. Actualmente, se dispone de un prototipo funcional en consola que permite gestionar el catálogo, verificar el inventario, realizar ventas, registrar préstamos y generar el balance de cierre de jornada.

---

## 🚀 Instrucciones de Ejecución

La interfaz de la aplicación se ejecuta íntegramente a través de la consola.

### Requisitos Previos
- **Java Development Kit (JDK):** Versión 17 o superior.
- **Git:** Para la clonación del repositorio (opcional si se descarga el código comprimido en .zip).

### Opción 1: Ejecución desde un Entorno de Desarrollo (IDE - Recomendado)
1. Clone el repositorio en su entorno local ejecutando el siguiente comando:
   ```bash
   git clone <https://github.com/Jjuncog/CumbreApp>
   ```
2. Abra el directorio del proyecto en su editor de código (por ejemplo, Visual Studio Code con el *Extension Pack for Java*, o Eclipse).
3. En el explorador de archivos del proyecto, diríjase a la ruta `src/app/` y abra el archivo `Main.java`.
4. Ejecute el archivo principal. 
5. Podrá interactuar con el menú del sistema directamente desde la terminal integrada de su editor.

### Opción 2: Ejecución manual desde la Terminal
Si requiere compilar y ejecutar el proyecto sin depender de un IDE:
1. Abra una consola de comandos y ubíquese en el directorio raíz del proyecto.
2. Compile los archivos fuente respetando la estructura de paquetes. Los archivos `.class` generados se guardarán en el directorio `bin`:
   ```bash
   javac -d bin src/app/*.java src/models/*.java
   ```
3. Ejecute la aplicación llamando a la clase principal:
   ```bash
   java -cp bin app.Main
   ```
