# Facultad Politécnica
# Sistemas Distribuidos
## Alumno: Esteban Fernández

# Cliente y Servidor TCP conectado a Redis

## Requerimientos
- Realizar la adaptación de servidor (TCPMultiServer, TCPServerHilo) y cliente para que contemple las siguientes funciones:
    - Login usuario
    - Logout usuario
    - Ver lista de usuarios conectados
- El almacenamiento de los usuarios conectados debe realizar el servidor mediante una base de datos en memoria caché: Redis
- Deben estar en proyectos independientes el cliente y servidor.

## Pasos para desplegar
Levantar el contenedor de redis. En caso de desplegarse de otra forma, asegurarse de levantar con las mismas propiedades que el compose (puerto password, etc.)
```shell
docker compose up -d
```
Compilar el cliente y servidor, dentro de la raiz del proyecto ejecutar
```shell
mvn clean install
```
Levantar el servidor
```shell
java -jar server/target/server-1.0.0-jar-with-dependencies.jar
```
Levantar en client, se puede realizar en varias shells para tener multiples clients
```shell
java -jar client/target/client-1.0.0-jar-with-dependencies.jar
```
Resultado esperado para el server
```shell
sept 26, 2024 1:31:41 A. M. py.una.dao.UserDao <init>
INFORMACIÓN: Conexión a Redis satisfactoria
sept 26, 2024 1:31:41 A. M. py.una.server.TCPMultiServer ejecutar
INFORMACIÓN: Puerto abierto: 4444.
```
Resultado esperado para el client
```shell
Servidor: Bienvenido!
```

## Uso
| Entrada           | Parámetros | Descripción                             | Salida esperada                                  |
|-------------------|------------|-----------------------------------------|--------------------------------------------------|
| /login {username} | username   | Iniciar sesión con un nombre de usuario | Servidor: Usuario \[esteban\] logueado           |
| /logout           | -          | Cerrar sesión con el usuario activo     | Servidor: Usuario \[esteban\] deslogueado        |
| /users            | -          | Listar los usuarios con sesión activa   | Servidor: Lista de usuarios: \[esteban, mancia\] |
| /exit             | -          | Termina la conexión con el servidor     | -                                                |
