# Transportation & Distribution: Lemon

Business Context

Lemon is an international shared electric vehicle company that operates in Portugal since 2018, both with electric scooters and electric bicycles.

Customers can look for and unlock these vehicles through a mobile app or a website, where they also perform the payment for the used services.

Lemon's employees also have an internal app to pick up wrongly parked vehicles, which has access to their location through GPS and suggests the best course with Google Maps integration.


# Build commands

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


---
### Software versions
|       | version  |
|-------|----------|
| node  | 16.5.0   |
| maven | => 3.6.3 |
|  JDK  | 17       |


