package rabbitmq.volttron.connection;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class Echo {

    public static void main(String[] args) throws Exception {
    	char[] keyPassphrase = "volttron".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        // ks.load(new FileInputStream("/home/osboxes/.volttron/certificates/jackpot-keystore.jks"), keyPassphrase);
        ks.load(new FileInputStream("/home/osboxes/repos/volttron-develop-no-py3/foobar-keystore.jks"), keyPassphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);

        char[] trustPassphrase = "volttron".toCharArray();
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream("/home/osboxes/repos/volttron-develop-no-py3/rabbitmq.ts"), trustPassphrase);
        // tks.load(new FileInputStream("/home/osboxes/.volttron/certificates/rabbitmq.ts"), trustPassphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);

        SSLContext c = SSLContext.getInstance("TLSv1.2");
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5671);
        factory.useSslProtocol(c);
        
        factory.setVirtualHost("volttron");
        //factory.enableHostnameVerification();

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare("rabbitmq-java-test", false, true, true, null);
        channel.basicPublish("volttron", "rabbitmq-java-test", null, "Hello, World".getBytes());

        GetResponse chResponse = channel.basicGet("rabbitmq-java-test", false);
        if (chResponse == null) {
            System.out.println("No message retrieved");
        } else {
            byte[] body = chResponse.getBody();
            System.out.println("Received: " + new String(body));
        }

        channel.close();
        conn.close();
    }
}