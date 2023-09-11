package onlysolorank.apiserver.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * packageName    : onlysolorank.apiserver.utils
 * fileName       : CustomFunctions
 * author         : solmin
 * date           : 2023/07/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/24        solmin       최초 생성
 * 2023/07/31        solmin       Converter -> 단순 구조로 변경
 * 2023/08/09        solmin       Double 반올림 내부함수 추가
 * 2023/09/04        solmin       클래스 이름 변경
 * 2023/09/11        solmin       divideAndReturnDouble 추가
 */


public class CustomFunctions {
    /**
     * 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
     *
     * @param keyword the keyword
     * @return the string
     */
    public static String keywordToInternalName(String keyword){
        if(keyword==null){
            return null;
        }

        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }

    /**
     * 반올림할 Double 값과 반올림할 자릿수를 넘겨 주어 그 값을 받음
     *
     * @param value the value
     * @param scale the scale: scale번째 자리수에서 반올림
     * @return the double
     */
    public static Double doubleValueToHalfUp(Double value, int scale){
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static Optional<Double> divideAndReturnDouble(Integer a, Integer b, int scale){
        if(b==0){
            return null;
        }
        return Optional.of(doubleValueToHalfUp(a.doubleValue() / b.doubleValue(), scale));
    }

}
