package cz.gargoyle.beerstore;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerStoreController {
	@RequestMapping("/")
	public String index() {
		return "BeerStore is up!";
	}

}
