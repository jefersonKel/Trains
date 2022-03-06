## Introduccion
API Rest desarrollada en Java usando Framework Spring boot. 

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
3. Comprobar la aplicación en el navegador con la url: http://localhost:8080/train/Test debe retornar la palabra Test

4.Los datos de muestra se encuentran en la siguiente ruta
  ```
  \src\main\resources\json
  ```
  * InformacionCliente.json
  * Rutas.json
  * TestInput.json


## Documentación de la API
Se uso la Swagger-OpenAPI para documentar la API. para ingresar debera ir a: http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config/

