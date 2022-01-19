package academy.devdojo.SpringBootEssentials.integration;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.repository.AnimeRepository;
import academy.devdojo.SpringBootEssentials.util.AnimeCreator;
import academy.devdojo.SpringBootEssentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @LocalServerPort
    private int port;

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("List Return list of an ime ine page object when successful")
    void list_ReturnsList0fAnimesInsidePageObject_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();
            Assertions.assertThat(animePage).isNotNull();
            Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
            Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
            Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }


}
