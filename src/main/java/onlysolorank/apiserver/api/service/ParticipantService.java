package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.ChampionPlaysDto;
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
 * 2023/08/16        solmin       getTopNChampionIdsByPuuids 메소드 추가
 * 2023/08/28        solmin       챔피언 장인랭킹 추가 (성능 너무 안나옴)
 * 2023/08/30        solmin       소환사 인게임 정보 조회 추가 (성능 매우 안나옴)
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private static final int SPECIALIST_CNT_LIMIT = 100;
    private static final int SPECIALIST_PLAYS_CNT_LIMIT = 50;

    public List<ChampionPlaysDto> getChampionStatus(String puuid, int limit){
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

    public List<ChampionPlaysDto> getSpecialistsByCondition(List<String> puuids, String championName) {

        return participantRepository.findTopChampionStatsByChampionNameAndPuuids(puuids, championName, SPECIALIST_CNT_LIMIT).stream()
            .filter(s->s.getTotalPlays()>=SPECIALIST_PLAYS_CNT_LIMIT).toList();
    }

    public ChampionPlaysDto getChampionPlaysByPuuidAndChampionId(String puuid, Long championId){
        return participantRepository.findChampionPlayInfoByPuuidAndChampionId(puuid, championId).get(0);
    }

}
