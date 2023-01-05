# Generate private & public keys for mobile device

echo "creating mobile private key"
openssl genrsa -out employer-mobile-private.key

echo "creating mobile public key"
openssl rsa -in employer-mobile-private.key -pubout > employer-mobile-public.key

echo "convert the private key to .pem format"
openssl rsa -in employer-mobile-private.key -text > employer-mobile-private.pem

echo "convert private Key to PKCS#8 format"
openssl pkcs8 -topk8 -inform PEM -outform DER -in employer-mobile-private.pem -out employer-mobile-private.der -nocrypt

echo "Output public key portion in .der format"
openssl rsa -in employer-mobile-private.pem -pubout -outform DER -out employer-mobile-public.der

cp employer-mobile-private.der ../mobile-app/src/main/credentials
cp employer-mobile-public.der ../mobile-app/src/main/credentials
cp employer-mobile-public.der ../database-backend/src/main/credentials
