package onlysolorank.apiserver.repository.champion;

import onlysolorank.apiserver.domain.Rotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


/**
 * packageName    : onlysolorank.apiserver.repository.champion
 * fileName       : ChampionRepositoryImplTest
 * author         : solmin
 * date           : 10/9/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/9/23        solmin       최초 생성
 */
@SpringBootTest
@ActiveProfiles("test")
class ChampionRepositoryImplTest {
    @Autowired
    private ChampionRepository championRepository;

    @Test
    void 챔피언_로테이션_조회_테스트() {
        List<Rotation> rotationChampions = championRepository.findRotationChampions();

        for (Rotation rotationChampion : rotationChampions) {
            System.out.println("rotationChampion = " + rotationChampion);
        }
    }

}