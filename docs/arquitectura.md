# 📦 Arquitectura del Proyecto – Feedback System

Este documento describe la estructura del proyecto, la responsabilidad de cada carpeta/clase y cómo se relaciona con las historias de usuario (HU).

---

# 🧱 Estructura del Proyecto

```
com.feedbacksystem
│
├── Main.java
├── config/
├── controller/
├── service/
├── model/
├── dto/
├── repository/
├── validator/
├── util/
└── exception/
```

---

# 🧠 Flujo General del Sistema

```
Controller → Validator → Service → Repository → EmailService
```

---

# 📂 Descripción por carpeta

---

## 🚪 `Main.java`

**Responsabilidad:**

* Punto de entrada del programa
* Orquesta el flujo general

**Debe contener:**

* Creación de objetos (services, validators, etc.)
* Ejecución del flujo principal

---

## 🎮 `controller/`

### `EvaluacionController`

**Responsabilidad:**

* Recibir datos del usuario (CLI por ahora)
* Enviar datos al sistema

**Debe hacer:**

* Crear `EvaluacionRequest`
* Llamar a `EvaluacionValidator`
* Llamar a `FeedbackService`

**NO debe hacer:**

* Validaciones complejas
* Lógica de negocio

---

## 📦 `dto/`

### `EvaluacionRequest`

**Responsabilidad:**

* Transportar datos de entrada

**Debe contener:**

* liderazgo
* comunicacion
* tiempo
* resolucionProblemas
* email

---

## 🧪 `validator/`

### `EvaluacionValidator`

**Responsabilidad:**

* Validar reglas de entrada

**Reglas:**

* Valores entre 1 y 10
* No vacíos
* No letras

---

### `EmailValidator`

**Responsabilidad:**

* Validar formato de correo

**Regla:**

* formato válido tipo `correo@dominio.com`

---

## ⚙️ `service/`

---

### `FeedbackService`

**Responsabilidad:**

* Generar feedback automático

**Debe hacer:**

* Clasificar habilidades:

  * 8–10 → Alto
  * 5–7 → Medio
  * 1–4 → Bajo

---

### `FeedbackFormatter`

**Responsabilidad:**

* Estructurar el feedback

**Debe generar:**

* Introducción
* Desarrollo
* Cierre

---

### `FeedbackManagerService`

**Responsabilidad:**

* Editar feedback
* Coordinar guardado

---

### `EmailService`

**Responsabilidad:**

* Enviar feedback

**Versión 1:**

* Simulación con `System.out.println`

---

## 🧬 `model/`

---

### `Evaluacion`

**Responsabilidad:**

* Representar datos validados

---

### `Feedback`

**Responsabilidad:**

* Representar el resultado final

**Debe contener:**

* introduccion
* desarrollo
* cierre

---

## 💾 `repository/`

---

### `FeedbackRepository` (INTERFAZ)

**Responsabilidad:**

* Definir contrato de persistencia

---

### `InMemoryFeedbackRepository`

**Responsabilidad:**

* Guardar datos en memoria

**Futuro:**

* Base de datos
* Archivos

---

## 🛠 `util/`

**Responsabilidad:**

* Funciones auxiliares

**Ejemplos:**

* formateo de texto
* helpers

---

## ⚠️ `exception/`

**Responsabilidad:**

* Manejo de errores personalizados

**Ejemplos:**

* InvalidDataException
* EmailNotValidException

---

# 📋 Relación con Historias de Usuario

---

## 🔵 ÉPICA 1: Captura de Evaluación

### HU-01: Ingreso de evaluación

* `EvaluacionController`
* `EvaluacionRequest`

### HU-02: Validación de datos

* `EvaluacionValidator`

---

## 🟣 ÉPICA 2: Generación de Feedback

### HU-03: Generación automática

* `FeedbackService`

### HU-04: Estructuración del feedback

* `FeedbackFormatter`
* `Feedback`

---

## 🟢 ÉPICA 3: Gestión del Feedback

### HU-05: Edición

* `FeedbackManagerService`

### HU-06: Persistencia

* `FeedbackRepository`
* `InMemoryFeedbackRepository`

---

## 🟠 ÉPICA 4: Entrega del Feedback

### HU-07: Validación de email

* `EmailValidator`

### HU-08: Envío de feedback

* `EmailService`

---

# 🚨 Reglas importantes del proyecto

---

## ❌ NO HACER

* Mezclar lógica en el controller
* Validar dentro del service
* Guardar datos directamente en controller
* Imprimir desde lógica de negocio

---

## ✅ HACER

* Separar responsabilidades
* Usar clases pequeñas y claras
* Validar antes de procesar
* Mantener flujo limpio

---

# 🚀 Flujo completo esperado

1. Usuario ingresa datos
2. Controller crea DTO
3. Validator valida datos
4. Service genera feedback
5. Formatter estructura texto
6. Repository guarda información
7. EmailService envía resultado

---

# 🧩 Notas finales

* Este proyecto sigue una arquitectura por capas
* Permite escalar fácilmente a API REST
* Facilita pruebas unitarias
* Mejora trabajo en equipo

---

# ✅ Próximo paso recomendado

Implementar:

1. `EvaluacionRequest`
2. `EvaluacionValidator`
3. Flujo básico en `Main`

---
