package me.dio.sdw2024.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.dio.sdw2024.domain.ports.GenerativeAIApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.AbstractDocument;
import java.util.List;

@FeignClient(name ="GoogleGeminiChatApi",url = "${gemini.base-url}", configuration = GeminiChatApi.Config.class)
@ConditionalOnProperty(name = "generative-ai.provider",havingValue = "GEMINI", matchIfMissing = true)
public interface GeminiChatApi extends GenerativeAIApi {
    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResponse textOnlyInput(GoogleGeminiRequest requisition);
    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s                
                """.formatted(objective,context);

        GoogleGeminiRequest  request = new GoogleGeminiRequest(
                List.of(
                        new Content(List.of(new Part(prompt)))
                ));

        try {
            GoogleGeminiResponse response = textOnlyInput(request);
            return response.candidates().getFirst().content().parts().getFirst().text();
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API do Google Gemini não contem os dados esperados.";
        }
    }
    record GoogleGeminiRequest(List<Content> contents){

    }
    record GoogleGeminiResponse(List<Candidate> candidates){}
    record Content(List<Part> parts){};
    record Part(String text){};
    record Candidate(Content content){};
    class Config{
        @Bean
        public RequestInterceptor apikeyrequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate ->  requestTemplate.query("key",apiKey);
        }
    }
}
