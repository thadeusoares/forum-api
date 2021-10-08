# forum-api
Estudos de Springboot utilizando Apis nas versões do Spring 5.3.x

Códigos com algumas atualizações do módulo do curso API REST e Testes com Spring Boot


# How-to run with spring webserver

```Maven POM
mvn clean package
```

Then 


```
java -jar -Dspring.profiles.active=prod -DFORUM_DATABASE_URL=jdbc:h2:mem:alura-forum -DFORUM_DATABASE_USERNAME=sa -DFORUM_DATABASE_PASSWORD= -DFORUM_JWT_SECRET=123456 forum.jar
```

<br><br>

# How-to run with Docker

Building the image

```
docker build -t alura/forum .
```

Listing if the image is there

```
docker image list
```


Running

```
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE='prod' -e FORUM_DATABASE_URL='jdbc:h2:mem:alura-forum' -e FORUM_DATABASE_USERNAME='sa' -e FORUM_DATABASE_PASSWORD='' -e FORUM_JWT_SECRET='123456' alura/forum
```


# How-to run with Heroku

Building the image

```
heroku login
heroku container:login
heroku create thadeu-forum
heroku git:remote -a alura-forum
heroku container:push web
heroku container:release web
heroku open
```

Logs
heroku logs --tail