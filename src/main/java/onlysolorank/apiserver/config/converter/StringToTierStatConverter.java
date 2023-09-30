package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.domain.statistics.tier.ChampionTier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.api.converter
 * fileName       : StringToTierStatConverter
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Component
@ReadingConverter
public class StringToTierStatConverter implements Converter<String, ChampionTier> {
    @Override
    public ChampionTier convert(String source) {
        try {
            switch (source){
                case "OP":
                    return ChampionTier.OP;
                case "1 tier":
                    return ChampionTier.TIER_1;
                case "2 tier":
                    return ChampionTier.TIER_2;
                case "3 tier":
                    return ChampionTier.TIER_3;
                case "4 tier":
                    return ChampionTier.TIER_4;
                case "5 tier":
                    return ChampionTier.TIER_5;
            }
            throw new IllegalArgumentException();
        }catch (IllegalArgumentException e){
            return ChampionTier.UNKNOWN;
        }
    }
}
