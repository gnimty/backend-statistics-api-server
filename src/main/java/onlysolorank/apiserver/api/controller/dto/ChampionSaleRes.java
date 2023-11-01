package onlysolorank.apiserver.api.controller.dto;

import lombok.Data;
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
@Data
public class ChampionSaleRes {

    private Long championId;
    private String krName;
    private String enName;
    private Integer originRp;
    private Integer discountedRp;
    private Integer originIp;

    public ChampionSaleRes(ChampionSale championSale) {
        this.championId = championSale.getChampionId();
        this.krName = championSale.getKrName();
        this.enName = championSale.getEnName();
        this.originRp = championSale.getOriginRp();
        this.discountedRp = championSale.getDiscountedRp();
        this.originIp = championSale.getOriginIp();
    }
}
