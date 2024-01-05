package onlysolorank.apiserver.repository.patch;

import java.util.List;
import java.util.Optional;
import onlysolorank.apiserver.domain.Champion;
import onlysolorank.apiserver.domain.statistics.analysis.ChampionPatch;
import onlysolorank.apiserver.repository.champion.ChampionRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

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
public interface ChampionPatchRepository extends MongoRepository<ChampionPatch, String>{
    List<ChampionPatch> findByVersionLessThanEqualAndAndChampionId(String version, String championId);
    List<ChampionPatch> findByChampionId(String championId);
    List<ChampionPatch> findAll();
}
