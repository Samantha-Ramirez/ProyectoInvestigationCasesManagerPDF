# Guía de Estilos

## 🛠 Convenciones de Nombres

Todo el código técnico (clases, variables y métodos) debe escribirse en **inglés**.

* **Clases y Componentes Swing:** Utilizar `PascalCase`.
    * *Ejemplo:* `UserLoginFrame`, `CustomButton`, `DatabaseConnector`.
* **Variables y Funciones:** Utilizar `camelCase`.
    * *Ejemplo:* `userName`, `getUserData()`, `isInputValid`.
* **Archivos y Módulos:** Utilizar `PascalCase`.
    * *Ejemplo:* `ViewsUsuarios.java`, `AuthService.java`.

---

## 📝 Convención de Commits

Los mensajes de commit deben redactarse en **español** siguiendo la estructura:
`[Acción]: Descripción en infinitivo`

| Acción | Propósito |
| :--- | :--- |
| **add** | Nueva funcionalidad o componente. |
| **fix** | Corrección de errores o bugs. |
| **refactor** | Mejora de código sin cambiar funcionalidad. |
| **docs** | Cambios en la documentación (README, comentarios). |

**Ejemplos:**
* `add: Agregar validación al formulario de registro`
* `fix: Corregir error de renderizado en el JTable`
* `docs: Actualizar instrucciones de instalación`

---

## 💻 Formato y Estructura

Para mantener un código limpio y legible, aplica las siguientes reglas:

* **Indentación:** 4 espacios.
* **Longitud de línea:** Máximo **120 caracteres**.
* **Comentarios:** Deben escribirse en **español** y clasificarse en:
    1.  **De "Por qué":** Justificar decisiones lógicas complejas.
    2.  **De advertencia:** Alertar sobre posibles errores (ej. `// ADVERTENCIA: Este método bloquea el EDT`).
    3.  **Tareas pendientes:** Usar el prefijo `// TODO:` para mejoras futuras.

---
