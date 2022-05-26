#!/usr/bin/env bash
cd TheVault/TheVaultAngular
npm install
npm audit fix
ng build
cd
http-server TheVault/TheVaultAngular/dist/the-vault -p 9001 \
   http-server > /dev/null 2> /dev/null < /dev/null &

cd
cd TheVault/
mvn clean install -DskipTests
cd
cd /home/ec2-user/TheVault/target
java -jar demo-0.0.1-SNAPSHOT.jar \
    *.jar > /dev/null 2> /dev/null < /dev/null &