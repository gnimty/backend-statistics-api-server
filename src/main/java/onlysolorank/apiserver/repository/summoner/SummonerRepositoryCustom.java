package onlysolorank.apiserver.repository.summoner;

import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner
 * fileName       : SummonerRepositoryCustom
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 * 2023/09/04        solmin       Custom Repository 분리
 */


public interface SummonerRepositoryCustom {
    @Query(
        value = "{mmr:{'$gte': ?0}}",
        fields = "{history:0, revisionDate: 0, updatedAt: 0, }")
    List<Summoner> findSummonersByMmrGreaterThanEqual(Integer mmr);
}
