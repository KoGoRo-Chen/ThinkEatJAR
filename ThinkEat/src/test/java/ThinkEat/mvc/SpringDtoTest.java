package ThinkEat.mvc;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringDtoTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    EatRepoService eatRepoService;

    //@Test
    void createRestaurant() {
        RestaurantDto restaurantDto1 = new RestaurantDto();
        restaurantDto1.setName("頂呱呱");
        restaurantDto1.setAddress("羅斯福路");
        restaurantService.addRestaurant(restaurantDto1);
        System.out.println(restaurantDto1);

    }

    @Test
    void UpDateEatRepo() {
        EatRepoDto eatRepoDto1 = new EatRepoDto();
        eatRepoDto1.setTitle("好吃");
        eatRepoDto1.setArticle("真的很好吃");
        Integer eatRepoDto1Id = eatRepoService.addEatRepo(eatRepoDto1);
        eatRepoDto1.setId(eatRepoDto1Id);
        System.out.println("eatRepoDto1: " + eatRepoDto1);

    }

}