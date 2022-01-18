package academy.devdojo.SpringBootEssentials.repository;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@DataJpaTest
@DisplayName("Test for AnimeRepositorys")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Saved creates anime when SuccessFull")
    public void save_PersistAnime_WhenSuccessFull(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    private Anime createAnime(){
        return Anime
                .builder()
                .name("Tokyo Ghoul")
                .build();
    }

}