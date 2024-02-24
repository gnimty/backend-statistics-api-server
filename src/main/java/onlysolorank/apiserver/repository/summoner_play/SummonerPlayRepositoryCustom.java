package onlysolorank.apiserver.repository.summoner_play;

import java.util.List;
import java.util.Map;
import onlysolorank.apiserver.domain.dto.QueueType;
import onlysolorank.apiserver.domain.summoner_play.BaseSummonerPlay;
import onlysolorank.apiserver.domain.summoner_play.SummonerPlay;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner_play
 * fileName       : SummonerPlayRepositoryCustom
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 * 2023/09/05        solmin       기존 쿼리메소드 대체
 */
public interface SummonerPlayRepositoryCustom {

    List<SummonerPlay> findSpecialists(Long championId);

    List<? extends BaseSummonerPlay> findByPuuidChampionIdPairs(Map<String, Long> pairs, QueueType queueType, String season);
    List<? extends BaseSummonerPlay> findAllByQueueType(String puuid, String season, QueueType queueType, Integer limit);
    List<? extends BaseSummonerPlay> findAllByQueueType(String puuid, String season, QueueType queueType);
}
