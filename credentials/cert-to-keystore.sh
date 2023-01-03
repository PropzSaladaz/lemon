# to delete certificate
sudo keytool -delete -alias lemon -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit
# to add it
sudo keytool -trustcacerts -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -importcert -alias lemon -file ~/sirs/lemon/server-backend/src/main/credentials/https-certificate.pem
