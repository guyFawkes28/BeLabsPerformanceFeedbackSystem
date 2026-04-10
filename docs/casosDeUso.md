# 🧪 Casos de Uso para Testing

## BeLabs Performance Feedback System

---

# 📌 Convenciones

* Sistema: Script de consola
* Formato: Gherkin (Given / When / Then)
* Validación estricta de entrada
* Todos los campos son obligatorios
* Valores permitidos: enteros de 1 a 10
* Sin decimales
* Feedback generado sin números en el texto final

---

# 🔵 HU-01 / HU-02: Captura y Validación de Evaluación

## 🎯 CU-01: Ingreso válido de evaluación

```gherkin

Feature: Captura de evaluación válida

  Scenario: Ingreso correcto de todos los parámetros
    Given que el evaluador ejecuta el script

    When ingresa valores válidos para:

      | Liderazgo | Comunicación | Tiempo | Resolución |
      | 8         | 7            | 6      | 9          |

    Then el sistema acepta los datos
    And los almacena temporalmente para procesamiento
```

---

## ❌ CU-02: Rechazo de campos vacíos

```gherkin

Feature: Validación de campos obligatorios

  Scenario: Campo vacío en la entrada
    Given que el evaluador ejecuta el script
    When deja un campo vacío
    Then el sistema rechaza la entrada
    And muestra un mensaje de error indicando campos obligatorios
```

---

## ❌ CU-03: Rechazo de valores fuera de rango

```gherkin
Feature: Validación de rango

  Scenario: Valor menor al mínimo permitido
    Given que el evaluador ejecuta el script
    When ingresa un valor menor a uno
    Then el sistema rechaza la entrada
    And muestra error de rango inválido

  Scenario: Valor mayor al máximo permitido
    Given que el evaluador ejecuta el script
    When ingresa un valor mayor a diez
    Then el sistema rechaza la entrada
    And muestra error de rango inválido
```

---

## ❌ CU-04: Rechazo de datos no numéricos

```gherkin
Feature: Validación de tipo de dato

  Scenario: Ingreso de letras
    Given que el evaluador ejecuta el script
    When ingresa texto en lugar de números
    Then el sistema rechaza la entrada
    And muestra error de tipo inválido

  Scenario: Ingreso de decimales
    Given que el evaluador ejecuta el script
    When ingresa un número decimal
    Then el sistema rechaza la entrada
    And muestra error indicando solo enteros permitidos
```

---

# 🟣 HU-03 / HU-04: Generación y Estructura del Feedback

## 🎯 CU-05: Generación de feedback completo

```gherkin
Feature: Generación automática de feedback

  Scenario: Generación con datos válidos
    Given que existen valores válidos para todos los parámetros
    When el sistema genera el feedback
    Then debe generar cinco bloques de contenido:
      | Liderazgo |
      | Comunicación |
      | Tiempo |
      | Resolución |
      | Combinación |
    And el contenido debe ser claro, profesional y accionable
    And no debe contener números
```

---

## 🎯 CU-06: Clasificación correcta alto/bajo

```gherkin
Feature: Clasificación de habilidades

  Scenario: Clasificación como alto
    Given un valor mayor o igual al umbral definido
    When se procesa el parámetro
    Then debe clasificarse como fortaleza

  Scenario: Clasificación como bajo
    Given un valor menor al umbral definido
    When se procesa el parámetro
    Then debe clasificarse como oportunidad de mejora
```

---

## 🎯 CU-07: Generación de feedback individual

```gherkin
Feature: Feedback por parámetro

  Scenario: Feedback positivo
    Given un parámetro clasificado como fortaleza
    When se genera el feedback
    Then debe reforzar la habilidad de forma constructiva

  Scenario: Feedback de mejora
    Given un parámetro clasificado como bajo
    When se genera el feedback
    Then debe incluir una recomendación clara y accionable
```

---

## 🎯 CU-08: Generación de feedback por combinación

```gherkin
Feature: Feedback por combinación de habilidades

  Scenario: Vector de desempeño generado correctamente
    Given los valores procesados
    When se binarizan según las reglas
    Then se genera un vector de cuatro posiciones

  Scenario: Asignación correcta del mensaje de combinación
    Given un vector específico de habilidades
    When se evalúa la combinación
    Then el sistema devuelve el mensaje correspondiente
```

---

## ❌ CU-09: Error si no hay datos

```gherkin
Feature: Validación previa a generación

  Scenario: Intento de generar feedback sin datos
    Given que no existen datos ingresados
    When se intenta generar feedback
    Then el sistema muestra un error
    And no genera contenido
```

---

## 🎯 CU-10: Estructura del feedback

```gherkin
Feature: Estructura del contenido

  Scenario: Feedback estructurado correctamente
    Given que el feedback ha sido generado
    When se revisa el contenido
    Then debe contener:
      | Introducción |
      | Desarrollo |
      | Cierre |
```

---

# 🟢 HU-05 / HU-06: Edición y Persistencia

## 🎯 CU-11: Edición de feedback

```gh

Feature: Edición de contenido

  Scenario: Modificación del feedback
    Given que el feedback ha sido generado
    When el evaluador edita el contenido
    Then el sistema permite modificar cualquier sección
```

---

## 🎯 CU-12: Guardado del feedback

```gherkin
Feature: Persistencia de datos

  Scenario: Guardado exitoso
    Given que el feedback ha sido editado o generado
    When se guarda la información
    Then el sistema almacena el contenido correctamente

  Scenario: Sobrescritura de datos
    Given que existe un feedback previo
    When se guarda un nuevo feedback
    Then el sistema reemplaza el anterior
```

---

## ❌ CU-13: Error en persistencia

```gherkin
Feature: Manejo de errores de guardado

  Scenario: Fallo al guardar
    Given un problema en el sistema de almacenamiento
    When se intenta guardar el feedback
    Then el sistema muestra un error
```

---

# 🟠 HU-07 / HU-08: Validación y Envío de Email

## 🎯 CU-14: Validación de email

```gherkin
Feature: Validación de correo electrónico

  Scenario: Email válido
    Given un correo con formato correcto
    When se valida
    Then el sistema lo acepta

  Scenario: Email inválido
    Given un correo con formato incorrecto
    When se valida
    Then el sistema lo rechaza
    And muestra un error
```

---

## 🎯 CU-15: Envío de feedback por SMTP

```gherkin
Feature: Envío de correo

  Scenario: Envío exitoso
    Given un email válido
    And un feedback generado
    When se envía el correo
    Then el sistema envía el mensaje correctamente
    And muestra confirmación al usuario
```

---

## ❌ CU-16: Error en envío

```gherkin
Feature: Manejo de errores de envío

  Scenario: Fallo de conexión SMTP
    Given un problema de conexión
    When se intenta enviar el correo
    Then el sistema muestra un error de envío

  Scenario: Contenido incompleto
    Given un feedback incompleto
    When se envía el correo
    Then el sistema detecta el error
    And evita el envío
```

---

