package onlysolorank.apiserver.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.repository.match.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    private final String matchId1 = "KR_6654612499";
    private final String matchId2 = "KR_6654548635";
    private final String wrongMatchId = "awefawefefwe";
    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void 매치ID_두개로조회() {
        List<String> matchIds = List.of(matchId1, matchId2);

        List<Match> matches = matchRepository.findMatchesByMatchIdIn(matchIds);

        assertEquals(2, matches.size());
    }

    @Test
    public void 매치ID_하나로조회() {
        List<String> matchIds = List.of(matchId1);

        List<Match> matches = matchRepository.findMatchesByMatchIdIn(matchIds);

        assertEquals(1, matches.size());

        matchRepository.findMatchByMatchId(matchId2)
            .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND));
    }

    @Test
    public void 존재하지_않는_매치ID로_조회() {
        assertThrows(CustomException.class, () ->
            matchRepository.findMatchByMatchId(wrongMatchId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESULT_NOT_FOUND)));
    }


}