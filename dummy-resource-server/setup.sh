keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=jwt, L=IN, S=IN, C=IN" -keypass mySecretKey -keystore jwt.jks -storepass mySecretKey
keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey -x509toreq 
