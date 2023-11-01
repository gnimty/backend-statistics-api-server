package onlysolorank.apiserver.domain.statistics.tier;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.tier
 * fileName       : JungleStatistics
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */
@Document("tier_jungle")
@AllArgsConstructor
@Getter
public class JungleTier extends BaseChampionTier {

    @Field("JUG")
    private Long championId;


    @PostConstruct
    public void onConstruct() {
        super.championId = championId;
    }
}
