package onlysolorank.apiserver.api.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.ChampionPlaysDto;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;
import onlysolorank.apiserver.api.service.dto.mostChampionsBySummonerDto;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.repository.participant.ParticipantRepository;
import onlysolorank.apiserver.repository.participant.ParticipantRepository.DistinctParticipantTeam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * 2023/09/11        solmin       getDistinctTeamMembersExceptMe 추가
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
    private final ParticipantRepository participantRepository;


    public List<ChampionPlaysDto> getChampionStatus(String puuid, int limit){
        return participantRepository.findTopChampionStatsByPuuid(puuid, limit);
    }

    public List<Participant> getParticipantListByMatchId(List<String> matchIds){
        return participantRepository.findParticipantsByMatchIdIn(matchIds);
    }

    public Map<String, List<Integer>> getTopNChampionIdsByPuuids(List<String> puuids, int limit){
        List<mostChampionsBySummonerDto> topChampionsForEachSummoner = participantRepository.findTopChampionsForEachSummoner(puuids, limit);

        Map<String, List<Integer>> mostChampionMap = new HashMap<>();

        topChampionsForEachSummoner.forEach(t->{
            mostChampionMap.put(t.getPuuid(), t.getChampionIds());
        });

        return mostChampionMap;
    }


    public ChampionPlaysDto getChampionPlaysByPuuidAndChampionId(String puuid, Long championId){
        return participantRepository.findChampionPlayInfoByPuuidAndChampionId(puuid, championId).get(0);
    }


    public List<RecentMemberDto> getDistinctTeamMembersExceptMe(String puuid){
        List<DistinctParticipantTeam> top20ByPuuidOrderByMatchId = participantRepository.findTop20ByPuuidOrderByMatchId(puuid);

        Map<String, Map<Boolean, Long>> collect = participantRepository.findByDistinctParticipantTeamsExceptMe(top20ByPuuidOrderByMatchId, puuid)
            .stream()
            .collect(Collectors.groupingBy(
                Participant::getPuuid,
                Collectors.groupingBy(
                    Participant::getWin,
                    Collectors.counting()
                )
            ));
        List<RecentMemberDto> result = collect.entrySet().stream().map(
            entry -> {
                String targetPuuid = entry.getKey();
                Map<Boolean, Long> value = entry.getValue();

                return RecentMemberDto.builder()
                    .puuid(targetPuuid)
                    .totalWin(value.getOrDefault(true, 0L).intValue())
                    .totalDefeat(value.getOrDefault(false, 0L).intValue())
                    .build();
            }).toList();


        return result;
    }

}
