package onlysolorank.apiserver.repository.participant;

import onlysolorank.apiserver.domain.Participant;

import java.util.List;

import static onlysolorank.apiserver.repository.participant.ParticipantRepository.*;

/**
 * packageName    : onlysolorank.apiserver.repository.participant
 * fileName       : ParticipantRepositoryCustom
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 * 2023/09/11        solmin       추상메소드 이관
 */
public interface ParticipantRepositoryCustom {

    List<Participant> findByDistinctParticipantTeamsExceptMe(List<DistinctParticipantTeam> teams, String puuid);

}
