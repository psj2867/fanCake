package ml.psj2867.demo.service.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ml.psj2867.demo.dao.AuthoritiesEntityDao;
import ml.psj2867.demo.dao.UserEntityDao;
import ml.psj2867.demo.entity.AuthoritiesEntity;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;
import ml.psj2867.demo.service.user.model.UserForm;

@Transactional
@Service
public class UserService  {    
    @Autowired
    private UserEntityDao userDao;
    @Autowired
    private AuthoritiesEntityDao authDao;

    public UserEntity addOriginUser(UserForm user) throws DataIntegrityViolationException{
        UserEntity userEntity = user.toEntity();
        userEntity.setLoginType(LoginTypeEnum.ORIGIN);
        return addUser(userEntity);
    }
    public UserEntity addUser(UserEntity user) throws DataIntegrityViolationException{
        userDao.save(user);
        return user;
    }    

    public AuthoritiesEntity addAuth(int idx, String auth) throws DataIntegrityViolationException{
        UserEntity user = userDao.getById(idx);
        AuthoritiesEntity authEntity = AuthoritiesEntity.builder()
                                            .auth(auth)
                                            .user(user)
                                            .build();
        authDao.save(authEntity);
        authEntity.getUser();
        return authEntity;
    }

    public UserEntity setCreator(UserForm user, boolean isCreator) throws DataIntegrityViolationException{
        UserEntity userEntity = userDao.findById(user.getUserIdx())
                                        .orElseThrow();
        userEntity.setCreator(isCreator);
        userDao.save(userEntity);
        return userEntity;
    }

}