package academy.devdojo.SpringBootEssentials.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequestBody {

    @NotEmpty(message = "The Anime name cannot be empty")
    private String name;

}
