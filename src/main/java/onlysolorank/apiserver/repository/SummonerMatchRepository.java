package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.SummonerMatch;
import org.springframework.data.mongodb.repository.MongoRepository;

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
