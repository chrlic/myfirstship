package cz.gargoyle.beerstore;

import java.util.Map;

import groovy.json.JsonSlurper;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerStoreController {
	@RequestMapping("/")
	public String status() {
		return "BeerStore is up!";
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String orderGet() {
		return order();
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json")
//	public String orderPost(@RequestBody BeerOrder input) {
	public String orderPost() {
		return order();
	}

	private String order() {
		String esServerUri = "http://localhost:9200/";
		int localPort = 4567;
		String nodeId = "local";
		String updateString = 
		"	{ " +
		"	   \"script\" : \"ctx._source.counter+=1\", " +
		"	   \"upsert\": { " +
		"	       \"counter\": 1 " +
		"	   } " +
		"	}";
		ElasticSearchHttpClient client;
		
		client = new ElasticSearchHttpClient();
		client.serverUri = esServerUri;
		client.httpClient = HttpClientBuilder.create().build();

		ElasticSearchHttpResult response = client.update("order", "counter", "2", 5, updateString);
		JsonSlurper jsonSlurper = new JsonSlurper();
		Object esDoc;
		Map esDocMapped;
		if (response.body == null) { //first order creates counter, results in response code 201 which is understood as error 
			esDoc = jsonSlurper.parseText(response.errorBody);
		} else {
			esDoc = jsonSlurper.parseText(response.body);
		}
		
		esDocMapped = (Map)esDoc;
		
		return "{ \"order\" : \"" + esDocMapped.get("_version") + "\", \"nodeId\" : \"" + nodeId + "\" }";
	}
}
