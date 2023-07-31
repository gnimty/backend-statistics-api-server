package onlysolorank.apiserver.api.controller;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static onlysolorank.apiserver.utils.NameToInternalNameConverter.keywordToInternalName;

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
public class KeywordRequestDto {

    @NotBlank(message = "internalName 기준으로 1글자 이상 입력해야 합니다.")
    private String keyword;

    public KeywordRequestDto(String keyword){
        this.keyword = keywordToInternalName(keyword);
    }

    public String getInternalName(){
        return this.keyword;
    }

}
