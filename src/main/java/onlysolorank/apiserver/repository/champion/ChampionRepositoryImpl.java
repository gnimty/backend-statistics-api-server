package onlysolorank.apiserver.repository.champion;

import java.util.List;
import onlysolorank.apiserver.domain.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * packageName    : onlysolorank.apiserver.repository.champion
 * fileName       : ChampionRepositoryImpl
 * author         : solmin
 * date           : 10/9/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/9/23        solmin       최초 생성
 */
public class ChampionRepositoryImpl implements ChampionRepositoryCustom {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public List<Rotation> findRotationChampions() {

        List<Rotation> result = mongoTemplate.findAll(Rotation.class);

        return result;
    }
}
