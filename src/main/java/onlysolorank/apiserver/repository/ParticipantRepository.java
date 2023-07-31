package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.Team;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
 */
public interface ParticipantRepository extends MongoRepository<Participant, String> {
    List<Participant> findByMatchId(String matchId);

    @Query("{'matchId': {$in: ?0}}")
    List<Participant> findByMatchIdInCustom(List<String> matchIds);
}
