#!/usr/bin/env bash
cd TheVault/TheVaultAngular
npm install
npm audit fix
ng build
cd
nohup http-server -a 0.0.0.0 TheVault/TheVaultAngular/dist/the-vault -p 9001 \
   http-server > /dev/null 2> /dev/null < /dev/null &

cd
cd /home/ec2-user/TheVault/target
sudo java -jar demo-0.0.1-SNAPSHOT.jar -Dserver.port=9000 \
    *.jar > /dev/null 2> /dev/null < /dev/null &