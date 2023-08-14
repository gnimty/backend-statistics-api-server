package onlysolorank.apiserver.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.utils
 * fileName       : NameToInternalNameConverter
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 */

@Component
public class NameToInternalNameConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        return keywordToInternalName(source);
    }

    private String keywordToInternalName(String keyword){
        // 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }
}
