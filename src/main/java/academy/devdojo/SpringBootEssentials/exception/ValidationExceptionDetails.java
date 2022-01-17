package academy.devdojo.SpringBootEssentials.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{

    private final String filds;
    private final String fildMessage;

}
