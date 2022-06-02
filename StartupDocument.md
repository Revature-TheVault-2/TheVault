# The Vault Start-up Guide

If you would like to:
Have a running Cloud Platform Pipeline, then start at the 1st step.
Have the application running on an EC2 instance, then start at the Nth step
Have the application running on a localhost, then start at the Nth step

> ## Step 1 - Creating the Pipeline
>Using this online resource as a guide:
>https://enlear.academy/deploy-your-spring-boot-application-using-codedeploy-and-codepipeline-4d853b1e486e
>We were able to create a pipeline quickly.




> ## Step 2 - Creating the instance
> Go to AWS’s EC2 page and create a instance
> Make sure that the EC2 has a tag with a Key = Name and a Value = {instance name}
> Set the security group to allow inbound for the chosen port and start it up
> While selecting the instance in AWS, you can select the connect button and copy the SSH access string as well as downloading the pem file.

> ## Step 3 - Clone the GitHub Repo
> With the command prompt open, run:
``` shell session
sudo yum install git
```
``` shell session
git clone <repo url>
```
Then checkout the desired branch
``` shell session
git checkout <branch name>
```

> ## Step 4 - Set up the Instance Environment
Inside of the EC2 instance you will need to run the script below at least once before the booting the application.
``` shell session
chmod +x TheVault/scripts/codedeploy_agent.sh
sh TheVault/scripts/codedeploy_agent.sh
```

> ## Step 5 - Set Up the Environment Variables
If you are using just the EC2 instance, you can manually add the environment variables to the instance with the following script:
``` shell session
cd 
cd TheVault/ 
export db_password='{Your Database Password Here}'
export db_username='{Your Database Username Here}'
export db_url='{Your Database Endpoint Here}'
```
Otherwise, inside of AWS CodePipeline 

> ## Step 6 - Starting Up the Server
To start up the server on just the EC2 you would need to run this script:
```shell session
cd 
cd TheVault/
mvn clean install -DskipTests
cd target/
java -jar <name of snapshot> &
```

> ## Step 7 - Starting Up the SPA
```shell session
cd 
cd TheVault/TheVaultAngular
sudo npm install -g
sudo npm install
ng update
ng build
```

> ## Step Extra - Some Notes to learn from our errors
# Never delete the dist folder after building
# ng build takes a long time, so make sure you have enough memory space to make sure that it does not take 33 hours. Once it is built, rebuilding it takes no time at all. 15 seconds or so.
