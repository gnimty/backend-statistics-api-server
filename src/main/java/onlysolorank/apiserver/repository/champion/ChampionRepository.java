package onlysolorank.apiserver.repository.champion;

import onlysolorank.apiserver.domain.Champion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.repository.champion
 * fileName       : ChampionRepository
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
public interface ChampionRepository extends MongoRepository<Champion,String> {

    Optional<Champion> findOneByChampionId(String championId);
    List<Champion> findAll();
}
