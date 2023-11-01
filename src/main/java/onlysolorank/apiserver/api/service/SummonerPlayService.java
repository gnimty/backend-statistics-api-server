package onlysolorank.apiserver.api.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.service.dto.PuuidChampionIdPair;
import onlysolorank.apiserver.domain.SummonerPlay;
import onlysolorank.apiserver.repository.summoner_play.SummonerPlayRepository;
import org.springframework.stereotype.Service;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerPlayService
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class SummonerPlayService {

    private final SummonerPlayRepository summonerPlayRepository;

    public List<SummonerPlay> getSummonerPlaysByPuuid(String puuid) {
        return summonerPlayRepository.findSummonerPlaysByPuuid(puuid);
    }

    public List<SummonerPlay> getSummonerPlaysLimit(String puuid, int limit) {
        return summonerPlayRepository.findSummonerPlaysByPuuid(puuid);
    }

    public List<SummonerPlay> getSummonerPlaysByCondition(String championName, Integer limit) {
        return summonerPlayRepository.findSpecialists(championName, limit);
    }

    public List<SummonerPlay> getSummonerPlaysByPairs(List<PuuidChampionIdPair> pairs) {
        return summonerPlayRepository.findByPuuidChampionIdPairs(pairs);
    }

    // 우선 놔두고 다음에 없애기: Summoner collection에 요약 정보가 들어감에 따라 필요 없어짐
    public Map<String, List<Long>> findMostPlayedChampionsByPuuidsAndLimit(List<String> puuids,
        int limit) {
        return summonerPlayRepository.findMostPlayedChampionsByPuuidsAndLimit(puuids, limit);
    }


}
