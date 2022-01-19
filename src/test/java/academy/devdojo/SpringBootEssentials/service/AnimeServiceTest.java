package academy.devdojo.SpringBootEssentials.service;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.exception.BadRequestException;
import academy.devdojo.SpringBootEssentials.repository.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)

class animeRepositoryTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;



    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(
                List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepository.findAll())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepository.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.doNothing().when(animeRepository).delete(ArgumentMatchers.any(Anime.class));

    }

    @Test
    @DisplayName("listAll Return list of an ime ine page object when successful")
    void listAll_ReturnsList0fAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();

        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable Return list of when successful")
    void listAllNonPageable_ReturnsListAll_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();

        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException Return anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createAnimeValidAnime().getId();

        Anime animes = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(animes)
                .isNotNull();

        Assertions.assertThat(animes.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException  ThrowBadRequestException Return anime when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowBadRequestException_WhenAnimeIsNotFound(){

        BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy (() -> animeService.findByIdOrThrowBadRequestException(1L));
    }
    

    @Test
    @DisplayName("findByName Return a list of anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeValidAnime().getName();

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName Return an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenSuccessful(){
        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save Return anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
        Anime animes = animeService.save(
                AnimePostRequestBodyCreator.createAnimePostRequestBody());
        Assertions.assertThat(animes).isNotNull().isEqualTo(AnimeCreator.createAnimeValidAnime());
    }

    @Test
    @DisplayName("replace update anime when successful")
    void replace_UpdateAnime_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_DeleteAnime_WhenSuccessful(){
        Assertions.assertThatCode(()-> animeService.delete(1))
                .doesNotThrowAnyException();
    }

}