package onlysolorank.apiserver.repository.summoner;

import java.util.List;
import lombok.RequiredArgsConstructor;
import onlysolorank.apiserver.domain.Summoner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

/**
 * packageName    : onlysolorank.apiserver.repository.summoner
 * fileName       : SummonerRepositoryImpl
 * author         : solmin
 * date           : 12/10/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/10/23        solmin       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class SummonerRepositoryImpl implements SummonerRepositoryCustom{
    @Qualifier("primaryMongoTemplate")
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Summoner> findSummonerPage(int page, int size, Sort sort) {

        Pageable pageable = PageRequest.of(page, size, sort);

        Query query = new Query()
            .with(pageable)
            .skip(pageable.getPageSize() * pageable.getPageNumber())
            .limit(pageable.getPageSize());
        List<Summoner> summoners = mongoTemplate.find(query, Summoner.class);
        Page<Summoner> result = PageableExecutionUtils.getPage(
            summoners,
            pageable,
            () -> mongoTemplate.estimatedCount(Summoner.class));

        return result;
    }
}
