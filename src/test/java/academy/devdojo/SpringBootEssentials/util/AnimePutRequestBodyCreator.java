package academy.devdojo.SpringBootEssentials.util;

import academy.devdojo.SpringBootEssentials.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody
                .builder()
                .id(AnimeCreator.createAnimeValidAnime().getId())
                .name(AnimeCreator.createAnimeValidAnime().getName())
                .build();
    }

}
