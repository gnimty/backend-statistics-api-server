package onlysolorank.apiserver.api.service;

import onlysolorank.apiserver.api.controller.dto.KeywordReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;

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

    private final String wrongPatternMatchId = "walfwealfk.n";
    private final String keyword = "h &^ ____I de on bu& S h";

    @Test
    void 키워드_변환결과_확인() {
        assertEquals(new KeywordReq(keyword).getKeyword(), "hideonbush");

        assertFalse("hideonbus".equals(new KeywordReq(keyword).getKeyword()));
    }


    @Test
    void 잘못된_패턴의_매치ID로_조회하면_오류발생(){

        assertThrows(ConstraintViolationException.class, ()-> summonerService.get20MatchesByLastMatchId("HideOnbush", wrongPatternMatchId));
    }




}