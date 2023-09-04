package onlysolorank.apiserver.repository.summoner_play;

import onlysolorank.apiserver.domain.SummonerPlay;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner_play
 * fileName       : SummonerPlayRepository
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */
public interface SummonerPlayRepository extends MongoRepository<SummonerPlay, String>, SummonerPlayRepositoryCustom {
    List<SummonerPlay> findSummonerPlaysByPuuid(String puuid);

    List<SummonerPlay> findTop100SummonerPlaysByChampionNameAndTotalPlaysGreaterThanEqualOrderByTotalPlaysDesc(String championName, int totalPlays);
}
