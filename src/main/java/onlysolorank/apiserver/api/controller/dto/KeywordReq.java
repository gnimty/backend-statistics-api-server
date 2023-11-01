package onlysolorank.apiserver.api.controller.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.keywordToInternalName;

import javax.validation.constraints.NotBlank;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : KeywordRequestDto
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 */


public class KeywordReq {

    @NotBlank(message = "keyword는 internalName 기준으로 1글자 이상 입력해야 합니다.")
    private final String keyword;

    public KeywordReq(String keyword) {
        this.keyword = keywordToInternalName(keyword);
    }

    public String getInternalName() {
        return this.keyword;
    }
}
