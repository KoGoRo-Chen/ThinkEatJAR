package ThinkEat.mvc;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.entity.EatRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class MyConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<EatRepoDto, EatRepo>() {
            protected void configure() {
                map().getPrice().setId(source.getPriceId());
            }
        });
        return modelMapper;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }
}