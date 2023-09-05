package onlysolorank.apiserver.repository.summoner_play;

import onlysolorank.apiserver.api.service.dto.PuuidChampionIdPair;
import onlysolorank.apiserver.domain.SummonerPlay;

import java.util.List;
import java.util.Map;

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

    List<SummonerPlay> findSpecialists(String championName, int totalPlays);

    List<SummonerPlay> findByPuuidChampionIdPairs(List<PuuidChampionIdPair> pairs);

    List<SummonerPlay> findSummonerPlaysByPuuidAndLimit(String puuid, int limit);

    Map<String, List<Long>> findMostPlayedChampionsByPuuidsAndLimit(List<String> puuids, int limit);

}
