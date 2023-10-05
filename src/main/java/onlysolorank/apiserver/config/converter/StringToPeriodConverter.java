package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.api.controller.dto.Period;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.config.converter
 * fileName       : StringToPeriodConverter
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Component
public class StringToPeriodConverter implements Converter<String, Period> {
    @Override
    public Period convert(String source) {
        try{
            return Period.valueOf(source);
        }catch(IllegalArgumentException e){
            return Period.DAY;
        }
    }
}
