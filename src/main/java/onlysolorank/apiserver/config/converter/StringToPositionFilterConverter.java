package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.config.converter
 * fileName       : StringToPositionConverter
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Component
@ReadingConverter
public class StringToPositionFilterConverter implements Converter<String, PositionFilter> {
    @Override
    public PositionFilter convert(String source) {
        try{
            return PositionFilter.valueOf(source);
        }catch (IllegalArgumentException e){
            return PositionFilter.ALL;
        }
    }
}
