#run below commands
echo "(((((     updating yum 1/7    )))))"
sudo yum update -y
echo "(((((     installing ruby 2/7 )))))"
sudo yum install ruby -y
echo "(((((     updating yum 3/7    )))))"
sudo yum install wget -y
cd /home/ec2-user
echo "(((((     Getting  CodeDeploy 4/7   )))))"
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto -y
echo "(((((     Installing Python Pip 5/7   )))))"
dnf install -y python3-pip
echo "(((((     Installing AWSCLI 6/7   )))))"
pip3 install awscli --upgrade --user
echo "(((((     Installing Java 11 7/7   )))))"
sudo yum install java-11-openjdk-devel

#verify installation
sudo service codedeploy-agent status
java -version