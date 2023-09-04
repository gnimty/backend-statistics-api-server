package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.repository.summoner_match.SummonerMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : SummonerMatchRepositoryTest
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */
@ActiveProfiles("test")
@SpringBootTest
class SummonerMatchRepositoryTest {
    @Autowired
    private SummonerMatchRepository summonerMatchRepository;

    private final static String testPuuid = "toDrnYVtSfaNMqhh8Vq3hvFau7DqbI54-GXN7-tjDrw2y55U6RpwadXH_lZnJ5MUPFjk-WqxEePd1w";

//    @Test
//    public void get20MatchIds(){
//        // 하나 요청 수행했을 때
//        Optional<SummonerMatch> result =
//            summonerMatchRepository.findOneByPuuid(testPuuid);
//
//        List<String> collect = result.get().getSummonerMatchIds().stream()
//            .limit(20)
//            .collect(Collectors.toList());
//
//        for (String matchId : collect) {
//            System.out.println("matchId = " + matchId);
//        }
//
//        // 존재하는지
//        assertEquals(20, collect.size());
//    }


}