package onlysolorank.apiserver.repository.analysis;

import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.api.controller.dto.TierFilter;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionAnalysis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository.analysis
 * fileName       : ChampionAnalysisRepositoryTest
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
class ChampionAnalysisRepositoryTest {
    @Autowired
    private ChampionAnalysisRepository championAnalysisRepository;

    private Long AATROX = 266L;

    @Test
    void 챔피언_티어_포지션으로_검색(){
        ChampionAnalysis byChampionIdAndPositionAndTier =
            championAnalysisRepository.findTop1ByChampionIdAndPositionAndTierOrderByVersionDesc(AATROX, PositionFilter.TOP, TierFilter.DIAMOND).get();

        System.out.println("byChampionIdAndPositionAndTier = " + byChampionIdAndPositionAndTier);
    }
}