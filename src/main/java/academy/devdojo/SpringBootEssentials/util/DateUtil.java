package academy.devdojo.SpringBootEssentials.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public String formatLocalDateTimeTobatabaseStyle (LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-DD-mm HH:mm:ss").format(localDateTime);
    }
}
