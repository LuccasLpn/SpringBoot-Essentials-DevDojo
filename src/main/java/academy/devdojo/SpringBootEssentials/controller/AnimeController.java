package academy.devdojo.SpringBootEssentials.controller;

import academy.devdojo.SpringBootEssentials.domain.Anime;
import academy.devdojo.SpringBootEssentials.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
public class AnimeController {

    @Autowired
    private DateUtil dateUtil;


    @GetMapping(path = "list")
    public List<Anime> list(){
        log.info(dateUtil.formatLocalDateTimeTobatabaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Boku No Hero"), new Anime("Berserk"));
}
}
