package onlysolorank.apiserver.repository.participant;

import onlysolorank.apiserver.domain.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : ParticipantRepository
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 * 2023/08/09        solmin       findTop10ChampionStatsByPuuid 추가
 * 2023/08/10        solmin       totalDeath=0인 경우의 예외 처리
 * 2023/08/16        solmin       findTopChampionStatsByPuuid 추가
 * 2023/08/30        solmin       findChampionPlayInfoByPuuidAndChampionId 추가
 * 2023/09/04        solmin       Custom Repository 분리
 */
public interface ParticipantRepository extends MongoRepository<Participant, String>, ParticipantRepositoryCustom{
    List<Participant> findParticipantsByMatchId(String matchId);
    List<Participant> findParticipantsByMatchIdIn(List<String> matchIds);
}
