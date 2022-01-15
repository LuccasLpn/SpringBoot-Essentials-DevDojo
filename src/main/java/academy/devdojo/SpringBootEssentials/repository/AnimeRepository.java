package academy.devdojo.SpringBootEssentials.repository;

import academy.devdojo.SpringBootEssentials.domain.Anime;

import java.util.List;

public interface AnimeRepository{

    List<Anime> listAll();
}
