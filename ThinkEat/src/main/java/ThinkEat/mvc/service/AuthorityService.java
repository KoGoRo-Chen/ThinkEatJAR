package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.AuthorityDao;
import ThinkEat.mvc.dao.UserDao;
import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityService {

    private final UserDao userDao;
    private final UserServiceDao userServiceDao;
    private final AuthorityDao authorityDao;

    @Autowired
    public AuthorityService(UserDao userDao,
                            UserServiceDao userServiceDao,
                            AuthorityDao authorityDao) {
        this.userDao = userDao;
        this.userServiceDao = userServiceDao;
        this.authorityDao = authorityDao;
    }

    //以ID尋找權限
    public Authority getAuthorityById(Integer authId) {
        Optional<Authority> authorityOpt = authorityDao.findById(authId);
        //確認會員是否存在
        if (authorityOpt.isPresent()) {
            Authority authority = authorityOpt.get();
            return authority;
        }
        return null;
    }

    //尋找所有清單
    public List<Authority> findAllAuthority() {
        List<Authority> authorityList = authorityDao.findAll();
        return authorityList;
    }


}
