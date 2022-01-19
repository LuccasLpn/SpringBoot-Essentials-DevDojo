package academy.devdojo.SpringBootEssentials.controller;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.requests.AnimePostRequestBody;
import academy.devdojo.SpringBootEssentials.requests.AnimePutRequestBody;
import academy.devdojo.SpringBootEssentials.service.AnimeService;
import academy.devdojo.SpringBootEssentials.util.AnimeCreator;
import academy.devdojo.SpringBootEssentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.SpringBootEssentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeService;



    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(
                List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeService.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeService.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeService.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeService.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.doNothing().when(animeService).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeService).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("List Return list of an ime ine page object when successful")
    void list_ReturnsList0fAnimesInsidePageObject_WhenSuccessful(){
       String expectedName = AnimeCreator.createAnimeValidAnime().getName();

       Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll Return list of when successful")
    void listAll_ReturnsListAll_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();

        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById Return anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createAnimeValidAnime().getId();

        Anime animes = animeController.findById(1).getBody();

        Assertions.assertThat(animes)
                .isNotNull();

        Assertions.assertThat(animes.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName Return a list of anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName Return an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenSuccessful(){
        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save Return anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime animes = animeController.save(
        AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        Assertions.assertThat(animes).isNotNull().isEqualTo(AnimeCreator.createAnimeValidAnime());
    }

    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

        ResponseEntity<Void> entity = animeController.
                replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_DeleteAnime_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeController.delete(1))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.
                delete(1);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}