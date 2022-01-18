package academy.devdojo.SpringBootEssentials.repository;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Test for AnimeRepositorys")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Saved Persist anime when SuccessFull")
    public void save_PersistAnime_WhenSuccessFull(){
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("Saved updates anime when SuccessFull")
    public void save_UpdateAnime_WhenSuccessFull(){
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        savedAnime.setName("Overlod");
        Anime animeUpdate = this.animeRepository.save(savedAnime);
        Assertions.assertThat(animeUpdate).isNotNull();
        Assertions.assertThat(animeUpdate.getId()).isNotNull();
        Assertions.assertThat(animeUpdate.getName()).isEqualTo(savedAnime.getName());

    }

    @Test
    @DisplayName("Delete removes anime when SuccessFull")
    public void delete_RemovesAnime_WhenSuccessFull(){
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        this.animeRepository.delete(savedAnime);
        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());
        Assertions.assertThat(animeOptional.isEmpty());
    }

    @Test
    @DisplayName("Find By Name return list of anime when SuccessFull")
    void find_ByNameAnime_WhenSuccessFull(){
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = this.animeRepository.save(anime);
        String name = savedAnime.getName();
        List<Anime> byName = this.animeRepository.findByName(name);
        Assertions.assertThat(byName)
                .isNotEmpty()
                .contains(savedAnime);
    }

    @Test
    @DisplayName("Find By Name return empty list anime when no anime is found")
    void findByName_ReturnEmptyLit_When(){
        List<Anime> byName = this.animeRepository.findByName("xaxaxa");
        Assertions.assertThat(byName).isEmpty();
    }

    @Test
    @DisplayName("Saved Throw ConstraintViolationException when name is empty")
    public void save_ThrowConstraintViolationException_WhenIsEmpty(){
        Anime anime = new Anime();
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
    }


}