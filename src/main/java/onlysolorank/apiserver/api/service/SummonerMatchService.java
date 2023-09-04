package onlysolorank.apiserver.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.SummonerMatch;
import onlysolorank.apiserver.repository.summoner_match.SummonerMatchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.api.service
 * fileName       : SummonerMatchService
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
public class SummonerMatchService {
    private final SummonerMatchRepository summonerMatchRepository;

    public Optional<SummonerMatch> getSummonerMatchBySummonerPuuid(String puuid){
        return summonerMatchRepository.findSummonerMatchByPuuid(puuid);
    }

}
