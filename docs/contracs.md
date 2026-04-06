# 📜 Contratos Oficiales del Sistema – Feedback System

Este documento define los **contratos técnicos oficiales** del sistema.
Todos los desarrolladores deben respetarlos estrictamente para garantizar la integración.

---

# 🎯 Objetivo

Permitir el desarrollo en paralelo de las épicas asegurando:

* Compatibilidad entre módulos
* Integración sin conflictos
* Código desacoplado

---

# 🧱 Principios

1. Programar contra **interfaces**, no implementaciones
2. NO modificar DTOs sin consenso del equipo
3. Respetar nombres de métodos y firmas
4. Cada épica es independiente pero compatible

---

# 📦 DTOs OFICIALES

---

## 🧾 `EvaluacionRequest`

```java
public class EvaluacionRequest {
    private int liderazgo;
    private int comunicacion;
    private int tiempo;
    private int resolucionProblemas;
    private String email;

    // getters y setters
}
```

---

## 🧾 `Feedback`

```java
public class Feedback {
    private String introduccion;
    private String desarrollo;
    private String cierre;

    // getters y setters
}
```

---

# 🔌 INTERFACES OFICIALES

---

## 🔵 ÉPICA 1 – Captura de Evaluación

### `EvaluacionInputPort`

```java
public interface EvaluacionInputPort {
    EvaluacionRequest capturarEvaluacion();
}
```

---

### `EvaluacionValidatorPort`

```java
public interface EvaluacionValidatorPort {
    void validar(EvaluacionRequest request);
}
```

📌 Reglas:

* Lanza excepción si hay error
* NO retorna boolean

---

## 🟣 ÉPICA 2 – Generación de Feedback

### `FeedbackGeneratorPort`

```java
public interface FeedbackGeneratorPort {
    Feedback generarFeedback(EvaluacionRequest request);
}
```

📌 Reglas:

* NO acepta valores nulos
* Debe ejecutar en < 1 segundo

---

### `FeedbackFormatterPort`

```java
public interface FeedbackFormatterPort {
    Feedback formatear(Feedback feedback);
}
```

📌 Reglas:

* Debe garantizar:

  * introduccion
  * desarrollo
  * cierre

---

## 🟢 ÉPICA 3 – Gestión del Feedback

### `FeedbackRepository`

```java
public interface FeedbackRepository {
    void guardar(Feedback feedback);
    Feedback obtener();
}
```

---

### `FeedbackEditorPort`

```java
public interface FeedbackEditorPort {
    Feedback editar(Feedback feedback);
}
```

---

## 🟠 ÉPICA 4 – Entrega del Feedback

### `EmailValidatorPort`

```java
public interface EmailValidatorPort {
    void validar(String email);
}
```

---

### `EmailServicePort`

```java
public interface EmailServicePort {
    void enviar(String email, Feedback feedback);
}
```

---

# 🔄 FLUJO OFICIAL DEL SISTEMA

```java
EvaluacionRequest request = input.capturarEvaluacion();

validator.validar(request);

Feedback feedback = generator.generarFeedback(request);

feedback = formatter.formatear(feedback);

feedback = editor.editar(feedback);

repository.guardar(feedback);

emailValidator.validar(request.getEmail());

emailService.enviar(request.getEmail(), feedback);
```

---

# ⚠️ CONTRATOS DE VALIDACIÓN

---

## Evaluación

* Valores entre 1 y 10
* No nulos
* No strings

---

## Email

* Formato válido: `correo@dominio.com`
* Lanza excepción si es inválido

---

# 🚨 MANEJO DE ERRORES

---

## Reglas

* NO usar `System.out.println` para errores
* Usar excepciones

---

## Excepciones sugeridas

```java
InvalidDataException
EmailNotValidException
FeedbackGenerationException
PersistenceException
```

---

# 🧪 MOCKING (DESARROLLO EN PARALELO)

Cada épica debe poder ejecutarse sola usando datos simulados.

---

## Ejemplo mock evaluación

```java
return new EvaluacionRequest(8, 7, 6, 9, "test@test.com");
```

---

## Ejemplo mock feedback

```java
return new Feedback("Intro", "Desarrollo", "Cierre");
```

---

# 🧩 REGLAS DE INTEGRACIÓN

---

## ❌ PROHIBIDO

* Cambiar nombres de métodos
* Cambiar DTOs sin consenso
* Acceder directamente a implementaciones de otras épicas

---

## ✅ OBLIGATORIO

* Usar interfaces
* Mantener firmas intactas
* Probar cada módulo de forma aislada

---

# 🌿 ESTRATEGIA DE RAMAS

```bash
feature/epica-1-input
feature/epica-2-feedback
feature/epica-3-management
feature/epica-4-email
```

---

# 🚀 DEFINICIÓN DE HECHO (Definition of Done)

Una épica se considera completa cuando:

* Implementa su interfaz
* Cumple criterios de aceptación
* Funciona con datos mock
* No rompe contratos

---

# 📌 NOTA FINAL

Este documento es la **fuente única de verdad** del sistema.

Cualquier cambio debe ser aprobado por TODO el equipo.
