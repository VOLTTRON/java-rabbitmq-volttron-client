package gov.pnnl.rabbitmq.volttron.example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VIP implements Serializable {
	
	private String bus = "rmq";
	private String sender = "rabbitmq-java-test";
	private Map<String, Map> headers = new HashMap<String, Map>();
	private String message = "Hello from Java Client";
	
	public VIP(String sender) {
		this.sender = sender;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}	
}
