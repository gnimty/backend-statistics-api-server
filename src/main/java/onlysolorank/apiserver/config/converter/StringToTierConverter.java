package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.api.controller.dto.Period;
import onlysolorank.apiserver.domain.dto.Tier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.api.converter
 * fileName       : StringToTierConverter
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@ReadingConverter
public class StringToTierConverter implements Converter<String, Tier> {
    @Override
    public Tier convert(String source) {
        try{
            return Tier.valueOf(source.toLowerCase());
        }catch(IllegalArgumentException e){
            if (source.equals("MASTER+")){
                return Tier.master;
            }
            else{
                return null;
            }
        }
    }
}
