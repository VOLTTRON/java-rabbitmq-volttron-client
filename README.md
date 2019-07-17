Create a volttron private key / cert for the user to connect with

````
vctl certs create-ssl-keypair jackpot
````

Export the private key / cert into a PKCS12 file to be imported into the JKS for the connection

````
vctl certs export-pkcs12 jackpot jackpot.p12
````

Create Java Key Store (JKS)

````
keytool -importkeystore -destkeystore jackpot-keystore.jks -srckeystore ./jackpot.p12 -srcstoretype PKCS12 -deststoretype PKCS12
````

Create Trust Store

````
keytool -import -alias server1 -file ~/.volttron/certificates/certs/v2-root-ca.crt -keystore ./rabbitmq.ts
````