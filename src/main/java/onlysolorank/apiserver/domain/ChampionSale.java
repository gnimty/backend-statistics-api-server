package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : ChampionSale
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */

@Document(collection = "champion_sales_info")
@Getter
@AllArgsConstructor
public class ChampionSale {

    @Id
    private String id;

    private Long championId;

    @Field("name_kr")
    private String krName;

    @Field("name_en")
    private String enName;

    @Field("origin_price")
    private Integer originRp;

    @Field("price")
    private Integer discountedRp;

    @Field("ip_price")
    private Integer originIp;
}
