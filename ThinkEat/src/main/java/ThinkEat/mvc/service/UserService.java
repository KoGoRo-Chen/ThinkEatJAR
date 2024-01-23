package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.AuthorityDao;
import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final UserServiceDao userServiceDao;
    private final AuthorityDao authorityDao;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final FavListService favListService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserDao userDao,
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

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userServiceDao.findByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("找不到使用者，使用者名稱：" + user.getUsername());
        }

        return new ThinkEat.mvc.model.entity.UserDetails(user);
    }


    //新增會員
    @Transactional
    public Integer addUser(UserDto userDto,
                           FavListDto favListDto) {
        favListDto.setName("我的清單");
        Integer favListId = favListService.addFavList(favListDto);
        userDto.getFavLists().add(favListDto);
        User user = modelMapper.map(userDto, User.class);
        userDao.save(user);
        return user.getId();
    }

    //更新會員暱稱
    @Transactional
    public Integer updateUserNickNameById(Integer userId, String newNickName) {
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isPresent()) {
            User userToUpdate = userOpt.get();
            // 更新會員暱稱
            userToUpdate.setNickName(newNickName);
            userDao.save(userToUpdate);
            return userToUpdate.getId();
        }
        return null;
    }

    //更新會員密碼
    @Transactional
    public Integer updateUserPasswordById(Integer userId,
                                          String curPassword,
                                          String newPassword) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User userToUpdate = userOpt.get();
            //驗證會員密碼
            if (userToUpdate.getPassword() == curPassword) {
                userToUpdate.setPassword(newPassword);
                userDao.save(userToUpdate);
                return userToUpdate.getId();
            }//驗證結束
        }
        return null;
    }

    //以ID刪除會員
    @Transactional
    public void deleteUserById(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User userToDelete = userOpt.get();
            userDao.delete(userToDelete);
        }
    }

    //以ID尋找會員
    public UserDto getUserById(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        }
        return null;
    }

    //尋找所有清單
    public List<UserDto> findAllUser() {
        List<User> userList = userDao.findAll();
        return userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    //以UserId尋找清單
    public List<FavListDto> findAllFavListByUserId(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto.getFavLists();
        }
        return null;
    }


}
