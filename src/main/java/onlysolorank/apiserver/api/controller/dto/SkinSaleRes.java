package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.SkinSaleDto;

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
@AllArgsConstructor
@Builder
public class SkinSaleRes {

    private List<SkinSaleDto> skinSales;
}
