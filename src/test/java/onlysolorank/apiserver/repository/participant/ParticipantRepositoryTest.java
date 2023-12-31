package onlysolorank.apiserver.repository.participant;

import static onlysolorank.apiserver.repository.participant.ParticipantRepository.DistinctParticipantTeam;

import java.util.List;
import onlysolorank.apiserver.api.service.dto.ChampionPlayDto;
import onlysolorank.apiserver.api.service.dto.MostChampionsDto;
import onlysolorank.apiserver.api.service.dto.ParticipantBriefDto;
import onlysolorank.apiserver.domain.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : onlysolorank.apiserver.repository.participant
 * fileName       : ParticipantRepositoryTest
 * author         : solmin
 * date           : 2023/09/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/11        solmin       최초 생성, 기존 ParticipantRepositoryTest 이관
 */

@ActiveProfiles("test")
@SpringBootTest
class ParticipantRepositoryTest {

    private final String matchId = "KR_6574632344";
    private final String testPuuid1 = "foXeHpqOrzJdIq3nXyjUZCcksCERlqkKnjT0NQ77zmT26ydwv6I3UzWcr4awGSsJvFOghhfdZrRdZA";
    private final String testPuuid2 = "EGwfiiFYMXwYxIRx1Fl1b2NhGFWAQiQfe2gb5SskB2nVTNQWqRkio9wpjlr0L1xKiJ2QGGraW1PpPg";
    private final String puuid1 = "kA3ZOxLp3FsEyvO51kpP-rMY0fdNxnhKxDhlnx3yOuZBGWpluRVBgTW2AW4uxRTvUf4-_s3lYNAQ1Q";
    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    void 최근20게임_팀ID_승리여부_조회() {
        List<DistinctParticipantTeam> top20ByPuuidOrderByMatchId = participantRepository.findTop20ByPuuidAndQueueIdOrderByMatchIdDesc(
            puuid1, 420);

        for (DistinctParticipantTeam distinctParticipantTeam : top20ByPuuidOrderByMatchId) {
            System.out.println("distinctParticipantTeam = " + distinctParticipantTeam);
        }
    }

    @Test
    void 최근_20게임의_팀원들_조회() {
        List<DistinctParticipantTeam> top20ByPuuidOrderByMatchId = participantRepository.findTop20ByPuuidAndQueueIdOrderByMatchIdDesc(
            puuid1, 420);

        List<Participant> byDistinctParticipantTeamsExceptMe = participantRepository.findByDistinctParticipantTeamsExceptMe(
            top20ByPuuidOrderByMatchId, puuid1);
        for (Participant participant : byDistinctParticipantTeamsExceptMe) {
            System.out.printf("%s | %s | %d | %s \n", participant.getPuuid(), participant.getMatchId(),
                participant.getTeamId(), participant.getWin());
        }

        System.out.println(byDistinctParticipantTeamsExceptMe.size());

    }


    @Test
    public void getParticipantsInMatch() {
        List<Participant> results = participantRepository.findParticipantsByMatchId(matchId);

        for (Participant result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void getMost10Plays() {
        List<ChampionPlayDto> topChampionStatsByPuuid = participantRepository.findTopChampionStatsByPuuid(testPuuid1,
            10);

        for (ChampionPlayDto championPlaysDto : topChampionStatsByPuuid) {
            System.out.println(championPlaysDto);
        }
    }

    @Test
    public void testGetTop3ChampionsByPuuids() {

        List<MostChampionsDto> topChampionsForEachSummoner =
            participantRepository.findTopChampionsForEachSummoner(List.of(testPuuid1, testPuuid2), 3);

        for (MostChampionsDto mostChampionsBySummonerDto : topChampionsForEachSummoner) {
            for (Integer championId : mostChampionsBySummonerDto.getChampionIds()) {
                System.out.println("championId = " + championId);
            }
        }


    }

    @Test
    public void testGetChampionPlaysDtoBySummonerIdAndChampionId() {
        List<ChampionPlayDto> championPlayInfoByChampionIdAndSummonerId =
            participantRepository.findChampionPlayInfoByPuuidAndChampionId(
                "p3sOFe72XJw93VP7ylLhObH1IGf7wXEedp_0MT8iHQtaRZcoNHt9hKJuR0vYMZIrK0Ct8SujMWk-gQ", 266L);

        System.out.println(championPlayInfoByChampionIdAndSummonerId);
    }

    @Test
    void DTO로_조회_테스트() {
        List<ParticipantBriefDto> results = participantRepository.findBriefDtoByMatchId(matchId);

        for (ParticipantBriefDto result : results) {

            System.out.println("result = " + result);

        }
    }

}