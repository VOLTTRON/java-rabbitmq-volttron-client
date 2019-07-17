# java rabbitmq volttron client

1. Create a VOLTTRON instance using https://volttron.readthedocs.io/en/develop/setup/index.html setup for RabbitMQ.
1. Make sure volttron is running before continuing.
1. Using the activated environment from step 1 (Note: change the paths to files below to be specific to your environment)
    1. Create a volttron private key / cert for the java client to connect with

    ````
    vctl certs create-ssl-keypair jackpot
    ````

    1. Export the private key / cert into a PKCS12 file to be imported into the JKS for the connection.

    ````
    vctl certs export-pkcs12 jackpot jackpot.p12
    ````

    1. Create Java Key Store (JKS).

    ````
    keytool -importkeystore -destkeystore jackpot-keystore.jks -srckeystore ./jackpot.p12 -srcstoretype PKCS12 -deststoretype PKCS12
    ````

    1. Create Trust Store.

    ````
    keytool -import -alias server1 -file ~/.volttron/certificates/certs/v2-root-ca.crt -keystore ./rabbitmq.ts
    ````
 1. Allow the user to access the volttron virtual host (assumes default virtual host in rabbitmq_config.yml from step 1).
  ````
  # vctl rabbitmq add-user instancename.user password
  vctl rabbitmq add-user v2.jackpot volttron
  ````
  1. Modify the information in Config.java for your environment.
  1. Run the RunTestClient.java.
