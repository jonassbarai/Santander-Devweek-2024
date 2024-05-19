package me.dio.sdw2024.application;

import me.dio.sdw2024.domain.model.Champion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.util.List;

@SpringBootTest
public class ListChampionsUseCaseIntegrationTest {
    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    public void testListChampions(){
        List<Champion> champions = listChampionsUseCase.findAll();
        Assertions.assertEquals(champions.size(),12);
        Assertions.assertNotNull(champions);
    }
}
