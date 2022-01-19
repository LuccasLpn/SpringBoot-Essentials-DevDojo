package academy.devdojo.SpringBootEssentials.util;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody
                .builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }

}
