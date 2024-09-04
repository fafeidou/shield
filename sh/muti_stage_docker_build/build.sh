#!/bin/sh
echo builder_app:v1

docker build -t builder_app:v1 . -f Dockerfile.one

docker create --name builder builder_app:v1

docker cp builder:/go/src/myapp ./

echo Building server_app:v1

docker build  --no-cache -t server_app:v1 . -f Dockerfile.copy


#docker build --no-cache  -t server_app:v2 . -f Dockerfile.build

