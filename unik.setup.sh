
sudo apt-get install -y apt-transport-https ca-certificates
sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
sudo apt-get install -y linux-image-extra-$(uname -r) linux-image-extra-virtual
sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
sudo apt-get update
apt-cache policy docker-engine
sudo apt-get install -y docker-engine
sudo service docker start
sudo docker run hello-world

sudo apr-get install -y jq
sudo apr-get install -y virtualbox *
sudo apr-get install -y maven
sudo apt-get install -y locate
git clone https://github.com/emc-advanced-dev/unik.git
cd unik
make
sudo mv _build/unik /usr/local/bin/
