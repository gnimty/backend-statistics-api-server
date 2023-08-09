package onlysolorank.apiserver.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
 * 2023/07/31        solmin       Converter -> 단순 구조로 변경
 * 2023/08/09        solmin       Double 반올림 내부함수 추가
 */


public class CustomConverter {
    public static String keywordToInternalName(String keyword){
        // 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }

    public static Double doubleValueToHalfUp(Double value, int scale){
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
