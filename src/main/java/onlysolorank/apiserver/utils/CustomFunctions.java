package onlysolorank.apiserver.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
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
 * 2023/10/11        solmin       internalName 조건으로 특수문자 й 추가
 */


public class CustomFunctions {

    /**
     * 영어, 한글, 숫자를 제외한 모든 문자 및 공백 제거 후 소문자로 변환
     *
     * @param keyword the keyword
     * @return the string
     */
    public static String keywordToInternalName(String keyword) {
        if (keyword == null) {
            return null;
        }

        return keyword
            .replaceAll("[^a-zA-Z가-힣0-9й]", "")
            .replaceAll("\\s", "")
            .toLowerCase();
    }

    public static String keywordToInternalTagName(String keyword, Boolean byPathVariable) {
        // 1.'-'을 기준으로 internalName, tagLine으로 분리
        String[] split;

        if(byPathVariable){
            split = keyword.split("-");
        }else{
            split = keyword.split("#");
        }

        split[0] = keywordToInternalName(split[0]);

        if (split.length != 2) { // 1-1. 만약 tagLine이 존재하지 않으면 keywordToInternalName 리턴
            return split[0];
        } else { // 1-2. tagLine이 존재한다면 strip() 처리 후 internalName에 붙이기
            split[1] = split[1].replaceAll("^\\s+", "").toLowerCase();
            return String.join("#",split);
        }
    }

    public static String[] splitInternalName(String internalName){
        return internalName.split("#");
    }

    /**
     * 반올림할 Double 값과 반올림할 자릿수를 넘겨 주어 그 값을 받음
     *
     * @param value the value
     * @param scale the scale: scale번째 자리수에서 반올림
     * @return the double
     */
    public static Double doubleValueToHalfUp(Double value, int scale) {
        if (Objects.isNull(value)){
            return null;
        }
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static Optional<Double> divideAndReturnDouble(Integer a, Integer b, int scale) {
        if (b == 0) {
            return Optional.of(0.0);
        }
        return Optional.of(doubleValueToHalfUp(a.doubleValue() / b.doubleValue(), scale));
    }

}
