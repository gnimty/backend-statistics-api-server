package onlysolorank.apiserver.api.service;

import onlysolorank.apiserver.api.controller.dto.KeywordRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerServiceTest
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 */


@ActiveProfiles("test")
@SpringBootTest
class SummonerServiceTest {

    @Autowired
    private SummonerService summonerService;

    @Test
    void keywordToInternalName() {
        String keyword = "h &^ ____I de on bu& S h";
        assertEquals(new KeywordRequestDto("hideonbush").getInternalName(), "hideonbush");
    }
}