package onlysolorank.apiserver.api.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static onlysolorank.apiserver.utils.CustomConverter.keywordToInternalName;

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


@Getter
public class KeywordReq {

    @NotBlank(message = "keyword는 internalName 기준으로 1글자 이상 입력해야 합니다.")
    private String keyword;

    public KeywordReq(String keyword){
        this.keyword = keywordToInternalName(keyword);
    }

}
