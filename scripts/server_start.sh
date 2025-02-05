#!/usr/bin/env bash
cd
cd TheVault/
git stash
git pull

cd
cd TheVault/TheVaultAngular
#npm install
#npm audit fix --force
ng build
cd
http-server TheVault/TheVaultAngular/dist/the-vault -p 9001 \
   http-server > /dev/null 2> /dev/null < /dev/null &
cd
cd TheVault/
mvn clean install -DskipTests
cd target/
echo =========================================================================
echo "In target folder"
echo =========================================================================
java -jar demo-0.0.1-SNAPSHOT.jar \
    *.jar > /dev/null 2> /dev/null < /dev/null &
    
echo =========================================================================
echo "JAR running"
echo =========================================================================
