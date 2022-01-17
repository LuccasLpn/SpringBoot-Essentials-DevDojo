package academy.devdojo.SpringBootEssentials.mapper;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.requests.AnimePostRequestBody;
import academy.devdojo.SpringBootEssentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {


    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Autowired
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    @Autowired
    public abstract Anime toAnime(AnimePutRequestBody animePostRequestBody);


}
