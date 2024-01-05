package onlysolorank.apiserver.repository.analysis;

import java.util.Optional;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.dto.Tier;
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
class ChampionAnalysisRankRepositoryTest {

    @Autowired
    private ChampionAnalysisRepository championAnalysisRepository;

    private Long AATROX = 266L;
    private Long ANIVIA = 34L;

    @Test
    void 챔피언_티어_포지션으로_검색() {
        Optional<ChampionAnalysis> byChampionIdAndPositionAndTier = championAnalysisRepository.findTop1ByChampionIdAndPositionAndTier(QueueType.RANK_SOLO, AATROX, Lane.TOP, Tier.diamond.name().toUpperCase());

        System.out.println("byChampionIdAndPositionAndTier = " + byChampionIdAndPositionAndTier.get());
    }

    @Test
    void 챔피언_티어_포지션_없이_검색() {
        Optional<ChampionAnalysis> byChampionIdAndPositionAndTier = championAnalysisRepository.findTop1ByChampionIdAndTier(QueueType.RANK_SOLO, ANIVIA, Tier.diamond.name().toUpperCase());

        System.out.println("byChampionIdAndPositionAndTier = " + byChampionIdAndPositionAndTier.get());
    }
}