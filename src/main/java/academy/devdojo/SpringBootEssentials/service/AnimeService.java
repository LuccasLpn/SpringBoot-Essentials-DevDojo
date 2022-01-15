package academy.devdojo.SpringBootEssentials.service;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {

    private List<Anime> animes = List.of(new Anime(1l,"Boku No Hero"), new Anime(2l,"Berserk"));

/*    @Autowired
    private AnimeRepository animeRepository;*/

    public List<Anime> listAll(){
        return animes;
    }

    public Anime findById(Long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Anime ID Not Found"));
    }

}
