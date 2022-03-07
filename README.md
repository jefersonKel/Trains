## Introduccion
API Rest desarrollada en Java usando Framework Spring boot. 

Proporcionar a sus clientes información sobre las rutas de tren

## Requisitos
* [jdk-11.0.11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

## Instalación
1. Clonar el repositorio o descargar desde https://github.com/jefersonKel/Trains
  ```sh
  git clone git@github.com:jefersonKel/Trains
  ```
2. Por el terminal ir a la carpera raiz Trains\ y ejecutamos la aplicación con el comando:
  ```
  .\mvnw.cmd spring-boot:run
  ```
3.Los datos de muestra se encuentran en la siguiente ruta
  ```
  \src\main\resources\json
  ```
  * InformacionCliente.json
  * Rutas.json
  * TestInput.json


## Documentación de la API
Se uso la Swagger-OpenAPI para documentar la API. para ingresar debera ir a: http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config/

## Pruebas
1. Ingresar distancias y ver informacion sobre las rutas del tren: Debe ir a la url 
```
  http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config/#/train-controller/IngresarDistanias
```
2. Ejecutar el servico dando clien en la opcion Try it out, luego ingresando la trama ###TestInput.json y dar clic en la opcion excute

```json
    [
    {
        "ciudadInicial": "A",
        "ciudadFinal": "B",
        "distancia": 5
    },
    {
        "ciudadInicial": "B",
        "ciudadFinal": "C",
        "distancia": 4
    },
    {
        "ciudadInicial": "C",
        "ciudadFinal": "D",
        "distancia": 8
    },
    {
        "ciudadInicial": "D",
        "ciudadFinal": "C",
        "distancia": 8
    },
    {
        "ciudadInicial": "D",
        "ciudadFinal": "E",
        "distancia": 6
    },
    {
        "ciudadInicial": "A",
        "ciudadFinal": "D",
        "distancia": 5
    },
    {
        "ciudadInicial": "C",
        "ciudadFinal": "E",
        "distancia": 2
    },
    {
        "ciudadInicial": "E",
        "ciudadFinal": "B",
        "distancia": 3
    },
    {
        "ciudadInicial": "A",
        "ciudadFinal": "E",
        "distancia": 7
    }
]
```



