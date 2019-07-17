package rabbitmq.volttron.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConnections {
	private Connection connection = null;

	public Connection connection() throws GeneralSecurityException, IOException {

		char[] keyPassphrase = "volttron".toCharArray();
		KeyStore ks = KeyStore.getInstance("PKCS12");
		// ks.load(new
		// FileInputStream("/home/osboxes/.volttron/certificates/jackpot-keystore.jks"),
		// keyPassphrase);
		ks.load(new FileInputStream("/home/osboxes/repos/volttron-develop-no-py3/foobar-keystore.jks"), keyPassphrase);

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, keyPassphrase);

		char[] trustPassphrase = "volttron".toCharArray();
		KeyStore tks = KeyStore.getInstance("JKS");
		tks.load(new FileInputStream("/home/osboxes/repos/volttron-develop-no-py3/rabbitmq.ts"), trustPassphrase);
		// tks.load(new
		// FileInputStream("/home/osboxes/.volttron/certificates/rabbitmq.ts"),
		// trustPassphrase);

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(tks);

		SSLContext c = SSLContext.getInstance("TLSv1.2");
		c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5671);
		factory.useSslProtocol(c);
		factory.setVirtualHost("volttron");

		try {
			connection = factory.newConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	public void close() {

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
