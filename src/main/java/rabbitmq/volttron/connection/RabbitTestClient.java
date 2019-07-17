package rabbitmq.volttron.connection;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.rabbitmq.client.Channel;

public class RabbitTestClient {
	
	public static void main(String[] args) throws Exception {
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
