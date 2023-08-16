package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.ChampionPlaysBriefDto;
import onlysolorank.apiserver.api.service.dto.mostChampionsBySummonerDto;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 2023/08/16        getTopNChampionIdsByPuuids 메소드 추가
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

    public Map<String, List<Integer>> getTopNChampionIdsByPuuids(List<String> puuids, int limit){
        List<mostChampionsBySummonerDto> topChampionsForEachSummoner = participantRepository.findTopChampionsForEachSummoner(puuids, limit);

        Map<String, List<Integer>> mostChampionMap = new HashMap<>();

        topChampionsForEachSummoner.forEach(t->{
            mostChampionMap.put(t.getPuuid(), t.getChampionIds());
        });

        return mostChampionMap;
    }
}
