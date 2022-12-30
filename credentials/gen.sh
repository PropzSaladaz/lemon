function clean {
  rm *.srl
  rm *.pem
  rm *.key
  rm *.p12
  rm *.crt
  rm *.der
}

clean
# CA certificate
bash ca.sh
# Backend-Database keys & certificates for grpc communication
bash backend-database.sh
# Server-backend key & certificate fot spring-boot https
bash https-backend.sh
# Mobile private & public keys
bash mobile.sh

clean