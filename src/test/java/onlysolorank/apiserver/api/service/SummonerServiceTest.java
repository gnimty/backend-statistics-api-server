package onlysolorank.apiserver.api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        String keyword = "h &^ I de on bu& S h";
        assertEquals(summonerService.keywordToInternalName(keyword), "hideonbush");

    }
}