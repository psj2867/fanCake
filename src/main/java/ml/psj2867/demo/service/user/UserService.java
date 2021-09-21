package ml.psj2867.demo.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ml.psj2867.demo.dao.UserEntityDao;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;
import ml.psj2867.demo.service.user.model.UserForm;

@Service
public class UserService  {
     
    
    @Autowired
    private UserEntityDao userDao;


    public UserEntity addOriginUser(UserForm user) throws DataIntegrityViolationException{
        UserEntity userEntity = user.convertToEntity();
        userEntity.setType(LoginTypeEnum.ORIGIN.name());
        userDao.save(userEntity);
        return userEntity;
    }


}