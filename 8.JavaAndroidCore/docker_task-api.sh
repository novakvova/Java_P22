#!/bin/bash

set -e

server_up() {
    echo "Server up..."
    docker pull novakvova/task-api:latest
    docker stop task-api_container || true
    docker rm task-api_container || true
    docker run -d --restart=always -v /volumes/pizushi-asp/images:/app/images -v /volumes/pizushi-asp/certs:/https -e ASPNETCORE_Kestrel__Certificates__Default__Password=StrongPassword123 --name task-api_container -p 4179:8080 novakvova/task-api
}

start_containers() {
    echo "Containers start..."
    docker run -d --restart=always -v /volumes/pizushi-asp/images:/app/images -v /volumes/pizushi-asp/certs:/https -e ASPNETCORE_Kestrel__Certificates__Default__Password=StrongPassword123 --name task-api_container -p 4179:8080 novakvova/task-api
}

stop_containers() {
    echo "Containers stop..."
    docker stop task-api_container || true
    docker rm task-api_container || true
}

restart_containers() {
    echo "Containers restart..."
    docker stop task-api_container || true
    docker rm task-api_container || true
    docker run -d --restart=always -v /volumes/pizushi-asp/images:/app/images -v /volumes/pizushi-asp/certs:/https -e ASPNETCORE_Kestrel__Certificates__Default__Password=StrongPassword123 --name task-api_container -p 4179:8080 novakvova/task-api
}

echo "Choose action:"
echo "1. Server up"
echo "2. Containers start"
echo "3. Containers stop"
echo "4. Containers restart"
read -p "Enter action number: " action

case $action in
    1)
        server_up
        ;;
    2)
        start_containers
        ;;
    3)
        stop_containers
        ;;
    4)
        restart_containers
        ;;
    *)
        echo "Invalid action number!"
        exit 1
        ;;
esac
