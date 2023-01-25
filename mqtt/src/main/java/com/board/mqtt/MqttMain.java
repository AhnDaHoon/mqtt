package com.board.mqtt;

import java.util.ArrayList;
import java.util.List;

public class MqttMain {
    public static void main(String[] args) {
        MyMqttSubClient subClient = new MyMqttSubClient();
		List<String> topics = new ArrayList<>();
		topics.add("test");
		topics.add("test2");
		subClient.init("tcp://ip:port", "id2").subscribe(topics);

		MyMqttPubClient sender = new MyMqttPubClient();
		new Thread(new Runnable() {
			@Override
			public void run() {
				sender.send("test", "{\n" +
						"  \"data\": [\n" +
						"    {\n" +
						"      \"CO2\": \"1255\",\n" +
						"      \"tvoc\": \"130\",\n" +
						"      \"temperature\": \"23.7\",\n" +
						"      \"humidity\": \"48.4\",\n" +
						"      \"pressure\": \"1021\",\n" +
						"      \"htsTemperature\": \"28.12\",\n" +
						"      \"htsHumidity\": \"40.25\",\n" +
						"      \"lpsPressure\": \"102.27\",\n" +
						"      \"proximity\": \"255\",\n" +
						"      \"acceleration\": \"119\",\n" +
						"      \"gyroscope\": \"119\",\n" +
						"      \"magneticfield\": \"20\",\n" +
						"      \"differencialPressure\": \"0\",\n" +
						"      \"altitude\": \"-49.16\",\n" +
						"      \"gasResistance\": \"41.83\"\n" +
						"    },\n" +
						"    {\n" +
						"      \"CO2\": \"1256\",\n" +
						"      \"tvoc\": \"131\",\n" +
						"      \"temperature\": \"23.8\",\n" +
						"      \"humidity\": \"48.5\",\n" +
						"      \"pressure\": \"1022\",\n" +
						"      \"htsTemperature\": \"28.52\",\n" +
						"      \"htsHumidity\": \"40.46\",\n" +
						"      \"lpsPressure\": \"102.50\",\n" +
						"      \"proximity\": \"245\",\n" +
						"      \"acceleration\": \"120\",\n" +
						"      \"gyroscope\": \"121\",\n" +
						"      \"magneticfield\": \"26\",\n" +
						"      \"differencialPressure\": \"1\",\n" +
						"      \"altitude\": \"-49.00\",\n" +
						"      \"gasResistance\": \"41.00\"\n" +
						"    }\n" +
						"  ]\n" +
						"}");
			}
		}).start();
    }
}
