package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.api.controller.dto.TierFilter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.config
 * fileName       : StringToTierFilterConverter
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Component
@WritingConverter
public class TierFilterToStringConverter implements Converter<TierFilter, String> {
    @Override
    public String convert(TierFilter source) {
        if (source==TierFilter.MASTER){
            return "MASTER+";
        }else{
            return source.name();
        }
    }
}
