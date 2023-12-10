package onlysolorank.apiserver.repository.summoner;

import onlysolorank.apiserver.domain.Summoner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

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

    Page<Summoner> findSummonerPage(int page, int size, Sort sort);

}
