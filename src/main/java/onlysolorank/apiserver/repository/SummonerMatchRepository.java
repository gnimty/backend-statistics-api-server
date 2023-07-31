package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.api.dto.SummonerMatchDTO;
import onlysolorank.apiserver.domain.Match;
import onlysolorank.apiserver.domain.SummonerMatch;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : SummonerMatchRepository
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */
public interface SummonerMatchRepository extends MongoRepository<SummonerMatch, String> {

    Optional<SummonerMatch> findOneByPuuid(String puuid);

    SummonerMatch findByPuuid(String puuid);

}
