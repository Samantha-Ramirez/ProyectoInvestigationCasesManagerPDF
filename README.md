# Investigation Cases Manager

## 📄 Descripción del Proyecto

Es un sistema que permite registrar y controlar información de casos de investigación según su origen y asimismo contar con un modulo de reporte que permite realizar un gestión de este tipo de información.

---

## 💻 Tecnologías Utilizadas

El proyecto utiliza el siguiente stack tecnológico:
*   **Lenguaje:** Java 21
*   **Framework:** Java Swing
*   **Base de Datos:** SQLite

---

## 🚀 Cómo iniciar el proyecto

Siga estos pasos para configurar, compilar y ejecutar la aplicación en su entorno local.

### 1. Requisitos Previos
* **Java JDK 17** o superior.
* **Maven 3.6+** (Gestor de dependencias).
* **SQLite** (Motor de base de datos embebido).

### 2. Configuración de la Base de Datos
El proyecto incluye un archivo `InvestigationCasesManager.db` pre-configurado.
* Asegúrese de que el archivo tenga permisos de lectura/escritura.
* Usuarios de prueba disponibles:
    * **Administrador:** Cédula `31307714`
    * **Investigador:** Cédula `30243278`

### 3. Compilación e Instalación
Desde la raíz del proyecto, ejecute el siguiente comando para descargar las dependencias (JDBC SQLite, etc.):

```bash
mvn clean install
```

### 4. Ejecución
Para iniciar el sistema, ejecute la clase principal:
```bash
mvn exec:java -Dexec.mainClass="com.ucv.investigationcasesmanager.Main"
```

---

## 👥 Equipo de Desarrollo (Dev Team)

Proyecto realizado para la asignatura "Patrones de Diseño y Frameworks" (Semestre 2025-2, UCV).

**Developers:**
*   **María Miranda**
*   **Samantha Ramírez**

--------------------------------------------------------------------------------
