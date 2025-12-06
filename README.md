# EcoGestor-Proyecto-POO
Simulador de gestión de recursos naturales

Integrantes: 
- Daniela Sofía Murcia Castillo
- María Alejandra Castro Acuña
- Daniel Santiago Valderrama Corredor
- Juliana Andrea Hincapié Sopo
- Juan Pablo Carrión Munar
- Juan Diego Londoño Ramírez



# 🧠 Reglas de Trabajo en Equipo — GitFlow

Este documento define la estrategia de colaboración para el equipo de desarrollo usando **Git** y el flujo **GitFlow**.  
---

## 📌 Objetivos

- Ordenar el desarrollo colaborativo  
- Evitar conflictos innecesarios  
- Facilitar integración y testing  
- Mantener ramas limpias y funcionales  

---

## 🌱 Estructura de Ramas

Usaremos el modelo **GitFlow simplificado**:

- `main` → rama principal (producción)  
- `develop` → integración y pruebas (estable)  
- `feature/xxx` → nuevas funcionalidades  
- `fix/xxx` → corrección de errores  
- `release/xxx` → versiones candidatas para producción  
- `hotfix/xxx` → parches urgentes sobre main
- `Actualización/xxx` → versiones aprobadas para muestra final

---

## 🔧 Reglas por tipo de rama

### 🟢 main
- Contiene el código en producción (estable)  
- Solo se actualiza desde `release` o `hotfix`  
- Nunca se hace commit directo  

### 🟣 develop
- Rama de integración continua (último código aprobado)  
- Se actualiza por *merge* de features y fixes  
- De aquí se parte para release  

### 🌿 feature/mi-funcionalidad
- Crear a partir de `develop`  
- Nombrar en minúsculas y con guiones: `feature/busqueda-vuelos`  
- Debe contener solo **UNA** funcionalidad  
- Merge a `develop` solo después de pruebas  

### 🐞 fix/nombre-del-fix
- Para bugs encontrados en `develop`  
- Merge a `develop`

---

### 🚑 hotfix/nombre-del-hotfix
- Correcciones urgentes en producción  
- Se crean desde `main`  
- Se hace merge tanto a `main` como a `develop`

---

### 🧩 release/v1.0.0
- Versión candidata para producción  
- Se crean desde `develop`  
- Se prueba, documenta y luego *merge* a `main` y `develop`

---
