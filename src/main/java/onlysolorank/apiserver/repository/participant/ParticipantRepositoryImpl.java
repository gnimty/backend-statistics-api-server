package onlysolorank.apiserver.repository.participant;

import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.Participant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static onlysolorank.apiserver.repository.participant.ParticipantRepository.*;

/**
 * packageName    : onlysolorank.apiserver.repository.participant
 * fileName       : ParticipantRepositoryImpl
 * author         : solmin
 * date           : 2023/09/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/11        solmin       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class ParticipantRepositoryImpl implements ParticipantRepositoryCustom{
    @Qualifier("mongoTemplate")
    private final MongoTemplate mongoTemplate;
    @Override
    public List<Participant> findByDistinctParticipantTeamsExceptMe(List<DistinctParticipantTeam> teams, String puuid) {
        // matchId, team, win 정보가 넘어옴
        // 자신이 아니고 teams 조건에 맞는 팀원들 정보를 받아오기

        Criteria cond1 =  new Criteria().orOperator(teams.stream()
            .map(team -> Criteria.where("matchId").is(team.getMatchId())
                .and("teamId").is(team.getTeamId()))
            .collect(Collectors.toList()));

        Criteria cond2 = Criteria.where("puuid").ne(puuid);

        Query query = new Query().addCriteria(new Criteria().andOperator(cond1, cond2));

        return mongoTemplate.find(query, Participant.class);
    }
}
