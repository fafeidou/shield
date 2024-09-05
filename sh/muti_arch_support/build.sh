docker manifest create batman01/server_app batman01/server_app:v1

docker manifest annotate batman01/server_app batman01/server_app:v1 --os linux --arch amd64 --variant v8

docker manifest inspect batman01/server_app
