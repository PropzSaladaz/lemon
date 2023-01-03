# Generate private & public keys for server-backend

echo "creating backend private key"
openssl genrsa -out backend-private.key

echo "creating backend public key"
openssl rsa -in backend-private.key -pubout > backend-public.key

echo "convert the private key to .pem format"
openssl rsa -in backend-private.key -text > backend-private.pem

echo "convert private Key to PKCS#8 format"
openssl pkcs8 -topk8 -inform PEM -outform DER -in backend-private.pem -out backend-private.der -nocrypt

echo "Output public key portion in .der format"
openssl rsa -in backend-private.pem -pubout -outform DER -out backend-public.der

cp backend-private.der ../server-backend/src/main/credentials
cp backend-public.der ../mobile-app/src/main/credentials