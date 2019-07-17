/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rabbitmq.volttron.connection;

import java.io.IOException;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

public class RabbitPublish implements Runnable {
	private Channel channel = null;

	public RabbitPublish(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		VIP vip = new VIP(Config.sender);
		Gson gson = new Gson();
		String message = gson.toJson(vip);
		
		try {
			int i = 0;
			while (i < 100) {
				if (i > 0) {
					if (i % 2 == 0) {
						vip.setMessage("I am an even java message");
					} else {
						vip.setMessage("I am an odd java message, yes very odd indeed :)");
					}
				}
				message = gson.toJson(vip);

				channel.basicPublish("volttron", "__pubsub__."+ Config.instanceName + "." + Config.sender, null, message.getBytes());
				i++;
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
