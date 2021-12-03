sudo docker ps -a -q --filter "name=user" | grep -q . && docker stop user && docker rm user | true
sudo docker rmi 0826hihi/up3-user-test:latest
sudo docker pull 0826hihi/up3-user-test:latest
docker run -d -p 1000:1000 --name user 0826hihi/up3-user-test:latest
docker rmi -f $(docker images -f "dangling=true" -q) || true

