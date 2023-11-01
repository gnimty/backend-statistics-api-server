package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : SkinSale
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */

@Document(collection = "skin_sales_info")
@Getter
@AllArgsConstructor
public class SkinSale {

    @Id
    private String id;
    @Field("name_kr")
    private String krName;

    @Field("origin_price")
    private Integer originRp;

    @Field("price")
    private Integer discountedRp;

    private String url;
}
