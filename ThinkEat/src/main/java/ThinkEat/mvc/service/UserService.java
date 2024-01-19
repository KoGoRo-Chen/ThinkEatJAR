package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao userDao;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final FavListService favListService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserDao userDao, RestaurantService restaurantService, EatRepoService eatRepoService, FavListService favListService, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.favListService = favListService;
        this.modelMapper = modelMapper;
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
