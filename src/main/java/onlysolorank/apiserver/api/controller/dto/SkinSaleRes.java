package onlysolorank.apiserver.api.controller.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import lombok.Data;
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

@Data
public class SkinSaleRes {

    private String skinName;
    private Integer originRp;
    private Integer discountedRp;
    private String skinImgUrl;
    private Double discountedRate;

    public SkinSaleRes(SkinSale skinSale) {
        this.skinName = skinSale.getKrName();
        this.originRp = skinSale.getOriginRp();
        this.discountedRp = skinSale.getDiscountedRp();
        this.skinImgUrl = skinSale.getUrl();
        this.discountedRate = divideAndReturnDouble(discountedRp, originRp, 2).get();
    }
}
