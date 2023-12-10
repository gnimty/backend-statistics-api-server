package onlysolorank.apiserver.api.service.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : SummonerNameDto
 * author         : solmin
 * date           : 12/9/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/9/23        solmin       최초 생성
 */

@Data
public class SummonerNameDto {
    private String puuid;
    private String name;
    private String tagLine;
    @Field("internal_tagname")
    private String internalTagName;
}
