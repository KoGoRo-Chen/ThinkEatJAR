package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.AuthorityDao;
import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.dto.AuthorityDto;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final UserServiceDao userServiceDao;
    private final AuthorityService authorityService;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final FavListService favListService;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao,
                       UserServiceDao userServiceDao,
                       AuthorityService authorityService,
                       RestaurantService restaurantService,
                       EatRepoService eatRepoService,
                       FavListService favListService,
                       ModelMapper modelMapper,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userServiceDao = userServiceDao;
        this.authorityService = authorityService;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.favListService = favListService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceDao.findByUserName(username);
        if (user == null) {
            // 返回一個空的UserDto或採取其他適當的處理方式
            return null;
        }
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
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
    public Integer addUser(UserDto userDto) {
        FavListDto favListDto = new FavListDto();
        favListDto.setName("我的第一個清單");
        Integer favListId = favListService.addFavList(favListDto);
        userDto.getFavLists().add(favListDto);
        userDto.setFavListCount(1);

        userDto.setUsername(userDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setNickname(userDto.getNickname());
        AuthorityDto authorityDto = authorityService.getAuthorityById(1);
        userDto.getAuthorities().add(authorityDto);
        userDto.setEnabled(true);

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
            userToUpdate.setNickname(newNickName);
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
            if (userToUpdate.getPassword().equals(curPassword)) {
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
