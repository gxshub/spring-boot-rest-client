package csci316.demo.libraryclient;

import csci316.demo.libraryclient.model.Library;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

	final String rootUrl = "http://localhost:8081/libraries";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {

		return args -> {

			// CREATE
			Library entry = new Library();
			entry.setName("my library");
			Library library = restTemplate.postForObject(rootUrl, entry, Library.class);
			assert library != null;
			System.out.println("****CREATE****" + library);
			// READ
			restTemplate.getForObject(rootUrl+"/"+library.getId(), Library.class);
			System.out.println("****READ**** "+ library);
			// UPDATE
			library.setName("my new library");
			HttpEntity<Library> update = new HttpEntity<>(library);
			restTemplate.exchange(rootUrl+"/"+library.getId(), HttpMethod.PUT, update, Void.class);
			System.out.println("****UPDATE****"+ library);
			//DELETE
			//TODO
		};

	}

}
