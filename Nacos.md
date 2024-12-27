# Nacos

```sh
docker pull nacos/nacos-server
```

```sql
导入nacos安装包下conf下的mysql-schema.sql文件，版本一定要匹配
```

```sh
PREFER_HOST_MODE=hostname
MODE=standalone
SPRING_DATASOURCE_PLATFORM=mysql
MYSQL_SERVICE_HOST=58.199.160.214
MYSQL_SERVICE_DB_NAME=nacos
MYSQL_SERVICE_PORT=3306
MYSQL_SERVICE_USER=root
MYSQL_SERVICE_PASSWORD=Gyh23820203
MYSQL_SERVICE_DB_PARAM=characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
```

```sh
docker run -d --name nacos --env-file ./nacos_env.env -p 8848:8848 -p 9848:9848 -p 9849:9849 --restart=always nacos/nacos-server
```

```sh
docker run -d --name chz-nacos \
  -e PREFER_HOST_MODE=hostname \
  -e MODE=standalone \
  -e SPRING_DATASOURCE_PLATFORM=mysql \
  -e MYSQL_SERVICE_HOST=58.199.160.214 \
  -e MYSQL_SERVICE_DB_NAME=nacos_config \
  -e MYSQL_SERVICE_PORT=3306 \
  -e MYSQL_SERVICE_USER=root \
  -e MYSQL_SERVICE_PASSWORD=Gyh23820203 \
  -e MYSQL_SERVICE_DB_PARAM="characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false" \
  -p 8848:8848 \
  -p 9848:9848 \
  -p 9849:9849 \
  --restart=always \
  nacos/nacos-server
```



