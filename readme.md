# 🏥 Sistema de Gestión Geriátrica

Sistema de consola desarrollado en Java para la administración integral de una residencia para adultos mayores, implementando arquitectura modular por capas (Modelos, DTOs, DAOs, Vistas y Utilidades) y persistencia en archivos de texto plano.

---

## 🚀 Características Principales

* **Gestión Completa (CRUD):** Administración de habitaciones, residentes, enfermeros, reservas y asignaciones de personal.
* **Persistencia en Archivos:** Almacenamiento local estructurado mediante archivos `.txt` delimitados por comas.
* **Robustez y Validaciones:** Control de entradas por consola para evitar caídas del sistema ante ingresos inválidos (letras, puntos, espacios vacíos).
* **Soporte UTF-8:** Configuración de salida en consola para asegurar la correcta visualización de acentos y caracteres especiales.

---

## 🛠️ Estructura del Proyecto

```text
residenciageriatrica/
│
├── Main.java                 # Punto de entrada de la aplicación
│
├── dao/                      # Capa de acceso a datos (archivos txt)
├── dtos/                     # Objetos de transferencia de datos
├── models/                   # Entidades del negocio (Estado, etc.)
├── utils/                    # Clases de utilidad (Mostrar, Mensajes, Validaciones)
└── views/                    # Interfaces de usuario por consola
    ├── MenuPrincipalView.java
    ├── HabitacionView.java
    ├── ResidenteView.java
    ├── EnfermeroView.java
    ├── ReservaView.java
    └── AsignacionView.java