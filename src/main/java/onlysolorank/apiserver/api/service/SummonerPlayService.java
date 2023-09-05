package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.SummonerPlay;
import onlysolorank.apiserver.repository.summoner_play.SummonerPlayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<SummonerPlay> getSummonerPlaysByPuuid(String puuid){
        return summonerPlayRepository.findSummonerPlaysByPuuid(puuid);
    }

    public List<SummonerPlay> getSummonerPlaysByCondition(String championName, Integer limit){
        return summonerPlayRepository.findSpecialists(championName, limit);
    }

}
