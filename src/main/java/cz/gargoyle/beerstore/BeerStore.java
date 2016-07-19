package cz.gargoyle.beerstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeerStore {

	public static void main(String[] args) {
		ElasticSearchService server = new ElasticSearchService();
		server.setup();
		
        SpringApplication.run(BeerStore.class, args);
	}

}
