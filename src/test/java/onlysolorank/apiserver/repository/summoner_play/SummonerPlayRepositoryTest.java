package onlysolorank.apiserver.repository.summoner_play;

import onlysolorank.apiserver.domain.SummonerPlay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner_play
 * fileName       : SummonerPlayRepositoryTest
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class SummonerPlayRepositoryTest {
    @Autowired
    private SummonerPlayRepository summonerPlayRepository;

    private final String puuid1 = "1XBUVjoa7tHwOP5XSmphaXOib5cwn03X1qzFgnYk1C5FpUOPQds4w56YUzMIKamPO-Lj2SUBGE9tEg";

    @Test
    public void PUUID로_챔피언_플레이정보_가져오기(){
        List<SummonerPlay> summonerPlays = summonerPlayRepository.findSummonerPlaysByPuuid(puuid1);

        for (SummonerPlay s : summonerPlays){
            System.out.printf("championName = %s,  championPlays = %d\n", s.getChampionName(), s.getTotalPlays());
        }

    }

    @Test
    public void 조건으로_챔피언_플레이정보_가져오기(){
        List<SummonerPlay> summonerPlays =
            summonerPlayRepository.findSpecialists("Aatrox", 30);

        for (SummonerPlay s : summonerPlays){
            System.out.printf("championName = %s,  championPlays = %d\n", s.getChampionName(), s.getTotalPlays());
        }

    }


    @Test
    public void 조건으로_챔피언_플레이정보_가져오기_몽고템플릿(){
        List<SummonerPlay> summonerPlays1 =
            summonerPlayRepository.findTop100SummonerPlaysByChampionNameAndTotalPlaysGreaterThanEqualOrderByTotalPlaysDesc("Aatrox", 30);

        List<SummonerPlay> summonerPlays2 =
            summonerPlayRepository.findSpecialists("Aatrox", 30);

        for (int i=0;i<summonerPlays1.size();i++) {
            Assertions.assertEquals(summonerPlays1.get(i), summonerPlays2.get(i));
        }
    }


}