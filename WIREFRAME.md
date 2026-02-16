# 📐 Wireframe – Nova Servicios App

Este documento define la estructura visual inicial de la aplicación según las tareas HT01–HT05.

---

# 🏠 Pantalla Principal (Dashboard)

Diseño basado en Grid de 2 columnas con Material Cards.

[ Logo Nova ]

Bienvenido a Nova Servicios
Soluciones técnicas a domicilio

| Servicios | Registrar |
| Online | Contacto |


---

## 🎨 Características Visuales

- Fondo: #F5F7FA
- Cards blancas
- Radius: 16dp
- Elevation: 4dp
- Espaciado basado en múltiplos de 8
- Icono superior (48dp)
- Texto centrado (16sp Medium)

---

# 🔄 Flujo de Navegación

SplashActivity
↓
MainActivity
↓
| RegistroActivity
| ConsultaActivity
| OnlineServicesActivity
| ContactoActivity


---

# 📱 Pantalla Registro (HT03)

Campos:

- Nombre del servicio
- Fecha
- Tipo
- Botón Guardar

Funcionalidad:
- Insertar datos en SQLite

---

# 📋 Pantalla Consulta (HT03)

- Lista de servicios almacenados
- Operaciones CRUD

---

# 🌐 Pantalla Online (HT04)

- Conexión a API REST
- Consumo JSON
- Visualización en RecyclerView
- Manejo de errores

---

# 🎯 Objetivo del Diseño

- Mantener simplicidad
- Escalabilidad progresiva
- Cumplir requerimientos de la guía
- Integrar principios de UI/UX modernos

# 👨‍💻 Autor

Miguel Ramos Alarcón  
SENATI | Docente de Desarrollo Web
---
