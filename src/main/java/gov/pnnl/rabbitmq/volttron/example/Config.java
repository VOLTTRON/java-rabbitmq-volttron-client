package gov.pnnl.rabbitmq.volttron.example;

public class Config {
	// VIP identity which is publishing to the volttron bus
	public final static String sender = "jackpot";
	// This is the queue that will be created for handling the messages.
	public final static String queueName = "rabbitmq-java-test";
	// Instance name from the rabbitmq_config.yml file in the volttron home directory
	public final static String instanceName = "v2";
	// virtual host for connecting the exchange to.
	public final static String exchangeName = "volttron";
	public final static String keyStorePath = "/home/osboxes/repos/volttron-develop-no-py3/jackpot-keystore.jks";
	public final static String trustStorePath = "/home/osboxes/repos/volttron-develop-no-py3/rabbitmq.ts";
	public final static String keyStorePassword = "volttron";
	public final static String trustStorePassword = "volttron";
	
}
