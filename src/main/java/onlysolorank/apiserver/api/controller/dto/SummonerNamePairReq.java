package onlysolorank.apiserver.api.controller.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalName;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : SummonerNamePairReq
 * author         : solmin
 * date           : 2023/08/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/23        solmin       최초 생성
 */

@Getter
public class SummonerNamePairReq {

    @NotBlank(message = "myName은 internalName 기준으로 1글자 이상 입력해야 합니다.")
    private final String myName;

    @NotBlank(message = "friendName은 internalName 기준으로 1글자 이상 입력해야 합니다.")
    private final String friendName;

    public SummonerNamePairReq(String myName, String friendName) {
        this.myName = keywordToInternalName(myName);
        this.friendName = keywordToInternalName(friendName);
    }
}
