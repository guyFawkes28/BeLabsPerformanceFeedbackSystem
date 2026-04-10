# 📄 ESPECIFICACIÓN FUNCIONAL Y PLAN DE DESARROLLO  
## BeLabs Performance Feedback System  

**Versión:** 1.1.0  
**Estado:** Documento Consolidado  

---

# 1. 🎯 VISIÓN DEL PRODUCTO

El sistema **BeLabs Performance Feedback System** tiene como objetivo permitir a los evaluadores generar, editar y entregar feedback estructurado de forma rápida, consistente y accionable, a partir de evaluaciones de desempeño en sesiones prácticas.

---

# 2. 👥 ACTORES

- **Evaluador (Principal):** Ingresa datos, genera y envía feedback  
- **Participante (Secundario):** Recibe el feedback por correo  

---

# 3. 🧩 ÉPICAS Y HISTORIAS DE USUARIO

## 🔵 ÉPICA 1: Captura de Evaluación

### HU-01: Ingreso de evaluación  
Como evaluador quiero ingresar calificaciones para registrar el desempeño  

### HU-02: Validación de datos  
Como sistema quiero validar los datos para asegurar integridad  

---

## 🟣 ÉPICA 2: Generación de Feedback

### HU-03: Generación automática  
Como evaluador quiero generar feedback automáticamente para ahorrar tiempo  

### HU-04: Estructuración del feedback  
Como evaluador quiero un comentario organizado para que sea claro  

---

## 🟢 ÉPICA 3: Gestión del Feedback

### HU-05: Edición  
Como evaluador quiero editar el feedback para personalizarlo  

### HU-06: Persistencia  
Como evaluador quiero guardar resultados para consultarlos  

---

## 🟠 ÉPICA 4: Entrega del Feedback

### HU-07: Validación de email  
Como sistema quiero validar correos para evitar errores  

### HU-08: Envío de feedback  
Como evaluador quiero enviar el feedback para que el participante lo reciba  

---

# 4. ✅ CRITERIOS DE ACEPTACIÓN

## HU-01 / HU-02

- ✅ Acepta números entre 1 y 10 en todos los campos  
- ❌ Rechaza campos vacíos  
- ❌ Rechaza letras o símbolos  
- ❌ Rechaza valores fuera del rango  

---

## HU-03

- ✅ Genera feedback automáticamente  
- ✅ Clasifica habilidades:
  - 8–10 → Alto  
  - 5–7 → Medio  
  - 1–4 → Bajo  
- ❌ No genera feedback sin datos  
- ❌ Falla si tarda más de 1 segundo  

---

## HU-04

- ✅ Incluye:
  - Introducción  
  - Desarrollo  
  - Cierre  
- ❌ Es inválido si falta alguna sección  

---

## HU-05

- ✅ Permite editar todo el texto  
- ✅ Guarda cambios correctamente  
- ❌ Error si no guarda cambios  

---

## HU-06

- ✅ Guarda feedback correctamente  
- ✅ Permite recuperar información  
- ❌ Error si se pierde información  

---

## HU-07

- ✅ Acepta formato: correo@dominio.com  
- ❌ Rechaza correos inválidos  

---

## HU-08

- ✅ Envía correo correctamente  
- ✅ Muestra confirmación  
- ❌ Muestra error si falla conexión  
- ❌ Error si el correo llega incompleto  

---

# 5. 📊 MATRIZ DE TRAZABILIDAD (RTM)

| Requisito | Historia | Validación |
|----------|--------|-----------|
| RF-01 | HU-01 | Entrada válida 1–10 |
| RF-01 | HU-02 | Validación correcta |
| RF-02 | HU-03 | Generación automática |
| RF-04 | HU-04 | Estructura correcta |
| RF-03 | HU-05 | Edición funcional |
| RF-05 | HU-06 | Persistencia |
| RF-07 | HU-07 | Validación email |
| RF-06 | HU-08 | Envío correcto |

---

# 6. 📊 BACKLOG PRIORIZADO

| Prioridad | Historia |
|----------|--------|
| 1 | HU-01 |
| 2 | HU-03 |
| 3 | HU-04 |
| 4 | HU-05 |
| 5 | HU-06 |
| 6 | HU-07 |
| 7 | HU-08 |

---

# 7. 🧮 ESTIMACIÓN (STORY POINTS)

| HU | SP |
|----|----|
| HU-01 | 3 |
| HU-02 | 2 |
| HU-03 | 5 |
| HU-04 | 3 |
| HU-05 | 3 |
| HU-06 | 5 |
| HU-07 | 2 |
| HU-08 | 8 |

---

# 8. 🚀 PLAN DE SPRINTS

## 🟢 Sprint 1 – MVP

**Objetivo:** Generar feedback automático  

- HU-01  
- HU-02  
- HU-03  
- HU-04  

---

## 🔵 Sprint 2

**Objetivo:** Sistema usable  

- HU-05  
- HU-06  

---

## 🟣 Sprint 3

**Objetivo:** Entrega final  

- HU-07  
- HU-08  

---

# 9. 🧪 CASOS DE PRUEBA (RESUMEN)

- Validación de entrada  
- Generación de feedback  
- Clasificación por niveles  
- Edición de texto  
- Persistencia  
- Validación de email  
- Envío de correo  

---

# 10. 📄 TEST PLAN (RESUMEN)

**Objetivo:** Validar correcto funcionamiento del sistema  

### Incluye:
- Pruebas funcionales  
- Pruebas negativas  
- Pruebas de rendimiento  
- Pruebas de integración  

### Criterios de éxito:
- 100% casos críticos aprobados  
- Sin errores bloqueantes  

---

# 11. ⚠️ RIESGOS

| Riesgo | Impacto |
|-------|--------|
| Fallo en envío de email | Alto |
| Mala definición de reglas | Alto |
| Pérdida de datos | Medio |

---

# 12. 📏 DEFINICIONES SCRUM

## ✅ Definition of Done

- Funcionalidad implementada  
- Probada  
- Cumple criterios  
- Integrada  

---

## ✅ Definition of Ready

- Historia clara  
- Criterios definidos  
- Estimada  
- Sin ambigüedad  

---

# 🎯 CONCLUSIÓN

Este documento permite:

- Desarrollo organizado  
- Trazabilidad completa  
- Validación clara  
- Trabajo en equipo eficiente  
- Base para proyecto real
