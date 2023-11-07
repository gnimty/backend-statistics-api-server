package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.SkinSale;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : SkinSaleRes
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */

@Data
@Builder
public class SkinSaleDto {
    private String skinName;
    private Integer originRp;
    private Integer discountedRp;
    private String skinImgUrl;
    private Double discountedRate;

    public static SkinSaleDto fromSkinSale(SkinSale skinSale) {
        return SkinSaleDto.builder()
                .skinName(skinSale.getKrName())
                .originRp(skinSale.getOriginRp())
                .discountedRp(skinSale.getDiscountedRp())
                .skinImgUrl(skinSale.getUrl())
                .discountedRate(divideAndReturnDouble(skinSale.getDiscountedRp(), skinSale.getOriginRp(), 2).get())
                .build();
    }
}
