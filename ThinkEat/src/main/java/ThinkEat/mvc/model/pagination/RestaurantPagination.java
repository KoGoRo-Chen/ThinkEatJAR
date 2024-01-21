package ThinkEat.mvc.model.pagination;

import ThinkEat.mvc.model.dto.RestaurantDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestaurantPagination extends PagingAndSortingRepository<RestaurantDto, Integer> {
    List<RestaurantDto> findAllRestaurant(Pageable pageable);
}
