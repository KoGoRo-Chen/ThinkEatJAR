package ThinkEat.mvc;

import ThinkEat.mvc.dao.*;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.service.RestaurantService;
import ThinkEat.mvc.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpringDtoTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    void createRestaurant() {
        RestaurantDto restaurantDto1 = new RestaurantDto();
        restaurantDto1.setName("頂呱呱");
        restaurantDto1.setAddress("羅斯福路");
        restaurantService.addRestaurant(restaurantDto1);
        System.out.println(restaurantDto1);

    }

}