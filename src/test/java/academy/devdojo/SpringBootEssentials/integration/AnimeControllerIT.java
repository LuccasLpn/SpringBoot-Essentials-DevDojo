package academy.devdojo.SpringBootEssentials.integration;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.domain.LuccasUser;
import academy.devdojo.SpringBootEssentials.repository.AnimeRepository;
import academy.devdojo.SpringBootEssentials.repository.LuccasUserRepository;
import academy.devdojo.SpringBootEssentials.requests.AnimePostRequestBody;
import academy.devdojo.SpringBootEssentials.util.AnimeCreator;
import academy.devdojo.SpringBootEssentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.SpringBootEssentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;


    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private LuccasUserRepository luccasUserRepository;

    private static  final LuccasUser USER = LuccasUser.builder()
            .name("Luccas2")
            .password("{bcrypt}$2a$10$sQsgQWjVQZxF5NT0QAZLfOMs80pUP/qdfrjAJfxHGefdBtlVVaNs6")
            .userName("Luccas2")
            .authorities("ROLE_USER")
            .build();

    private static  final LuccasUser ADMIN = LuccasUser.builder()
            .name("ADMIN")
            .password("{bcrypt}$2a$10$sQsgQWjVQZxF5NT0QAZLfOMs80pUP/qdfrjAJfxHGefdBtlVVaNs6")
            .userName("Luccas")
            .authorities("ROLE_ADMIN,ROLE_ADMIN")
            .build();


    @TestConfiguration
    @Lazy
    static class Config{

        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("Luccas2", "academy");
            return new TestRestTemplate(restTemplateBuilder);

        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("Luccas", "academy");
            return new TestRestTemplate(restTemplateBuilder);

        }
    }

    @Test
    @DisplayName("List Return list of an ime ine page object when successful")
    void list_ReturnsList0fAnimesInsidePageObject_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();
            Assertions.assertThat(animePage).isNotNull();
            Assertions.assertThat(animePage.toList())
                    .isNotEmpty()
                    .hasSize(1);
            Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll Return list of when successful")
    void listAll_ReturnsListAll_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animes).isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

   @Test
    @DisplayName("findById Return anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();

        Anime animes = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(animes)
                .isNotNull();
        Assertions.assertThat(animes.getId()).isNotNull().isEqualTo(expectedId);
    }

   @Test
    @DisplayName("findByName Return a list of anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
               new ParameterizedTypeReference<List<Anime>>() {
               }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

   @Test
    @DisplayName("findByName Return an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenSuccessful(){

       luccasUserRepository.save(USER);
       List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/find?name=dbz", HttpMethod.GET, null,
               new ParameterizedTypeReference<List<Anime>>() {
               }).getBody();

       Assertions.assertThat(animes)
               .isNotNull()
               .isEmpty();
    }

  @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        luccasUserRepository.save(USER);
        AnimePostRequestBody animePostRequestBody =
                AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity =
                testRestTemplateRoleUser.postForEntity("/animes",
                        animePostRequestBody,
                        Anime.class);
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");
        ResponseEntity<Void> animeResponseEntity =
                testRestTemplateRoleUser.exchange("/animes",
                HttpMethod.PUT,
                new HttpEntity<>(savedAnime),
                Void.class);
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        luccasUserRepository.save(ADMIN);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,null, Void.class, savedAnime.getId());
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_returns403_WhenSuccessful(){
        luccasUserRepository.save(USER);
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,null, Void.class, savedAnime.getId());
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
