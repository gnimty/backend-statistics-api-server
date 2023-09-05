package onlysolorank.apiserver.api.service;

import onlysolorank.apiserver.domain.SummonerHistory;
import onlysolorank.apiserver.domain.dto.History;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerHistoryServiceTest
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class SummonerHistoryServiceTest {

    @Autowired
    private SummonerHistoryService summonerHistoryService;

    private final String puuid1 = "pj_RWKeC23jgj_hzOX2jExi8CKukbsLg7ByV0JSdly0P7QcRXk8Fi8da90-t8MYRT1DoCU1X5DpDsw";

    @Test
    void 소환사_히스토리_업데이트일_검증(){
        SummonerHistory summonerHistoryByPuuid = summonerHistoryService.getSummonerHistoryByPuuid(puuid1);

        List<History> history = summonerHistoryByPuuid.getHistory();

        for (History h : history) {
            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;

            LocalDateTime dateTime = LocalDateTime.parse(h.getUpdatedAt().toString(), isoFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(outputFormatter);

            System.out.println("업데이트일: " + formattedDateTime);
        }

    }

}