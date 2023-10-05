package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.api.converter
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
public class StringToPositionConverter implements Converter<String, PositionFilter> {
    @Override
    public PositionFilter convert(String source) {
        try{
            if (source==null){
                throw new IllegalArgumentException();
            }

            return PositionFilter.valueOf(source);
        }catch (IllegalArgumentException e){
            return PositionFilter.UNKNOWN;
        }
    }
}
