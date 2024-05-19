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

import java.util.ArrayList;
import java.util.List;

@FeignClient(name ="OpenIAChatApi",url = "${openai.base-url}", configuration = OpenIAChatApi.Config.class)
@ConditionalOnProperty(name = "generative-ai.provider",havingValue = "openai")
public interface OpenIAChatApi extends GenerativeAIApi {
    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq requisition);
    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", context),
                new Message("user", objective)
        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        OpenAiChatCompletionResp response = chatCompletion(req);

        try {
            OpenAiChatCompletionResp resp = chatCompletion(req);
            return resp.choices().getFirst().message().content();
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API da OpenAI.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API da OpenAI não contem os dados esperados.";
        }
    }
    record OpenAiChatCompletionReq(String model, List<Message> messages){}

    record OpenAiChatCompletionResp(String model, List<Choice> choices){}
    record Message(String role, String content){};
    record Choice(Message message) { }

    class Config{
        @Bean
        public RequestInterceptor apikeyrequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate ->  requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }

    }
}
