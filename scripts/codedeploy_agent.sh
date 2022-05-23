#run below commands
sudo yum update -y
sudo yum install ruby -y
sudo yum install wget -y
cd /home/ec2-user
wget https://aws-codedeploy-us-east-1.s3.us-east-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto -y
sudo yum install -y python-pip
sudo pip install awscli -y
sudo amazon-linux-extras install java-openjdk11 -y

#verify installation
sudo service codedeploy-agent status
java -version