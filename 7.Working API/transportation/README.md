# web_site
```
mvn spring-boot:run

mvn clean package

java -jar target/p22.jar

```

## Run Docker java
```
docker build -t p22-java .

docker run -it --rm -p 6892:8345 --name p22-java_container p22-java

docker run -d --restart=always -p 6892:8345 --name p22-java_container p22-java

docker run -d --restart=always -v d:/volumes/p22-java/uploads:/app/uploads -p 6892:8345 --name p22-java_container p22-java

docker run -d --restart=always -v /volumes/p22-java/uploads:/app/uploads -p 6892:8345 --name p22-java_container p22-java

docker ps -a

docker stop p22-java_container
docker rm p22-java_container
docker rmi p22-java

docker login
docker tag p22-java:latest novakvova/p22-java:latest
docker push novakvova/p22-java:latest

```

# Run app
```
dos2unix java-p22.sh
chmod +x java-p22.sh
./java-p22.sh
```

# nginx config --/etc/nginx/sites-available/default--
```
server {
    server_name   javap22.itstep.click *.javap22.itstep.click;
    client_max_body_size 200M;
    location / {
       proxy_pass         http://localhost:6892;
       proxy_http_version 1.1;
       proxy_set_header   Upgrade $http_upgrade;
       proxy_set_header   Connection keep-alive;
       proxy_set_header   Host $host;
       proxy_cache_bypass $http_upgrade;
       proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_set_header   X-Forwarded-Proto $scheme;
    }
}
```

# nginx command
```
systemctl status nginx
systemctl restart nginx
certbot
```

