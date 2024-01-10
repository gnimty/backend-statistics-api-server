package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import onlysolorank.apiserver.domain.ChampionSale;

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

@SuperBuilder
@Getter
public class ChampionSaleDto extends ChampionDto{

    @Schema(example = "975", description = "기존 RP 가격")
    private Integer originRp;
    @Schema(example = "585", description = "할인된 RP 가격")
    private Integer discountedRp;
    @Schema(example = "1350", description = "기존 IP 가격")
    private Integer originIp;
    @Schema(example = "0.55", description = "챔피언 할인율")
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
