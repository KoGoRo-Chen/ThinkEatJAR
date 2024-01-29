package ThinkEat.mvc;

import ThinkEat.mvc.model.entity.EatRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:C:/ThinkEat/image/");

    }
            /*
            學校電腦圖片資料夾路徑: "file:C:/Users/NTPU/Desktop/IdeaProjects/ThinkEatJAR/img/"
            筆電圖片資料夾路徑: "file:C:/Users/marge/OneDrive/Desktop/MyClassDemo/ThinkEatJAR/img/"
            PC圖片資料夾路徑: "file:C:/Users/kogor/Desktop/MyJavaProject/IntelliJ/ThinkEatJAR/img/";
             */


}