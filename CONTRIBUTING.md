# Guía de Estilos

## 🛠 Convenciones de Nombres

* **Clases y Componentes Swing:** Utilizar `PascalCase`.
    * *Ejemplo:* `UserLoginFrame`, `CustomButton`, `DatabaseConnector`.
* **Variables y Funciones:** Utilizar `camelCase`.
    * *Ejemplo:* `userName`, `getUserData()`, `isInputValid`.
* **Archivos y Módulos:** Utilizar `PascalCase`.
    * *Ejemplo:* `ViewsUsuarios.java`, `AuthService.java`.

---

## 💻 Formato y Estructura

Para mantener un código limpio y legible, aplica las siguientes reglas:

* **Indentación:** 4 espacios.
* **Longitud de línea:** Máximo **120 caracteres**.
* **Comentarios:** Deben escribirse en **español** y clasificarse en:
    1.  **De "Por qué":** Justificar decisiones lógicas complejas.
    2.  **De advertencia:** Alertar sobre posibles errores (ej. `// ADVERTENCIA: `).
    3.  **Tareas pendientes:** Sugerir mejoras futuras (ej. `// TODO: `).
    4.  **Aplicación de patrones:** Especificar el uso de algún patrón de diseño (ej. `// PDyF: `).

---

## 🌳 Estrategia de Ramas

Utilizamos el modelo **GitHub Flow**:

* **Rama `main`**: Es la rama principal y persistente. Siempre debe contener código estable, compilable y funcional.
* **Ramas `feature`**: Ramas secundarias y temporales para implementar requerimientos o corregir errores sin afectar `main`.

### Convención de Nombres de Ramas
Las ramas secundarias se nombran en **español**:
> `[Tipo]/UC#-título`

* **`feature/`**: Para nuevas funcionalidades.
* **`fix/`**: Para correcciones de errores críticos.

**Ejemplos:**
* `feature/UC3-publicar-oferta`
* `fix/UC1-error-perfil`

---

## 🔄 Ciclo de Vida de una Rama

Para cada funcionalidad, sigue estos pasos:
1.  **Crear una rama:** Derivar siempre desde `main`.
2.  **Agregar Commits:** Realizar commits frecuentes siguiendo la guía de estilos de este documento.
3.  **Abrir un Pull Request (PR):** Al finalizar, abre un PR en GitHub hacia `main`.
4.  **Discutir y revisar:** El equipo revisará el código para asegurar la calidad y cumplimiento de requisitos.
5.  **Merge:** Una vez aprobado, se fusionan los cambios en `main` y se elimina la rama secundaria.

---

## 📝 Convención de Commits

Los commit se redactan en **español**:
> **UC# [Acción]: Descripción en infinitivo**

* `add`: Nueva función o componente.
* `fix`: Corrección de errores.
* `refactor`: Mejora de código.
* `docs`: Documentación.
* `chore`: Tareas varias.

**Ejemplos:** `UC3 add: Agregar validación al formulario`, `docs: Modificar README`.

---
