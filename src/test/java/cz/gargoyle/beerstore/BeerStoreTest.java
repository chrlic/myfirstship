package cz.gargoyle.beerstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.gargoyle.beerstore.BeerStore;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BeerStore.class)
@WebAppConfiguration
public class BeerStoreTest {

	@Test
	public void contextLoads() {
	}

}
