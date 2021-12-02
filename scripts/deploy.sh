sudo docker ps -a -q --filter "name=userservice" | grep -q . && docker stop userservice && docker rm userservice | true
sudo docker rmi 0826hihi/up3-user-test:1.0
sudo docker pull 0826hihi/up3-user-test:1.0
sudo docker run -d -p 1000:1000 --name authserver 0826hihi/up3-user-test:1.0


