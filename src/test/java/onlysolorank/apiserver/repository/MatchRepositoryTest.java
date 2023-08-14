package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Summoner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : MatchRepositoryTest
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
class MatchRepositoryTest {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void getMatchesByMatchIdList(){
        // 리스트 여러개 생성
        List<String> matchIds = List.of("KR_6625112620", "KR_6625061672");

        // 쿼리 수행
        List<Match> matches = matchRepository.findByMatchIdInCustom(matchIds);

        for (Match match : matches) {
            System.out.println("match = " + match);

        }
        assertEquals(2, matches.size());
    }

}