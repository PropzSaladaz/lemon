# Client - Frontend-Server communication

# Frontend-server side
echo "Creating Server's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout frontend-server-key.pem -out frontend-server-req.pem -subj "/C=PT/ST=Lisbon/L=Oeiras/O=Lemon/OU=Lemon/CN=localhost/emailAddress=lemon@gmail.pt"

echo "Signing server certificate request with ca's private key"
openssl x509 -req -in frontend-server-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out frontend-server-cert.pem

echo "Server's signed certificate:"
openssl x509 -in frontend-server-cert.pem -noout -text

# Client side
echo "Creating Client's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout frontend-client-key.pem -out frontend-client-req.pem -subj "/C=PT/ST=Lisbon/L=Oeiras/O=Lemon/OU=Lemon/CN=localhost/emailAddress=lemon@gmail.pt"

echo "Signing client's certificate request with ca's private key"
openssl x509 -req -in frontend-client-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out frontend-client-cert.pem

echo "Client's signed certificate:"
openssl x509 -in frontend-client-cert.pem -noout -text

cp frontend-server-cert.pem ../database-backend/src/main/credentials
cp frontend-server-key.pem ../database-backend/src/main/credentials
cp ca-cert.pem ../database-backend/src/main/credentials

cp frontend-client-cert.pem ../server-backend/src/main/credentials
cp frontend-client-key.pem ../server-backend/src/main/credentials
cp ca-cert.pem ../server-backend/src/main/credentials