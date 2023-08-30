package onlysolorank.apiserver.repository;

import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

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
 * 2023/07/24        solmin       findTop5ByInternalNameStartsWithOrderByInternalName 추가
 * 2023/07/31        solmin       findSummonersByPuuidInCustom 추가

 */

public interface SummonerRepository extends MongoRepository<Summoner, String> {
    /** 소환사의 internalName과 패턴이 일치하는 (키워드로 시작하는) 소환사 정보를 사전 순으로 최대 5명까지 조회
     * */
    List<Summoner> findTop5ByInternalNameStartsWithOrderByInternalName(String internalName);

    Optional<Summoner> findOneByInternalName(String internalName);
    Optional<Summoner> findOneByPuuid(String puuid);

    Boolean existsByPuuid(String puuid);
    Boolean existsByInternalName(String internalName);

    List<Summoner> findSummonersByPuuidIn(List<String> puuids);

    List<Summoner> findSummonersBySummonerIdIn(List<String> puuids);

    List<Summoner> findSummonersByMmrGreaterThanEqual(Integer mmr);
}
