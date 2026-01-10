# Run app
```
dos2unix docker_task-api.sh
chmod +x docker_task-api.sh
./docker_task-api.sh
```


# Install dotnet server
sudo add-apt-repository ppa:dotnet/backports

sudo apt-get update && \
  sudo apt-get install -y dotnet-sdk-10.0

# Run server command
dotnet dev-certs https --clean
dotnet dev-certs https -ep cert.pfx -p StrongPassword123
dotnet dev-certs https --trust

copy to folder /volumes/pizushi-asp/certs

#opitons nginx /etc/nginx/sites-available/default

server {
    server_name   task.itstep.click *.task.itstep.click;
    client_max_body_size 200M;
    location / {
       proxy_pass         https://localhost:4179;
       proxy_http_version 1.1;
       proxy_set_header   Upgrade $http_upgrade;
       proxy_set_header   Connection keep-alive;
       proxy_set_header   Host $host;
       proxy_cache_bypass $http_upgrade;
       proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_set_header   X-Forwarded-Proto $scheme;
    }


    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/task.itstep.click/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/task.itstep.click/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}