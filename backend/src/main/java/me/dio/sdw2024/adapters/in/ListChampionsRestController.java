package me.dio.sdw2024.adapters.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.sdw2024.adapters.out.ChampionJDBCRepository;
import me.dio.sdw2024.application.ListChampionsUseCase;
import me.dio.sdw2024.domain.model.Champion;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Campeões", description = "domínio de campeões do Lol")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController (ListChampionsUseCase useCase){

    @GetMapping
    public List<Champion> findAllChampions(){
        return useCase.findAll();
    }
    @GetMapping("/{id}")
    public Champion findOneChampion(@PathVariable Long id){
        return useCase.findOne(id);
    }

}
