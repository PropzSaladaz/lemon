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
|-------|----------|

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
$ wget --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
$ unzip apache-maven-3.8.6-bin.zip
$ rm apache-maven-3.8.6-bin.zip
$ sudo mv apache-maven-3.8.6/ ~/../../opt/apache-maven-3.8.6
$ export PATH="$PATH:/opt/apache-maven-3.8.6/bin"

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

### Launch Backend server
``` bash
mvn spring-boot:run
```

### Launch frontend
``` bash
npm install
npm run serve
```

### Launch Mobile App
``` bash
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
