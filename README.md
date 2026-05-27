# Robot Management System 🤖

Aplicación Java para gestionar robots categorizados utilizando operaciones CRUD, JSON y CSV. Proyecto realizado para el segundo parcial de Programación 2 (POO - Programación Orientada a Objetos).

## 📋 Descripción del Proyecto

Sistema de gestión de laboratorio de robots que permite crear, leer, actualizar y eliminar robots de dos categorías diferentes. La aplicación utiliza una interfaz gráfica moderna construida con **JavaFX** y persiste los datos en archivos JSON. Además, almacena automáticamente aquellos robots con bajo nivel de energía en archivos CSV para su seguimiento.

## 🎯 Características Principales

### Gestión de Robots
- **Dos tipos de robots**:
  - **DOMESTICO**: Robots domésticos con cantidad de tareas configurables
  - **INDUSTRIAL**: Robots industriales con capacidad de carga máxima (kg)

- **Operaciones CRUD**:
  - ✅ **Create (Crear)**: Agregar nuevos robots al sistema
  - ✅ **Read (Leer)**: Visualizar todos los robots registrados
  - ✅ **Update (Actualizar)**: Modificar datos de robots existentes
  - ✅ **Delete (Eliminar)**: Remover robots del sistema

### Características Técnicas
- **Almacenamiento en JSON**: Todos los robots se guardan en el archivo `ArchivoRobots` en formato JSON
- **Exportación a CSV**: Los robots con **nivel de energía menor al 20%** se guardan automáticamente en `robotEnergia.csv` para seguimiento de bajo nivel energético
- **Interfaz Gráfica (GUI)**: Desarrollada con **JavaFX** y diseñada utilizando **Scene Builder**
- **Validación de Datos**:
  - Campos requeridos no vacíos
  - Nivel de energía entre 0 y 100
  - Número de serie no negativo
  - Evita la creación de robots duplicados (por número de serie)

### Manejo de Excepciones Personalizado
La aplicación utiliza excepciones custom para un control robusto:
- `CamposVaciosException`: Campos sin llenar
- `NumeroSerieNegativoException`: Número de serie inválido
- `ValoresFueraDelLimiteException`: Valores fuera de rango permitido
- `RobotEncontradoException`: Intento de crear robot duplicado
- `ReconstructorObjetosException`: Error al reconstruir objetos desde JSON

## 🏗️ Arquitectura del Proyecto

### Estructura de Paquetes

```
src/segundaparcialmarchettaagustin/
├── SegundaParcialMarchettaAgustin.java    # Clase principal (Entry point)
├── entidades/                              # Clases de modelo
│   ├── Robot.java                         # Clase abstracta base
│   ├── Domestico.java                     # Subtipo: robot doméstico
│   ├── Industrial.java                    # Subtipo: robot industrial
│   ├── CSVEscritor.java                   # Interfaz para serialización CSV
│   ├── CSVParseador.java                  # Interfaz para deserialización CSV
│   ├── ReconstruirObjeto.java             # Interfaz para reconstrucción desde JSON
│   └── jsonSerializacion.java             # Interfaz para serialización JSON
├── controller/                            # Controladores JavaFX
│   ├── ViewController.java                # Controlador principal
│   ├── FormularioController.java          # Controlador de formulario (agregar/editar)
│   └── RobotsenergiaController.java       # Controlador de vista de bajo nivel energético
├── utils/                                 # Utilidades
│   ├── JsonUtils.java                     # Manejo de archivo JSON
│   └── CSVUtils.java                      # Manejo de archivos CSV
└── exception/                             # Excepciones personalizadas
    ├── CamposVaciosException.java
    ├── NumeroSerieNegativoException.java
    ├── ValoresFueraDelLimiteException.java
    ├── RobotEncontradoException.java
    └── ReconstructorObjetosException.java

src/view/                                  # Archivos FXML (interfaces JavaFX)
├── view.fxml                              # Ventana principal (listado y CRUD)
├── formulario.fxml                        # Formulario para crear/editar robots
└── robotsenergia.fxml                     # Ventana de robots con bajo nivel energético
```

### Clases Principales

#### **Robot (Clase Abstracta)**
Clase base que define la estructura común de todos los robots:
- `tipo`: Identificador del tipo (DOMESTICO o INDUSTRIAL)
- `nombre`: Nombre del robot
- `nivelEnergia`: Porcentaje de energía (0-100)
- `numeroSerie`: Identificador único del robot

