# to delete ca certificate from keystore
#sudo keytool -delete -alias lemon-ca-certificate -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit

# Certificate
echo "Creating CA private key and certificate"
openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout ca-key.pem -out ca-cert.pem -config ca.cnf

echo "CA's signed certificate:"
openssl x509 -in ca-cert.pem -noout -text

# to add ca certificate from keystore
#sudo keytool -trustcacerts -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -importcert -alias lemon-ca-certificate -file ./ca-cert.pem
