# 📘 Belabs Feedback System — Reglas de Generación de Feedback (v1)

## 🎯 Objetivo

Definir las reglas para la generación automática de feedback en la primera versión del sistema Belabs, utilizando un enfoque de **lógica híbrida**:

1. Feedback individual por parámetro evaluado
2. Feedback adicional basado en combinaciones de desempeño

---

## 📊 Parámetros Evaluados

El sistema evalúa 4 dimensiones principales:

* Liderazgo
* Comunicación Asertiva
* Manejo del Tiempo
* Resolución de Problemas

Cada parámetro recibe una calificación numérica entre **0 y 100**.

---

## 🔀 Simplificación de Evaluación (Binarización)

Para reducir la complejidad de combinaciones, las calificaciones se transforman a valores binarios:

* **Alto (1)** → Calificación ≥ 70
* **Bajo (0)** → Calificación < 70

---

## 🧠 Lógica de Generación de Feedback

El feedback final está compuesto por dos niveles:

### 1. 🧩 Feedback Individual

Se genera un mensaje independiente por cada parámetro, basado en su valor binario:

| Estado     | Descripción         |
| ---------- | ------------------- |
| Alto (≥70) | Refuerza fortalezas |
| Bajo (<70) | Sugiere mejora      |

Ejemplo:

* Liderazgo (Alto): "Demuestra una capacidad sólida para guiar al equipo..."
* Comunicación (Bajo): "Se recomienda mejorar la claridad en la transmisión de ideas..."

---

### 2. 🔗 Feedback por Combinación

Se genera un feedback adicional dependiendo de la combinación de los 4 parámetros binarizados.

Dado que cada parámetro puede ser:

* 0 (Bajo)
* 1 (Alto)

Total de combinaciones:

[
2^4 = 16
]

---

## 🧮 Representación de Combinaciones

Las combinaciones se representan como un vector binario en el siguiente orden:

```
[Liderazgo, Comunicación, Tiempo, Resolución]
```

Ejemplo:

* `[1, 1, 0, 1]` → Alto, Alto, Bajo, Alto
* `[0, 0, 0, 0]` → Todos bajos
* `[1, 1, 1, 1]` → Todos altos

---

## 🧾 Lista de Combinaciones (16 Casos)

| Código | L | C | T | R | Interpretación                      |
| ------ | - | - | - | - | ----------------------------------- |
| C1     | 0 | 0 | 0 | 0 | Desempeño bajo general              |
| C2     | 0 | 0 | 0 | 1 | Solo resolución destacada           |
| C3     | 0 | 0 | 1 | 0 | Solo tiempo destacado               |
| C4     | 0 | 0 | 1 | 1 | Buen manejo operativo               |
| C5     | 0 | 1 | 0 | 0 | Comunicación destacada              |
| C6     | 0 | 1 | 0 | 1 | Buen análisis y comunicación        |
| C7     | 0 | 1 | 1 | 0 | Organización y comunicación         |
| C8     | 0 | 1 | 1 | 1 | Perfil colaborativo fuerte          |
| C9     | 1 | 0 | 0 | 0 | Liderazgo sin soporte técnico       |
| C10    | 1 | 0 | 0 | 1 | Liderazgo con resolución            |
| C11    | 1 | 0 | 1 | 0 | Liderazgo organizado                |
| C12    | 1 | 0 | 1 | 1 | Liderazgo operativo fuerte          |
| C13    | 1 | 1 | 0 | 0 | Liderazgo comunicativo              |
| C14    | 1 | 1 | 0 | 1 | Perfil estratégico                  |
| C15    | 1 | 1 | 1 | 0 | Alto rendimiento con mejora técnica |
| C16    | 1 | 1 | 1 | 1 | Desempeño sobresaliente             |

---

## 🧱 Estructura del Feedback Final

El feedback final se construye concatenando:

```
Feedback Final =
    Feedback(Liderazgo) +
    Feedback(Comunicación) +
    Feedback(Tiempo) +
    Feedback(Resolución) +
    Feedback(Combinación)
```

---

## ⚙️ Reglas Clave

* Siempre generar feedback individual para los 4 parámetros
* Siempre generar feedback de combinación
* No usar valores numéricos en el mensaje final (solo interpretación)
* Mantener tono:

  * Constructivo
  * Profesional
  * Accionable

---

## 🚀 Escalabilidad (Futuro)

En versiones futuras se podrá:

* Volver a granularidad 0–100 con clustering
* Usar IA para generación dinámica de texto
* Añadir peso relativo por parámetro
* Incorporar historial del evaluado

---

## ✅ Ejemplo de Flujo

Entrada:

```
Liderazgo: 80
Comunicación: 75
Tiempo: 60
Resolución: 90
```

Transformación:

```
[1, 1, 0, 1] → C14
```

Salida:

* 4 feedback individuales
* 1 feedback de combinación (perfil estratégico)

---

## 🧩 Nota Final

Este enfoque híbrido permite:

* Escalabilidad controlada
* Coherencia en los mensajes
* Personalización sin complejidad exponencial

---
