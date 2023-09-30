package onlysolorank.apiserver.domain.statistics.tier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.PostConstruct;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.tier
 * fileName       : UtilityStatistics
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Document("tier_utility")
@AllArgsConstructor
@Getter
public class UtilityTier extends BaseChampionTier {
    @Field("SUP")
    private Long championId;

    @PostConstruct
    public void onConstruct() {
        super.championId = this.championId;
    }
}