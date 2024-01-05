package onlysolorank.apiserver.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.config.converter
 * fileName       : StringToBooleanConverter
 * author         : solmin
 * date           : 11/1/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/1/23        solmin       최초 생성
 */

@Component
@ReadingConverter
public class LongToStringConverter implements Converter<Long, String> {

    @Override
    public String convert(Long source) {
        try {
            return String.valueOf(source);
        } catch (IllegalArgumentException e) {
            return "0";
        }
    }
}