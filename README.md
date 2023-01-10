# Transportation & Distribution: Lemon

Business Context

Lemon is an international shared electric vehicle company that operates in Portugal since 2018, both with electric scooters and electric bicycles.

Customers can look for and unlock these vehicles through a mobile app or a website, where they also perform the payment for the used services.

Lemon's employees also have an internal app to pick up wrongly parked vehicles, which has access to their location through GPS and suggests the best course with Google Maps integration.


## General Information

This section expands on the introductory paragraph to give readers a better understanding of your project.
Include a brief description and answer the question, "what problem does this project solve?"

### Built With

* [Java](https://openjdk.java.net/) - Programming Language and Platform
* [Maven](https://maven.apache.org/) - Build Tool and Dependency Management
* [Spring-boot](https://spring.io/projects/spring-boot) - Framework to build modern java-based enterprise applications
* [Postgresql](https://www.postgresql.org/) - Relational database
* [Vuejs](https://vuejs.org/) - Javascript framework

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

|       | version  |
|-------|----------|
| node  | 16.5.0   |
| maven | => 3.6.3 |
|  JDK  | 17       |
| psql  | 14       |

The software was tested only on devices running Linux.

## Installing and configurating VMs

In this section we include detailed instructions for configuring fresh VMs and installing the required software the application is dependent upon.

### Lemon Database Server VM configuration:
```
Download Seed VM: https://seedsecuritylabs.org/labsetup.html
Setup Seed VM: https://github.com/seed-labs/seed-labs/blob/master/manuals/vm/seedvm-manual.md
```


```bash
# Boot VM and install postgresql-14:
$ sudo apt update; sudo apt upgrade
$ sudo apt install curl ca-certificates gnupg
$ curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/apt.postgresql.org.gpg >/dev/null
$ sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
$ sudo apt update
$ sudo apt install postgresql-14
$ sudo systemctl enable postgresql
$ sudo systemctl start postgresql
$ sudo -u postgres psql
  # postgres=# CREATE DATABASE sirsdb;
  # postgres=# \c sirsdb
  # postgres=# CREATE ROLE sirsdb_manager WITH LOGIN PASSWORD '1234';

-> Allowing remote access:
$ sudo vim /etc/postgresql/14/main/postgresql.conf
    # Look for listen_addresses = 'localhost'
    #      and change to: listen_addresses = '*'

$ sudo vim /etc/postgresql/14/main/pg_hba.conf
    # Look for # IPv4 local connections and swap:
    #     host    all             all             127.0.0.1/32         md5
    # to this 
    #     host    all             all             0.0.0.0/0            md5
    #
    # Look for # TYPE  DATABASE        USER            ADDRESS                 METHOD
    # and insert host  sirsdb          sirsdb_manager  192.168.1.254/0         md5

$ sudo ufw allow 5432/tcp
$ sudo systemctl restart postgresql

# Download java:
$ sudo add-apt-repository -y ppa:openjdk-r/ppa
$ sudo apt install -y openjdk-17-jdk
$ export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
$ export PATH="$PATH:$JAVA_HOME/bin"

# Download maven:
$ wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.zip
$ unzip apache-maven-3.8.7-bin.zip
$ rm apache-maven-3.8.7-bin.zip
$ sudo mv apache-maven-3.8.7/ ~/../../opt/apache-maven-3.8.7
$ export PATH="$PATH:/opt/apache-maven-3.8.7/bin"
```


### Lemon Application Server VM configuration:

```bash
# Update package manager:
$ sudo apt update; sudo apt upgrade

# Download java:
$ sudo add-apt-repository -y ppa:openjdk-r/ppa
$ sudo apt install -y openjdk-17-jdk
$ export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
$ export PATH="$PATH:$JAVA_HOME/bin"

# Download maven:
$ wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.zip
$ unzip apache-maven-3.8.7-bin.zip
$ rm apache-maven-3.8.7-bin.zip
$ sudo mv apache-maven-3.8.7/ ~/../../opt/apache-maven-3.8.7
$ export PATH="$PATH:/opt/apache-maven-3.8.7/bin"
```

### Lemon Mobile App VM configuration:

```bash
# Update package manager:
$ sudo apt update; sudo apt upgrade

# Download java:
$ sudo add-apt-repository -y ppa:openjdk-r/ppa
$ sudo apt install -y openjdk-17-jdk
$ export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
$ export PATH="$PATH:$JAVA_HOME/bin"

# Download maven:
$ wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.zip
$ unzip apache-maven-3.8.7-bin.zip
$ rm apache-maven-3.8.7-bin.zip
$ sudo mv apache-maven-3.8.7/ ~/../../opt/apache-maven-3.8.7
$ export PATH="$PATH:/opt/apache-maven-3.8.7/bin"
```


### Lemon Frontend Server VM configuration:

```bash
# Download nvm and nodejs 16.15.0:
$ sudo apt install curl 
$ curl https://raw.githubusercontent.com/creationix/nvm/master/install.sh | bash 
$ source ~/.bashrc
$ nvm install node
$ nvm install 16.15.0
$ nvm use 16.15.0
```


### Generate all certificates & keys
Got to the credentials' directory (lemon/credentials) and run the generator script
``` bash
cd ./credentials
bash gen.sh
```

### Launch Database server
``` bash
mvn exec:java -Dexec.mainClass="com.tecnico.lemon.LemonDatabaseServer" -Dexec.args=""
```

Upon launching, the database server will be waiting for commands. The commands accepted are:
| command | description |
|---------|-------------|
|    init | drops and creates the required tables |
| exit    | closes de database server |

If it is the first time running the database server, the 'init' command should be provided.
The 'init' command can be ran to restart the database at any moment.

### Launch Backend server
``` bash
# Add ca-certificate to truststore
sudo keytool -trustcacerts -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -importcert -alias lemon-ca-certificate -file src/main/credentials/ca-cert.pem

mvn spring-boot:run
```

### Launch frontend
``` bash
npm install
npm run serve
```

### Launch Mobile App
``` bash
# Add ca-certificate to truststore
sudo keytool -trustcacerts -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -importcert -alias lemon-ca-certificate -file src/main/credentials/ca-cert.pem

mvn exec:java -Dexec.mainClass="com.tecnico.lemon.MobileApp" -Dexec.args=""
```

## Testing

There are very few tests to run and only test encription/decription and database

To run them, run:
```
mvn test
```

## Demo

Give a tour of the best features of the application.
Add screenshots when relevant.

## Deployment

Add additional notes about how to deploy this on a live system e.g. a host or a cloud provider.

Mention virtualization/container tools and commands.

```
Give an example command
```

Provide instructions for connecting to servers and tell clients how to obtain necessary permissions.

## Additional Information

### Authors

Afonso Pinto  
Ricardo Rocha  
Sidnei Teixeira  

### Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc


# READ ONYL ------------------------------
VM configs:
	VM1 (database:8082) [Firewall]:
		Adapter1 - IN, sw-1, ip-192.168.0.1
	VM6 [Firewall]:
		Adapter1 - IN, sw-1, ip-192.168.0.254
		Adapter2 - IN, sw-2, ip-192-168.1.1
	VM2 (backend:8443):
		Adapter1 - IN, sw-2, ip-192.168.1.2
	VM3 (frontend:8081):
		Adapter1 - IN, sw-2, ip-192.168.1.3
	VM5 [Firewall]:
		Adapter1 - IN, sw-2, ip-192.168.1.254
		Adapter2 - IN, sw-3, ip-192-168.2.1
	VM4 (web browser client):
		Adapter1 - IN, sw-3, ip-192.168.2.2
  VM7 (mobile device:8080):
    Adapter1 - IN, sw-3, ip-192.168.2.3
		
NOTE: add NATNetwork to all VMs for internet access.
------------------------------


# APPLY ------------------------------------------
Network files (sudo gedit /etc/netplan/01-network-manager-all.yaml):

VM1 Database Server:
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.0.1/24
      routes:
        - to: 0.0.0.0/0
          via: 192.168.0.254
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]

			
VM2 Backend Server:
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.1.2/24
      routes:
        - to: 192.168.0.0/24
          via: 192.168.1.1
        - to: 0.0.0.0/0
          via: 192.168.1.254
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]

			
VM3 Frontend Server:
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.1.3/24
      routes:
        - to: 0.0.0.0/0
          via: 192.168.1.254
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
			

VM4 Web Browser Client:
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.2.2/24
      routes:
        - to: 192.168.1.0/24
          via: 192.168.2.1
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
    enp0s8:
      dhcp4: yes
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]


VM7 Mobile App:
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.2.3/24
      routes:
        - to: 0.0.0.0/0
          via: 192.168.2.1
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
			
VM5 (copy all after this line):
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.1.254/24
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
    enp0s8:
      addresses:
        - 192.168.2.1/24
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
    enp0s9:
        dhcp4: yes
        nameservers:
          addresses: [8.8.8.8, 8.8.4.4]

VM6 (copy all after this line):
network:
  version: 2
  renderer: NetworkManager
  ethernets:
    enp0s3:
      addresses:
        - 192.168.0.254/24
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
    enp0s8:
      addresses:
        - 192.168.1.1/24
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]



Run in all VMs:
# run this
sudo netplan try
sudo netplan apply


On VM6:
edit /etc/sysctl.conf and uncomment the following line "net.ipv4.ip_forward=1"
$ sudo sysctl -p
$ sudo apt install iptables-persistent
# iptables_setup.sh bash script to setup iptables rules
sudo iptables -F
sudo iptables -P FORWARD DROP
sudo iptables -A FORWARD -s 192.168.0.1 -d 192.168.1.2 -p tcp -j ACCEPT
sudo iptables -A FORWARD -s 192.168.1.2 -d 192.168.0.1 -p tcp --dport 8082 -j ACCEPT



On VM5:
edit /etc/sysctl.conf and uncomment the following line "net.ipv4.ip_forward=1"
$ sudo sysctl -p
$ sudo apt install iptables-persistent
# iptables_setup.sh bash script to setup iptables rules
sudo iptables -F
sudo iptables -P FORWARD DROP
sudo iptables -A FORWARD -s 192.168.1.2 -p tcp -j ACCEPT
sudo iptables -A FORWARD -s 192.168.1.3 -p tcp -j ACCEPT
sudo iptables -A FORWARD -d 192.168.1.2 -p tcp -j ACCEPT
sudo iptables -A FORWARD -d 192.168.1.3 -p tcp -j ACCEPT
sudo iptables -t nat -F
sudo iptables -t nat -A POSTROUTING -o enp0s9 -j MASQUERADE
sudo iptables -t nat -A POSTROUTING -d 192.168.2.3 -o enp0s8 -j MASQUERADE



File changes to run (If running on virtual network):

		server-backend (server-backend/src/main/.../database):
			Change the IP:port from all files inside database directory to "192.168.0.1:8082" (maybe put this in a global config file)
      mobile-frontend: "https://192.168.2.3:8080"
		frontend:
			In ApplicationService.js -> "https://192.168.1.2:8443"
		mobile:
			config.properties -> server-hostname="192.168.1.2"

	add dependency (server-backend/pom.xml):
	<dependency>
		<groupId>org.bouncycastle</groupId>
		<artifactId>bcpkix-jdk15to18</artifactId>
		<version>1.71</version>
	</dependency>