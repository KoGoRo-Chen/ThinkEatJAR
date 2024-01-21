package ThinkEat.mvc.model.pagination;

import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.FavList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FavListPagination extends PagingAndSortingRepository<FavListDto, Integer> {
    List<FavListDto> findAllFavList(Pageable pageable);
}
