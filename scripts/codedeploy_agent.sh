#run below commands
sudo yum update -y
echo "(((((     updating yum    )))))"
sudo yum install ruby -y
echo "(((((     installing ruby )))))"
sudo yum install wget -y
echo "(((((     updating yum    )))))"
cd /home/ec2-user
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto -y
#sudo yum install -y python-pip
sudo pip install awscli -y
sudo yum install java-11-openjdk-devel

#verify installation
sudo service codedeploy-agent status
java -version