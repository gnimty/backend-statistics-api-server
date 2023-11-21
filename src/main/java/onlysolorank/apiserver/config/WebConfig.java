package onlysolorank.apiserver.config;

import onlysolorank.apiserver.config.converter.StringToBooleanConverter;
import onlysolorank.apiserver.config.converter.StringToPeriodConverter;
import onlysolorank.apiserver.config.converter.StringToPositionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : onlysolorank.apiserver.config
 * fileName       : WebConfig
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToPeriodConverter());
        registry.addConverter(new StringToPositionConverter());
        registry.addConverter(new StringToBooleanConverter());
    }
}
