package onlysolorank.apiserver.domain.statistics.analysis.component;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.dto.component
 * fileName       : SpellComponentStat
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@NoArgsConstructor
@Getter
public class SpellComponentStat extends BaseComponentStat {

    @Field("summoner1Id")
    private Long spellD;

    @Field("summoner2Id")
    private Long spellF;
}
