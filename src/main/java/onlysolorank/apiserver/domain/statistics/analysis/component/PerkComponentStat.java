package onlysolorank.apiserver.domain.statistics.analysis.component;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain.dto.component
 * fileName       : PerkComponentStat
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
public class PerkComponentStat extends BaseComponentStat {

    @Schema(example = "8200", description = "주룬 스타일 ID (지배, 정밀, ...)")
    @Field("primaryStyle")
    private Long primaryStyleId;

    @Schema(example = "8229", description = "주룬 첫 번째 선택 ID (감전, 칼날비, ...)")
    @Field("mainStyle")
    private Long mainStyleId;

    @Schema(example = "8400", description = "보조룬 스타일 ID (지배, 정밀, ... )")
    @Field("subStyle")
    private Long subStyleId;

    @Schema(description = "주룬 세부 픽")
    private List<Long> primaryStyles;
    @Schema(description = "보조룬 세부 픽")
    private List<Long> subStyles;
    @Schema(description = "룬 파편 세부 픽")
    private List<Long> statPerks;


}
