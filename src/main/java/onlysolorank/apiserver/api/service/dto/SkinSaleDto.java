package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import onlysolorank.apiserver.domain.SkinSale;

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

@SuperBuilder
@Getter
public class SkinSaleDto {

    @Schema(example = "나무정령 오공", description = "스킨 이름")
    private String skinName;
    @Schema(example = "1350", description = "기존 RP 가격")
    private Integer originRp;
    @Schema(example = "975", description = "할인된 RP 가격")
    private Integer discountedRp;
    @Schema(example = "1350", description = "스킨 이미지 url")
    private String skinImgUrl;
    @Schema(example = "0.72", description = "스킨 할인율")
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
