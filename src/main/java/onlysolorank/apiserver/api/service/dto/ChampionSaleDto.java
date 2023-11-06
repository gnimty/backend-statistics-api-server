package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.ChampionSale;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : ChampionSaleRes
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
public class ChampionSaleDto {
    private Long championId;
    private String krName;
    private String enName;
    private Integer originRp;
    private Integer discountedRp;
    private Integer originIp;
    private Double discountedRate;

    public static ChampionSaleDto from(ChampionSale championSale) {
        return ChampionSaleDto.builder()
                .championId(championSale.getChampionId())
                .krName(championSale.getKrName())
                .enName(championSale.getEnName())
                .originRp(championSale.getOriginRp())
                .discountedRp(championSale.getDiscountedRp())
                .originIp(championSale.getOriginIp())
                .discountedRate(divideAndReturnDouble(championSale.getDiscountedRp(), championSale.getOriginRp(), 2).get())
                .build();
    }
}
