package onlysolorank.apiserver.repository.counter;

import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.domain.statistics.analysis.counter.BaseCounter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.counter
 * fileName       : ChampionCounterRepositoryCustomTest
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class ChampionCounterRepositoryCustomTest {

    @Autowired
    private ChampionCounterRepository championCounterRepository;

    @Test
    void findCounterChampions() {
        List<? extends BaseCounter> counterChampions = championCounterRepository.findCounterChampions(266L, PositionFilter.TOP, true);

        System.out.println("counterChampions = " + counterChampions);

    }
}