FROM openjdk:14
ADD target/plastic_store.jar plastic_store.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","plastic_store.jar"]

FROM mysql
ENV MYSQL_ROOT_PASSWORD=poruka
COPY plastic_store_db.sql /docker-entrypoint-initdb.d/