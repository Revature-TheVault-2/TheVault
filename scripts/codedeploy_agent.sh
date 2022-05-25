echo "          ((((((((((     updating existing packages START     ))))))))))          "
sudo yum update -y
echo "          ((((((((((     updating existing packages END     ))))))))))          "

echo "          ((((((((((     install a couple tools that may be helpful for later START     ))))))))))          "
sudo yum install wget -y
sudo yum install unzip -y
sudo yum install lsof -y
echo "          ((((((((((     install a couple tools that may be helpful for later END     ))))))))))          "

echo "          ((((((((((     installing java 11 START     ))))))))))          "
sudo yum install -y java-11-openjdk-devel
sudo update-alternatives --config java
sudo update-alternatives --config javac
echo "          ((((((((((     installing java 11 END     ))))))))))          "

echo "          ((((((((((     installing node START     ))))))))))          "
curl --silent --location https://rpm.nodesource.com/setup_14.x | sudo bash -
sudo yum -y install nodejs
echo "          ((((((((((     installing node END     ))))))))))          "

echo "          ((((((((((     installing angular START     ))))))))))          "
sudo npm install -g @angular/cli
sudo npm install -g http-server
echo "          ((((((((((     installing angular END     ))))))))))          "

echo "          ((((((((((     installing maven START     ))))))))))          "
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y maven
echo "          ((((((((((     installing maven END     ))))))))))          "


echo "(((((     installing ruby 2/7 )))))"
sudo yum install ruby -y


cd /home/ec2-user
echo "(((((     Getting  CodeDeploy 4/7   )))))"
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
echo "(((((     Installing Python Pip 5/7   )))))"
dnf install -y python3-pip
echo "(((((     Installing AWSCLI 6/7   )))))"
pip3 install awscli --upgrade --user

#verify installation
sudo service codedeploy-agent status
java -version