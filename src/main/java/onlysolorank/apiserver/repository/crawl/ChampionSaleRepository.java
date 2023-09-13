package onlysolorank.apiserver.repository.crawl;

import onlysolorank.apiserver.domain.ChampionSale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.repository.crawl
 * fileName       : ChampionSaleRepository
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
public interface ChampionSaleRepository extends MongoRepository<ChampionSale,String> {
    List<ChampionSale> findAll();
}
