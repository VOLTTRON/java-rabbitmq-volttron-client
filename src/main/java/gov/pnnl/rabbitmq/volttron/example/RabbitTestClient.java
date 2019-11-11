package gov.pnnl.rabbitmq.volttron.example;

import com.rabbitmq.client.Connection;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class RabbitTestClient {
	
	public static void main(String[] args) throws Exception {
		System.out.println("This is a rabbitmq test client, please use at your own risk\n");
		File ksfile = new File(Config.keyStorePath);
		boolean isValid = true;
		
		if (!ksfile.exists()){
			System.err.println(String.format("Key Store Path: %s does not exist", Config.keyStorePath));
			isValid = false;
		}
		
		File tsfile = new File(Config.keyStorePath);
				
		if (!tsfile.exists()){
			System.err.println(String.format("Trust Store Path: %s does not exist", Config.trustStorePath));
			isValid = false;
		}
		
		if (!isValid) {
			System.err.println("Exiting due to missing trust and/or key store paths in \n\tgov/pnnl/rabbitmq/volttron/example/Config.java");
			System.exit(0);
		}
		
		System.out.println("Current configuration has the following attributes:");
		System.out.println("\tTrust Store Path: "+Config.trustStorePath);
		System.out.println("\tKey Store Path: " + Config.keyStorePath);
		System.out.println("If this is not the correct please modify the file");
		System.out.println("\tgov/pnnl/rabbitmq/volttron/example/Config.java");
		System.out.println("And run the command ./gradlew jarWithDependencies from the top level of this repository.");
		Connection conn = null;
		RabbitConnections connections = new RabbitConnections();
		try {
			conn = connections.connection();
			RabbitSubscription subscriber = new RabbitSubscription(conn.createChannel());
			Thread s = new Thread(subscriber);
			s.start();
			RabbitPublish publisher = new RabbitPublish(conn.createChannel());
			Thread p = new Thread(publisher);
			p.start();
			while (true) {
				Thread.sleep(2000);
			}
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
	}

}
