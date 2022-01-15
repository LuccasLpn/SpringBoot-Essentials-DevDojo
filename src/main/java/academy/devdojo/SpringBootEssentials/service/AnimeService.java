package academy.devdojo.SpringBootEssentials.service;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {

    private static List<Anime> animes;
    static {
        animes = new ArrayList<>(List.of(new Anime(1l,"Boku No Hero"), new Anime(2l,"Berserk")));
    }
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

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 10000));
        animes.add(anime);
        return anime;
    }

    public void delete(Long id) {
        animes.remove(findById(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);

    }
}
