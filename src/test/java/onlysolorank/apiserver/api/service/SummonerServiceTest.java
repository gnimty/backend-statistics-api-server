package onlysolorank.apiserver.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import javax.validation.ConstraintViolationException;
import onlysolorank.apiserver.api.controller.dto.KeywordReq;
import onlysolorank.apiserver.api.service.dto.SummonerDto;
import onlysolorank.apiserver.domain.dto.QueueType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    private final String wrongPatternMatchId = "walfwealfk.n";
    private final String keyword = "h &^ ____I de on bu& S h#KR1";
    private final String keyword2 = "김솔민#TO";
    @Autowired
    private SummonerService summonerService;

    @Test
    void 키워드_변환결과_확인() {
        assertEquals(new KeywordReq(keyword).keyword(), "hideonbush");

        assertFalse("hideonbus".equals(new KeywordReq(keyword).keyword()));
    }


    @Test
    void 잘못된_패턴의_매치ID로_조회하면_오류발생() {

        assertThrows(ConstraintViolationException.class,
            () -> summonerService.get20MatchesByOptionalLastMatchId("HideOnbush", wrongPatternMatchId, QueueType.ALL));
    }

    @Test
    void 키워드로_검색_테스트(){
        String search = new KeywordReq(keyword).keyword();
        List<SummonerDto> top5SummonersByInternalTagName = summonerService.getTop5SummonersByInternalTagName(search);

        for(SummonerDto s : top5SummonersByInternalTagName){
            System.out.println(s);
        }
    }

    @Test
    void 키워드로_검색_테스트_2(){
        String search = new KeywordReq(keyword2).keyword();
        List<SummonerDto> top5SummonersByInternalTagName = summonerService.getTop5SummonersByInternalTagName(search);

        for(SummonerDto s : top5SummonersByInternalTagName){
            System.out.println(s);
        }
    }


}