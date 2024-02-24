package onlysolorank.apiserver.api.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onlysolorank.apiserver.api.exception.CustomException;
import onlysolorank.apiserver.api.exception.ErrorCode;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.summoner_play.BaseSummonerPlay;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlay;
import onlysolorank.apiserver.repository.season.SeasonRepository;
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
    private final SeasonRepository seasonRepository;

    private final Integer LIMIT_ON_MATCH_HISTORY = 10;

    public List<? extends BaseSummonerPlay> getSummonerPlaysByPuuid(String puuid, QueueType queueType, Boolean brief) {
        String currentSeason = getCurrentSeasonName();

        if (brief){
            return summonerPlayRepository.findAllByQueueType(puuid, currentSeason, queueType, LIMIT_ON_MATCH_HISTORY);
        }else{
            return summonerPlayRepository.findAllByQueueType(puuid, currentSeason, queueType);
        }
    }

    public List<? extends BaseSummonerPlay> getSummonerPlaysByPairs(Map<String, Long> pairs, QueueType queueType) {
        String currentSeason = getCurrentSeasonName();
        return summonerPlayRepository.findByPuuidChampionIdPairs(pairs, queueType, currentSeason);
    }

    private String getCurrentSeasonName() {
        return seasonRepository.findTop1SeasonByOrderByStartAtDesc().orElseThrow(
            ()-> new CustomException(ErrorCode.RESULT_NOT_FOUND, "시즌 정보가 존재하지 않습니다.")
        ).getSeasonName();
    }

    public List<SummonerPlay> getSpecialists(Long championId){
        return summonerPlayRepository.findSpecialists(championId);
    }
}
