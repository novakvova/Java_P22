#!/bin/bash

set -e

server_up() {
    echo "Server up..."
    docker pull novakvova/p22-java:latest
    docker stop p22-java_container || true
    docker rm p22-java_container || true
    docker run -d --restart=always -v /volumes/p22-java/images:/app/images --name p22-java_container -p 6892:8081 novakvova/p22-java
}

start_containers() {
    echo "Containers start..."
    docker run -d --restart=always -v /volumes/p22-java/images:/app/images --name p22-java_container -p 6892:8081 novakvova/p22-java
}

stop_containers() {
    echo "Containers stop..."
    docker stop p22-java_container || true
    docker rm p22-java_container || true
}

restart_containers() {
    echo "Containers restart..."
    docker stop p22-java_container || true
    docker rm p22-java_container || true
    docker run -d --restart=always -v /volumes/p22-java/images:/app/images --name p22-java_container -p 6892:8081 novakvova/p22-java
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
