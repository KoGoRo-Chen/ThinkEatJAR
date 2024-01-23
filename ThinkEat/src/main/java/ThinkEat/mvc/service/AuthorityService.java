package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.AuthorityDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.dto.AuthorityDto;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.Authority;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityService {

    private final UserDao userDao;
    private final UserServiceDao userServiceDao;
    private final AuthorityDao authorityDao;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final FavListService favListService;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorityService(UserDao userDao,
                            UserServiceDao userServiceDao,
                            AuthorityDao authorityDao,
                            RestaurantService restaurantService,
                            EatRepoService eatRepoService,
                            FavListService favListService,
                            ModelMapper modelMapper) {
        this.userDao = userDao;
        this.userServiceDao = userServiceDao;
        this.authorityDao = authorityDao;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.favListService = favListService;
        this.modelMapper = modelMapper;
    }

    //以ID尋找會員
    public AuthorityDto getAuthorityById(Integer authId) {
        Optional<Authority> authorityOpt = authorityDao.findById(authId);
        //確認會員是否存在
        if (authorityOpt.isPresent()) {
            Authority authority = authorityOpt.get();
            System.out.println(authority);
            AuthorityDto authorityDto = modelMapper.map(authority, AuthorityDto.class);
            return authorityDto;
        }
        return null;
    }

    //尋找所有清單
    public List<AuthorityDto> findAllAuthority() {
        List<Authority> authorityList = authorityDao.findAll();
        return authorityList.stream()
                .map(authority -> modelMapper.map(authority, AuthorityDto.class))
                .toList();
    }


}
