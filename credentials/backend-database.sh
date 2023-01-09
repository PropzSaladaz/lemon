# Database - Backend-Server communication

# Database side
echo "Creating Server's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout server-key.pem -out server-req.pem -config database.cnf

echo "Signing server certificate request with ca's private key"
openssl x509 -req -in server-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -extensions req_ext -extfile database.cnf

echo "Server's signed certificate:"
openssl x509 -in server-cert.pem -noout -text

# Server-backend side
echo "Creating Client's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout client-key.pem -out client-req.pem -config backend.cnf

echo "Signing client's certificate request with ca's private key"
openssl x509 -req -in client-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out client-cert.pem -extensions req_ext -extfile backend.cnf

echo "Client's signed certificate:"
openssl x509 -in client-cert.pem -noout -text

cp server-cert.pem ../database-backend/src/main/credentials
cp server-key.pem ../database-backend/src/main/credentials
cp ca-cert.pem ../database-backend/src/main/credentials

cp client-cert.pem ../server-backend/src/main/credentials
cp client-key.pem ../server-backend/src/main/credentials
cp ca-cert.pem ../server-backend/src/main/credentials