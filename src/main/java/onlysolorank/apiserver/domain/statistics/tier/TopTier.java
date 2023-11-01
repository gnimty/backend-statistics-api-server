package onlysolorank.apiserver.domain.statistics.tier;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.tier
 * fileName       : TopStatistics
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Document("tier_top")
@AllArgsConstructor
@Getter
public class TopTier extends BaseChampionTier {

    @Field("TOP")
    private Long championId;


    @PostConstruct
    public void onConstruct() {
        super.championId = championId;
    }
}
