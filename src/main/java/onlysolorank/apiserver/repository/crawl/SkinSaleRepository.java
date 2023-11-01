package onlysolorank.apiserver.repository.crawl;

import java.util.List;
import onlysolorank.apiserver.domain.SkinSale;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * packageName    : onlysolorank.apiserver.repository.crawl
 * fileName       : SkinSaleRepository
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
public interface SkinSaleRepository extends MongoRepository<SkinSale, String> {

    List<SkinSale> findAll();
}
