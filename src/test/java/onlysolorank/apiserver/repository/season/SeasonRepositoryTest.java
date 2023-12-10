package onlysolorank.apiserver.repository.season;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import onlysolorank.apiserver.domain.Season;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatistics;
import onlysolorank.apiserver.repository.statistics.ChampionStatisticsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository.season
 * fileName       : SeasonRepositoryTest
 * author         : solmin
 * date           : 12/10/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/10/23        solmin       최초 생성
 */
@ActiveProfiles("test")
@SpringBootTest
class SeasonRepositoryTest {
    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    void 단일시즌정보_조회() {

        Optional<Season> season = seasonRepository.findTop1SeasonByOrderByStartAtDesc();
//
        System.out.println(season.get().getSeasonName());
//
//        if(oneByChampionId.isPresent()){
//            System.out.println("oneByChampionId = " + oneByChampionId.get());
//        }
    }
}