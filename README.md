# java rabbitmq volttron client

## Requirements

1. java jdk version 8
1. A rabbitmq configured VOLTTRON instance.

## Assumptions

The following directions assume:

- a default VOLTTRON_HOME (~/.volttron)
- an instance name of volttron1
- all passwords associated with the instructions must be volttron

## Procedure

1. Create a VOLTTRON instance using https://volttron.readthedocs.io/en/develop/setup/index.html setup for RabbitMQ.
1. Make sure volttron is running. (run the command vctl status)
1. Change directory (cd) out of the volttron directory (cd ..)
1. Clone the repository (git clone https://github.com/VOLTTRON/java-rabbitmq-volttron-client)
1. cd into the newly cloned directory (cd java-rabbitmq-volttron-client)

The following steps will create a java key store and java trust store to connect to a secure rabbitmq bus and add a user to the
rabbitmq management in order for the client to connect.  The following steps should be run from a volttron rabbitmq activated environment.   

1. Create java key/trust stores
	
    1. Create a volttron private key / cert for the java client to connect with (This will be stored in $VOLTTRON_HOME/certificates)

	    ````vctl certs create-ssl-keypair jackpot````

    1. Export the private key / cert into a PKCS12 file to be imported into the JKS for the connection. (There should be a jackpot.p12 file in the current directory)

	    ````vctl certs export-pkcs12 jackpot jackpot.p12````

    1. Create Java Key Store (JKS). (There should be a jackpot-keystore.jks file in the current directory)

    	````keytool -importkeystore -destkeystore jackpot-keystore.jks -srckeystore ./jackpot.p12 -srcstoretype PKCS12 -deststoretype PKCS12````

    1. Create Trust Store. (There should be a rabbitmq.ts file in the current directory)

    	````keytool -import -alias server1 -file ~/.volttron/certificates/certs/<INSTANCE_NAME>-root-ca.crt -keystore ./rabbitmq.ts````
 
 1. Allow the user to access the volttron virtual host (assumes default virtual host in rabbitmq_config.yml from step 1).
	
 	````# vctl rabbitmq add-user instancename.user password
	vctl rabbitmq add-user v2.jackpot volttron
	````
	
 1. Step 1 of this section created two files needed for connecting to the secure rabbitmq bus (jackpot-keystore.jks and rabbitmq.ts) respectively.  Modify the file 
 	
 	````test-rabbitmq-volttron-connection/src/main/java/gov/pnnl/rabbitmq/volttron/example/Config.java```` 
 	
 	specifying the correct locations of these files for the variables keyStorePath and trustStorePath.

## Building and Running

The following commands should be run from the root of the java-rabbitmq-volttron-client repository.

 1. Create the executable jar by executing
	
	````./gradlew jarWithDependencies````
	
 1. Execute the test by runing

	````java -jar build/libs/test-rabbitmq-volttron-connection-all-1.0.1.jar```` 
	

