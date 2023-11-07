package onlysolorank.apiserver.repository.summoner_play;

import onlysolorank.apiserver.domain.SummonerPlay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    public void 여러조건으로_챔피언_플레이정보_가져오기(){
        Map<String, Long> championPairs = new HashMap<>() {{
            put("1XBUVjoa7tHwOP5XSmphaXOib5cwn03X1qzFgnYk1C5FpUOPQds4w56YUzMIKamPO-Lj2SUBGE9tEg", 120L);
            put("a6aFFmCsAD5tPh4AzbOs-ppx8AC-O44Qd2M5R_tIxsGSFhE7vxHukyNLVkchBXtERExrUml9mYFauQ", 23L);
            put("I8MwVF0TLOXfwT9LYhzDOPvrBn_9sDy8csBC_mead-A1OsNZP6nLM1NGPnEVF9BAiKZNUAzL9W35Cg", 51L);
            put("Q84HPpuEYgyTLqSO1qovoTlakacWTAM_iKldz8Y5y7Lq3fs6BtKp59MBKRplg_gDPLwYR0IOuSxyQQ", 23L);
            put("1XBUVjoa7tHwOP5XSmphaXOib5cwn03X1qzFgnYk1C5FpUOPQds4w56YUzMIKamPO-Lj2SUBGE9tEg", 132L);
            put("c2LSlbSQzj3UufcM62sI3uzy-CCGRaTGDTh0XYubnipZvuG47T_5tPLzrJGmJVaZrHvbulK35JtiDg", 23L);
            put("x4g2HkbMoWqidXd35K4e_3yFeGqyl4aeMM2bpsi3G3rjvMZqPV8c6geyWT_u8Mzr3IhcDOxm1DVN8", 13L);
        }};

        List<SummonerPlay> byPuuidChampionIdPairs = summonerPlayRepository.findByPuuidChampionIdPairs(championPairs);

        for (SummonerPlay byPuuidChampionIdPair : byPuuidChampionIdPairs) {
            System.out.println("byPuuidChampionIdPair = " + byPuuidChampionIdPair);
        }

    }

    @Test
    public void 최대10개가져오기_테스트(){
        List<SummonerPlay> summonerPlaysByPuuidAndLimit = summonerPlayRepository.findSummonerPlaysByPuuidAndLimit(puuid1, 10);

        for (SummonerPlay summonerPlay : summonerPlaysByPuuidAndLimit) {
            System.out.println("summonerPlay = " + summonerPlay);
        }

        assertThat(summonerPlaysByPuuidAndLimit.size()).isLessThanOrEqualTo(10);
    }

}