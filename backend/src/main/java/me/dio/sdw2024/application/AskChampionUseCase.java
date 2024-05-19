package me.dio.sdw2024.application;

import me.dio.sdw2024.domain.exception.ChampionNotFoundException;
import me.dio.sdw2024.domain.ports.ChampionsRepository;
import me.dio.sdw2024.domain.ports.GenerativeAIApi;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAIApi genAiApi) {

    public String askChampion(Long id, String question){
        var champion =repository.findById(id)
                .orElseThrow(() -> new ChampionNotFoundException(id));

        String context = champion.generateContextByQuestion(question);

        String objective ="""
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responsa perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
                """;

        return genAiApi.generateContent(objective,context);
    }
}
