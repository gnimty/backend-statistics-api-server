package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.SummonerDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : AutoCompleteRes
 * author         : solmin
 * date           : 10/11/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/11/23        solmin       최초 생성
 */

@Data
public class AutoCompleteRes {

    private String keyword;
    private List<SummonerDto> summoners;
    private Integer matched;

    @Builder
    public AutoCompleteRes(String keyword, List<SummonerDto> summoners) {
        this.keyword = keyword;
        this.summoners = summoners;
        this.matched = summoners.size();
    }

}
