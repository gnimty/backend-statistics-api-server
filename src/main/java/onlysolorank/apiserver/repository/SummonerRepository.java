package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository
 * fileName       : SummonerRepository
 * author         : solmin
 * date           : 2023/07/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/07        solmin       최초 생성
 */

public interface SummonerRepository extends MongoRepository<Summoner, String> {

    List<Summoner> findSummonersByNameLike(String name);
}
