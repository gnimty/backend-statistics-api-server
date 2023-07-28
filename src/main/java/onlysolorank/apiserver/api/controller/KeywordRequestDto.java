package onlysolorank.apiserver.api.controller;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

public class KeywordRequestDto {

    @NotBlank(message = "internalName 기준으로 1글자 이상 입력해야 합니다.")
    private String keyword;

    public KeywordRequestDto(String keyword){
        this.keyword = keywordToInternalName(keyword);
    }

    public String getInternalName(){
        return this.keyword;
    }

    private String keywordToInternalName(String keyword){
        // 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }
}
