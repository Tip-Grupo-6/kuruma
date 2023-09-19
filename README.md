Nombre: Kuruma
Icon: https://ibb.co/r0wz0Qn
Descripcion: Kuruma intenta convertirse en una web-app para centralizar la administración de un vehículo


<h2>Base de datos</h2>

Para inicializar la base se debe correr el siguiente comando en la terminal
```
docker-compose -p "local_postgres_db" -f ./src/main/resources/docker-compose-postgres.yml up -d --build
```

las conexiones a la base son:
```
host: localhost
port: 5432
user: postgres
password: postgres
```

Para bajarlo tenemos dos opciones.
Baja del servicio sin borrar información
```
docker-compose -p "local_postgres_db" -f ./src/main/resources/docker-compose-postgres.yml down
```

Baja del servicio borrar la información de los volumes
```
docker-compose -p "local_postgres_db" -f ./src/main/resources/docker-compose-postgres.yml down --volumes
```
