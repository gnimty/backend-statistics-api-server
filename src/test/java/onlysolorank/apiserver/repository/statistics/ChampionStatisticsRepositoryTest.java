package onlysolorank.apiserver.repository.statistics;

import java.util.Optional;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository.statistics
 * fileName       : ChampionStatisticsRepositoryTest
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class ChampionStatisticsRepositoryTest {

    @Autowired
    private ChampionStatisticsRepository championStatisticsRepository;
//
//    @Autowired
//    private ChampionStatisticsRepositoryCustom championStatisticsRepositoryCustom;

    @Test
    void 단일통계정보_조회() {

        Optional<ChampionStatistics> oneByChampionId = championStatisticsRepository.findOneByChampionId(112L);
//
//        if(oneByChampionId.isPresent()){
//            System.out.println("oneByChampionId = " + oneByChampionId.get());
//        }
    }

}