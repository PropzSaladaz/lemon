# Server-backend SSL certificates

echo "Creating Server's private key and Certificate Signing Request"
openssl req -newkey rsa:4096 -nodes -keyout https-server-private-key.pem -out https-server-req.pem -subj "/C=PT/ST=Lisbon/L=Oeiras/O=Lemon/OU=Lemon/CN=localhost/emailAddress=lemon@gmail.pt"

echo "Signing server certificate request with ca's private key"
openssl x509 -req -in https-server-req.pem -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out https-server-cert.pem

echo "Server's signed certificate:"
openssl x509 -in https-server-cert.pem -noout -text

echo "Setting password and alias to the certificate, and convert it to .p12 format"
openssl pkcs12 -export -out https-certificate.p12 -inkey https-server-private-key.pem -in https-server-cert.pem -certfile ca-cert.pem -passout pass:password -name "https-certificate"

cp https-certificate.p12 ../server-backend/src/main/credentials
cp https-server-private-key.pem ../server-backend/src/main/credentials

