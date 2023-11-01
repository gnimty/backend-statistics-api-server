package onlysolorank.apiserver.repository.summoner_play;

import java.util.List;
import onlysolorank.apiserver.domain.SummonerPlay;
import org.springframework.data.mongodb.repository.MongoRepository;

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
public interface SummonerPlayRepository extends MongoRepository<SummonerPlay, String>,
    SummonerPlayRepositoryCustom {

    List<SummonerPlay> findSummonerPlaysByPuuid(String puuid);

    List<SummonerPlay> findTop100SummonerPlaysByChampionNameAndTotalPlaysGreaterThanEqualOrderByTotalPlaysDesc(
        String championName, int totalPlays);
}
