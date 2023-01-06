# Certificate
echo "Creating CA private key and certificate"
openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout ca-key.pem -out ca-cert.pem -subj "/C=PT/ST=Lisbon/L=Oeiras/O=LemonCA/OU=LemonCA/CN=192.168.1.2/emailAddress=lemon-ca@gmail.pt" -addtext "subjectAltName = IP:192.168.1.2"

echo "CA's signed certificate:"
openssl x509 -in ca-cert.pem -noout -text