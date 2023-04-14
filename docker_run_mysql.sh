#!/bin/sh

# TODO: something wring with MYSQL_DATABASE?
docker run --name mysql-train-db --rm -e MYSQL_ROOT_PASSWORD=password123 -e MYSQL_DATABASE=train-db -d -p 3306:3306 mysql:8.0.32@sha256:f496c25da703053a6e0717f1d52092205775304ea57535cc9fcaa6f35867800b