package onlysolorank.apiserver.repository;

import java.util.Optional;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.repository.summoner.SummonerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;


/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : SummonerRepositoryTest
 * author         : solmin
 * date           : 2023/07/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/07        solmin       최초 생성
 * 2023/07/24        solmin       internalName test 추가
 */


@ActiveProfiles("test")
@SpringBootTest
class SummonerRepositoryTest {

    private final String puuid = "DzbTX2IssL1RTF4niZAsB0qPiYhGTXVff1ggA4k_PjwdoWW2r0QaOmLVqlmcAqAhONmGu0YyJfsURA";

    private final String tagName = "hideonbush#KR";

    @Autowired
    private SummonerRepository summonerRepository;

    @Test
    public void 소환사_count_테스트() {
        long l = summonerRepository.countBy();

        System.out.println("l = " + l);
    }

    @Test
    public void getTop20UserTest() {
        Page<Summoner> allSummoners = summonerRepository.findAll(PageRequest.of(0, 20));

        allSummoners.forEach(summoner -> {
            System.out.println("summoner = " + summoner);

        });
    }

    @Test
    public void 특정_PUUID_소환사_정보_조회() {
        Optional<Summoner> summoner = summonerRepository.findSummonerByPuuid(puuid);

        System.out.println("summonerInfo = " + summoner);
    }

//    @Test
//    public void 특정_tagName으로_상위_5명_조회(){
//    }


//    @Test
//    public void getSummonersByMmrGreaterThanEqual() {
//        List<Summoner> summoners = summonerRepository.findSummonersByMmrGreaterThanEqual(STD_MMR);
//
//        summoners.forEach(summoner -> {
//            Assertions.assertThat(summoner.getMmr()).isGreaterThanOrEqualTo(STD_MMR);
//            System.out.println("mmr =  " + summoner.getMmr());
//        });
//    }
}