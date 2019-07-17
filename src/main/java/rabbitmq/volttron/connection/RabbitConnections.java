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

	/**
	 * Create a connection to the Rabbitmq bus and return it.
	 * 
	 * @return open connection
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Connection connection() throws GeneralSecurityException, IOException {

		char[] keyPassphrase = Config.keyStorePassword.toCharArray();
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(new FileInputStream(Config.keyStorePath), keyPassphrase);

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, keyPassphrase);

		char[] trustPassphrase = Config.trustStorePassword.toCharArray();
		KeyStore tks = KeyStore.getInstance("JKS");
		tks.load(new FileInputStream(Config.trustStorePath), trustPassphrase);
		
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(tks);

		SSLContext c = SSLContext.getInstance("TLSv1.2");
		c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5671);
		factory.useSslProtocol(c);
		factory.setVirtualHost(Config.exchangeName);

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
