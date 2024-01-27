package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.AuthorityDao;
import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.Authority;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final UserServiceDao userServiceDao;
    private final AuthorityService authorityService;
    private final FavListService favListService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao,
                       UserServiceDao userServiceDao,
                       AuthorityService authorityService,
                       @Lazy FavListService favListService,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userServiceDao = userServiceDao;
        this.authorityService = authorityService;
        this.favListService = favListService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceDao.findByUserName(username);
        if (user == null) {
            // 返回一個空的UserDto或採取其他適當的處理方式
            return null;
        }
        return user;
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
    public Integer addUser(User user) {
        FavList favList = new FavList();
        favList.setName("我的第一個清單");
        favList.setFavList_User(user);
        Integer favListId = favListService.addFavList(favList);

        String password = passwordEncoder.encode(user.getRawPassword());
        user.setPassword(password);

        Date curDate = new Date();
        user.setDate(curDate);

        Authority authority = authorityService.getAuthorityById(1);
        user.setAuthority(authority);
        user.setEnabled(true);

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

    //更新會員密碼(會員用)
    @Transactional
    public void updateUserPasswordById(Integer userId,
                                       String curPassword,
                                       String newPassword) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User userToUpdate = userOpt.get();
            String encryptedPassword = passwordEncoder.encode(curPassword);
            //驗證會員密碼
            if (userToUpdate.getPassword().equals(encryptedPassword)) {
                userToUpdate.setRawPassword(newPassword);
                String newEncryptedPassword = passwordEncoder.encode(newPassword);
                userToUpdate.setPassword(newEncryptedPassword);
                userDao.save(userToUpdate);
            }
        }
    }

    //更改會員資訊(管理員用)
    @Transactional
    public String updateUserInformation(Integer userId,
                                        String newNickname,
                                        String newPassword,
                                        Integer authorityId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User userToUpdate = userOpt.get();
            userToUpdate.setNickname(newNickname);
            userToUpdate.setRawPassword(newPassword);
            userToUpdate.setPassword(passwordEncoder.encode(newPassword));
            userToUpdate.setAuthority(authorityService.getAuthorityById(authorityId));
            userDao.save(userToUpdate);
            return "更改成功";
        } else {
            return "找不到會員";
        }

    }

    //以ID刪除會員
    @Transactional
    public String deleteUserById(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User userToDelete = userOpt.get();
            userDao.delete(userToDelete);
            return "刪除成功";
        } else {
            return "找不到會員";
        }
    }

    //以ID尋找會員
    public User getUserById(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user;
        }
        return null;
    }

    //尋找所有會員
    public List<User> findAllUser() {
        List<User> userList = userDao.findAll();
        return userList;
    }

    //尋找所有會員(分頁)
    public UserPageDto getAllUserInPage(Pageable pageable) {
        Page<User> userPage = userDao.findAll(pageable);
        return new UserPageDto(userPage);
    }

    //以UserId尋找清單
    public List<FavList> findAllFavListByUserId(Integer userId) {
        Optional<User> userOpt = userDao.findById(userId);
        //確認會員是否存在
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getFavLists();
        }
        return null;
    }


}
