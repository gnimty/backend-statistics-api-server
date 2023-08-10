package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.api.dto.ChampionPlaysBriefDto;
import onlysolorank.apiserver.domain.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : ParticipantRepositoryTest
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 * 2023/08/09        solmin       findTop10ChampionStatsByPuuid 테스트
 */

@ActiveProfiles("test")
@SpringBootTest
class ParticipantRepositoryTest {
    @Autowired
    private ParticipantRepository participantRepository;

    private final String matchId = "KR_6574632344";
    private final String testPuuid = "E_Dv1ATl2pdem7ZvwOvES-ATHBr9j2QEkQNMUS7MSpjNEZYWjjpab0o8oD-76dX1V17OQfvtchsGDA";
    @Test
    public void getParticipantsInMatch(){
        List<Participant> results = participantRepository.findByMatchId(matchId);

        for (Participant result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void getMost10Plays(){
        List<ChampionPlaysBriefDto> topChampionStatsByPuuid = participantRepository.findTopChampionStatsByPuuid(testPuuid, 10);

        for (ChampionPlaysBriefDto championPlaysBriefDto : topChampionStatsByPuuid) {
            System.out.println(championPlaysBriefDto);
        }


    }
}