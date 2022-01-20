package academy.devdojo.SpringBootEssentials.configurer.client;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringCLient {

    public static void main(String[] args) {
        ResponseEntity<Anime> Entity = new RestTemplate().getForEntity(
                "http://localhost:8080/animes/{id}",
                Anime.class, 2);
        log.info(Entity);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);

        log.info(Arrays.toString(animes));

        //@formatter:off
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        //@formatter:on
        log.info(exchange.getBody());


/*        Anime kingdom = Anime.builder().name("kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
        log.info("saved anime {}", kingdomSaved);*/


        Anime samurai = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiSaved = new RestTemplate().exchange(
                "http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samurai, createJsonHeader()),
                Anime.class);
        log.info("saved anime {}", samuraiSaved);

        Anime animeToBeupdate = samuraiSaved.getBody();
        animeToBeupdate.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiupdate = new RestTemplate().exchange(
                "http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeupdate, createJsonHeader()),
                Void.class);
        log.info(samuraiupdate);

        ResponseEntity<Void> samuraiDelete = new RestTemplate().exchange(
                "http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class, animeToBeupdate.getId());
        log.info(samuraiDelete);


    }

    private static HttpHeaders createJsonHeader () {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }



}
