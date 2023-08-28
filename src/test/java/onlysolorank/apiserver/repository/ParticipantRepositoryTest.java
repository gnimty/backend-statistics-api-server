package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.api.service.dto.ChampionPlaysDetailDto;
import onlysolorank.apiserver.api.service.dto.mostChampionsBySummonerDto;
import onlysolorank.apiserver.domain.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : ParticipantRepositoryTest
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 * 2023/08/09        solmin       findTop10ChampionStatsByPuuid 테스트
 * 2023/08/09        solmin       findTopChampionStatsByPuuid 테스트
 * 2023/08/16        solmin       findTopChampionsForEachSummoner 테스트
 */

@ActiveProfiles("test")
@SpringBootTest
class ParticipantRepositoryTest {
    @Autowired
    private ParticipantRepository participantRepository;

    private final String matchId = "KR_6574632344";
    private final String testPuuid1 = "foXeHpqOrzJdIq3nXyjUZCcksCERlqkKnjT0NQ77zmT26ydwv6I3UzWcr4awGSsJvFOghhfdZrRdZA";
    private final String testPuuid2 = "EGwfiiFYMXwYxIRx1Fl1b2NhGFWAQiQfe2gb5SskB2nVTNQWqRkio9wpjlr0L1xKiJ2QGGraW1PpPg";
    @Test
    public void getParticipantsInMatch(){
        List<Participant> results = participantRepository.findByMatchId(matchId);

        for (Participant result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void getMost10Plays(){
        List<ChampionPlaysDetailDto> topChampionStatsByPuuid = participantRepository.findTopChampionStatsByPuuid(testPuuid1, 10);

        for (ChampionPlaysDetailDto championPlaysDetailDto : topChampionStatsByPuuid) {
            System.out.println(championPlaysDetailDto);
        }
    }

    @Test
    public void testGetTop3ChampionsByPuuids(){

        List<mostChampionsBySummonerDto> topChampionsForEachSummoner = participantRepository.findTopChampionsForEachSummoner(List.of(testPuuid1, testPuuid2), 3);

        for (mostChampionsBySummonerDto mostChampionsBySummonerDto : topChampionsForEachSummoner) {
            for(Integer championId : mostChampionsBySummonerDto.getChampionIds()){
                System.out.println("championId = " + championId);
            }
        }


    }
}