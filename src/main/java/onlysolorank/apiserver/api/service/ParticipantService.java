package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.ChampionPlaysBriefDto;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : ParticipantService
 * author         : solmin
 * date           : 2023/08/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/14        solmin       최초 생성
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public List<ChampionPlaysBriefDto> getChampionStatus(String puuid, int limit){
        return participantRepository.findTopChampionStatsByPuuid(puuid, limit);
    }

    public List<Participant> getParticipantListByMatchId(List<String> matchIds){
        return participantRepository.findByMatchIdInCustom(matchIds);
    }

}
