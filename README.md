## REST Service job4j_chat

Сервис представляет собой чат, который имеет в себе:
- Пользователей
- Роли пользователей
- Комнаты

Стэк технологий:
 - PostgreSQL
 - Spring Boot
 - Spring REST
 - Spring Security, JWT

Для запуска требуется:
 - Java 17
 - Maven
 - PostgreSQL

Порядок запуска:
1. Создать базу данных
````
CREATE DATABASE chat
````
2. Запустить проект
````
mvn spring-boot:run
````