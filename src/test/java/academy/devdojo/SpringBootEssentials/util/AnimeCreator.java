package academy.devdojo.SpringBootEssentials.util;

import academy.devdojo.SpringBootEssentials.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime
                .builder()
                .name("Tokyo Ghoul")
                .build();
    }

    public static Anime createAnimeValidAnime(){
        return Anime
                .builder()
                .name("Tokyo Ghoul")
                .id(1L)
                .build();
    }
    public static Anime createValidUpdatedAnime(){
        return Anime
                .builder()
                .name("Tokyo Ghoul 2")
                .id(1L)
                .build();
    }

}
