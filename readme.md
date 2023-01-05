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
The software was tested only on devices running Linux.
In this section also include detailed instructions for installing additiona software the application is dependent upon (such as PostgreSQL database, for example).

```
Give installation command examples
```

## Installing

### Generate all certificates & keys
Got to the credentials' directory (lemon/credentials) and run the generator script
``` bash
cd ./credentials
bash gen.sh
```

### Add Lemon certificate to java keystore
Since the certificate is singed by an "CA", we need to add it to the java keystore:
``` bash
# to delete certificate
sudo keytool -delete -alias lemon -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit
# to add it
sudo keytool -trustcacerts -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -importcert -alias lemon -file <path-to-lemon>/lemon/server-backend/src/main/credentials/https-certificate.pem
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
