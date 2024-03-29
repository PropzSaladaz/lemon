# Server-backend - Mobile App communication

# Mobile side
echo "Creating Server's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout mobile-server-key.pem -out mobile-server-req.pem -config mobile.cnf

echo "Signing server certificate request with ca's private key"
openssl x509 -req -in mobile-server-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out mobile-server-cert.pem -extensions req_ext -extfile mobile.cnf

echo "Server's signed certificate:"
openssl x509 -in mobile-server-cert.pem -noout -text

# Server-backend side
echo "Creating Client's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout mobile-client-key.pem -out mobile-client-req.pem -config backend.cnf

echo "Signing client's certificate request with ca's private key"
openssl x509 -req -in mobile-client-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out mobile-client-cert.pem -extensions req_ext -extfile backend.cnf

echo "Client's signed certificate:"
openssl x509 -in mobile-client-cert.pem -noout -text

cp mobile-server-cert.pem ../mobile-app/src/main/credentials
cp mobile-server-key.pem ../mobile-app/src/main/credentials
cp ca-cert.pem ../mobile-app/src/main/credentials

cp mobile-client-cert.pem ../server-backend/src/main/credentials
cp mobile-client-key.pem ../server-backend/src/main/credentials
cp ca-cert.pem ../server-backend/src/main/credentials