Implementa métodos para:
- Conversión a/desde CSV
- Comparación por número de serie (equals/hashCode)
- Representación en string

#### **Domestico (Subtipo)**
Extiende Robot agregando:
- `cantidadTareas`: Número de tareas que puede realizar

#### **Industrial (Subtipo)**
Extiende Robot agregando:
- `capacidadCarga`: Capacidad máxima de carga en kg

#### **JsonUtils<T>**
Genérica para gestionar persistencia JSON:
- `guardar()`: Serializa lista de objetos a JSON
- `cargar()`: Deserializa objetos desde JSON
- Utiliza **GSON** para conversión

#### **CSVUtils**
Genérica para gestionar persistencia CSV:
- `guardarListaCSV()`: Escribe objetos a CSV
- `cargarListaCSV()`: Lee objetos desde CSV

## 🚀 Cómo Usar

### Requisitos
- **Java 11+**
- **JavaFX SDK**
- **GSON** (para serialización JSON)

### Compilación y Ejecución

```bash
# Compilar
javac -d build src/**/*.java

# Ejecutar
java -cp build:lib/gson.jar segundaparcialmarchettaagustin.SegundaParcialMarchettaAgustin
```

### Interfaz de Usuario

**Ventana Principal:**
- Tabla con listado de todos los robots
- Botones: Crear, Editar, Eliminar, Ver bajo nivel energético

**Formulario (Crear/Editar):**
- Campos: Nombre, Nivel de Energía, Número de Serie, Tipo (Radio buttons)
- Dato específico dinámico según tipo (Cantidad de Tareas o Capacidad de Carga)
- Validación en tiempo real

**Vista de Bajo Nivel Energético:**
- Muestra robots con nivel de energía < 20%
- Datos importados desde `robotEnergia.csv`

## 💾 Almacenamiento de Datos

### Archivo JSON (`ArchivoRobots`)
Estructura de ejemplo:
```json
[
  {
    "tipo": "DOMESTICO",
    "nombre": "Robot X1",
    "nivelEnergia": 5.0,
    "numeroSerie": 1111,
    "cantidadTareas": 2
  },
  {
    "tipo": "INDUSTRIAL",
    "nombre": "Robot M2",
    "nivelEnergia": 100.0,
    "numeroSerie": 1212,
    "capacidadCarga": 400
  }
]
```

### Archivo CSV (`robotEnergia.csv`)
Solo contiene robots con energía < 20%:
```
DOMESTICO;;Robot X1;5.0;1111;2
INDUSTRIAL;;Robot Q6;10.0;1313;500
```

## 🎓 Conceptos POO Implementados

- **Herencia**: `Domestico` e `Industrial` extienden `Robot`
- **Polimorfismo**: Métodos `toCSV()` y `fromCSV()` especializados en subclases
- **Encapsulación**: Atributos privados/protected con getters/setters
- **Interfaces**: `CSVEscritor`, `CSVParseador`, `jsonSerializacion`, `ReconstruirObjeto`
- **Genéricos**: `JsonUtils<T>`, `CSVUtils` con bounded wildcards
- **Excepciones**: Manejo personalizado con clases custom

## 🛠️ Tecnologías Utilizadas

| Tecnología | Descripción |
|-----------|------------|
| **JavaFX** | Framework para interfaz gráfica moderna |
| **Scene Builder** | Herramienta visual para diseño de FXML |
| **GSON** | Librería de serialización JSON |
| **NetBeans** | IDE de desarrollo |
| **POO (Java)** | Programación Orientada a Objetos |

## 📝 Notas Importantes

- Los robots se identifican únicamente por **número de serie** (clave primaria)
- La energía se expresa en **porcentaje (0-100)**
- Los robots con energía **menor al 20%** se consideran de bajo nivel energético
- La interfaz fue diseñada completamente en **Scene Builder** para facilitar la edición visual
- El proyecto utiliza un modelo **MVC** (Model-View-Controller) con separación clara de capas

## 👨‍💻 Autor

**Agustín Marchetta**
- Proyecto realizado para: Programación 2 - Segundo Parcial
- Universidad: [Institución Educativa]
- Año: 2026

---

**¡Gracias por usar Robot Management System! 🤖✨**
