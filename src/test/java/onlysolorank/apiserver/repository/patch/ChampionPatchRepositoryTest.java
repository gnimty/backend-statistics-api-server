package onlysolorank.apiserver.repository.patch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionPatch;
import onlysolorank.apiserver.repository.analysis.ChampionAnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository.patch
 * fileName       : ChampionPatchRepositoryTest
 * author         : solmin
 * date           : 1/5/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/5/24        solmin       최초 생성
 */

@ActiveProfiles("test")
@SpringBootTest
class ChampionPatchRepositoryTest {
    @Autowired
    private ChampionPatchRepository championPatchRepository;

    private Long AATROX = 266L;
    private Long ANIVIA = 34L;
    private Long JARVANIV = 59L;
    private Long AZIR = 268L;

    @Test
    void 특정버전의_챔피언_패치_조회(){
        List<ChampionPatch> byVersionLessThanEqualAndAndChampionId = championPatchRepository.findByVersionLessThanEqualAndAndChampionId(
            "13.24.1", String.valueOf(JARVANIV));

        for (ChampionPatch championPatch : byVersionLessThanEqualAndAndChampionId) {
            System.out.println("championPatch = " + championPatch);
        }
    }

    @Test
    void 패치노트_전부_조회(){
        List<ChampionPatch> all = championPatchRepository.findAll();

        for (ChampionPatch championPatch : all) {
            System.out.println("championPatch = " + championPatch);
        }
    }

    @Test
    void 특정_챔피언_조회(){
        List<ChampionPatch> all = championPatchRepository.findByChampionId(String.valueOf(AZIR));

        for (ChampionPatch championPatch : all) {
            System.out.println("championPatch = " + championPatch);
        }
    }
}