package onlysolorank.apiserver.api.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.domain.SummonerMatch;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.repository.summoner_match.SummonerMatchRepository;
import org.springframework.stereotype.Service;

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

    public Optional<SummonerMatch> getSummonerMatchBySummonerPuuid(String puuid) {
        return summonerMatchRepository.findSummonerMatchByPuuid(puuid);
    }

}
