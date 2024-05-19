package me.dio.sdw2024.adapters.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.sdw2024.application.AskChampionUseCase;
import me.dio.sdw2024.application.ListChampionsUseCase;
import me.dio.sdw2024.domain.model.Champion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Campeões", description = "domínio de campeões do Lol")
@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionUseCase useCase){

    @PostMapping("/{id}/ask")
    public AskChampionResponse findAllChampions(@PathVariable Long id, @RequestBody AskChampionRequest request){
      String answer = useCase.askChampion(id, request.question);

      return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question){};
    public record AskChampionResponse(String answer){};

}
