#!/usr/bin/env bash
cd /home/ec2-user/server
sudo java -jar -Dserver.port=9000 \
    *.jar > /dev/null 2> /dev/null < /dev/null &
cd
cd TheVault/TheVaultAngular
npm install
npm audit fix
cd
nohup http-server -a 0.0.0.0 TheVault/TheVaultAngular/dist/the-vault -p 9001 \
   http-server > /dev/null 2> /dev/null < /dev/null &
