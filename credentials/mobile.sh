# Generate private & public keys for mobile device

echo "creating mobile private key"
openssl genrsa -out mobile-private.key

echo "creating mobile public key"
openssl rsa -in mobile-private.key -pubout > mobile-public.key

echo "convert the private key to .pem format"
openssl rsa -in mobile-private.key -text > mobile-private.pem

echo "convert private Key to PKCS#8 format"
openssl pkcs8 -topk8 -inform PEM -outform DER -in mobile-private.pem -out mobile-private.der -nocrypt

echo "Output public key portion in .der format"
openssl rsa -in mobile-private.pem -pubout -outform DER -out mobile-public.der

cp mobile-private.der ../mobile-app/src/main/credentials
cp mobile-public.der ../mobile-app/src/main/credentials
