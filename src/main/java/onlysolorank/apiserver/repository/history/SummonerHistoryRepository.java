package onlysolorank.apiserver.repository.history;

import onlysolorank.apiserver.domain.SummonerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner_history
 * fileName       : SummonerHistoryRepository
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */
public interface SummonerHistoryRepository extends MongoRepository<SummonerHistory, String> {
    Optional<SummonerHistory> findByPuuid(String puuid);
}
