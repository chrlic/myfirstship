package cz.gargoyle.beerstore;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.elasticsearch.client.Client;

public class ElasticSearchService {

		ElasticSearchServer server;
		
		static String NETWORK_HOST = "0.0.0.0";
		static int NETWORK_PORT = 9200;
		
	    public void setup() {
			Map<String, String> config = new HashMap<String, String> ();
			config.put("network.host",NETWORK_HOST);
			config.put("path.data","/Users/mdivis/elasticsearch/data") ;
			config.put("path.home","/Users/mdivis/elasticsearch");
			config.put("script.inline", "true");
			config.put("script.indexed", "true");
			config.put("script.update", "true");
			
			config.put("http.cors.enabled", "true");
			config.put("http.cors.allow-origin", "*");
			config.put("cluster.name", "elasticsearch");
			server = new ElasticSearchServer(config);
			
			server.start();
	    }

	    public void cleanup() {
			server.stop();
			server = null;
	    }
		
		public Client getClient() {
			return server.getClient();
		}

		public ElasticSearchHttpClient getHttpClient() {
			// make more intelligent by getting node and port from localhost:9200/_nodes/process?pretty
			
			HttpClient client = HttpClientBuilder.create().build();
			String serverUri = "http://" + NETWORK_HOST + ":" + NETWORK_PORT;
			ElasticSearchHttpClient httpClient = new ElasticSearchHttpClient();
			httpClient.setHttpClient(client);
			httpClient.setServerUri(serverUri);
			
			return httpClient;
		}
		
		public static void main(String[] args) {
			ElasticSearchService server = new ElasticSearchService();
			server.setup();
			
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
