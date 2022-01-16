package academy.devdojo.SpringBootEssentials.repository;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
