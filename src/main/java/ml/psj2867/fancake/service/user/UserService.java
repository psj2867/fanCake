package ml.psj2867.fancake.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.security.AuthEnum;
import ml.psj2867.fancake.dao.AuthoritiesEntityDao;
import ml.psj2867.fancake.dao.UserEntityDao;
import ml.psj2867.fancake.entity.AuthoritiesEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.conflict.ConflictException;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.service.email.EmailService;
import ml.psj2867.fancake.service.user.model.CreatorListForm;
import ml.psj2867.fancake.service.user.model.DetailUserDto;
import ml.psj2867.fancake.service.user.model.FindIdForm;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;
import ml.psj2867.fancake.service.user.model.UpdateUserPasswordForm;
import ml.psj2867.fancake.service.user.model.UserListForm;
import ml.psj2867.fancake.service.user.model.UserUpdateForm;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;
import ml.psj2867.fancake.service.user.model.sign.SignUpUserForm;
import ml.psj2867.fancake.util.SecurityUtil;

@Slf4j
@Transactional
@Service
public class UserService {
    @Autowired
    private UserEntityDao userDao;
    @Autowired
    private AuthoritiesEntityDao authDao;
    @Autowired
    private EmailService emailService;

    public DetailUserDto getDetailUser(){
        return DetailUserDto.of(this.getUserOrThrow());
    }

    public void updateUser(final UserUpdateForm userUpdateForm) {
        UserEntity user = this.getUserOrThrow();
        userUpdateForm.overWrite(user);
        userDao.save(user);
    } 

    public void updatePassword(final UpdateUserPasswordForm passwordForm) {
        UserEntity user = this.getUserOrThrow();
        checkCurrentPasswordIsValid(user, passwordForm);
        passwordForm.overWrite(user);
        userDao.save(user);
    }
    private void checkCurrentPasswordIsValid(UserEntity user, UpdateUserPasswordForm passwordForm){
      if(! user.getPassword().equals(passwordForm.getCurrentPassword()) )
        throw new ConflictException();
    }

    public Optional<UserEntity> findId(final FindIdForm findIdForm) {
        return userDao.findByNameIsAndEmailIsAndLoginTypeIs(findIdForm.getName(), findIdForm.getEmail(),LoginTypeEnum.ORIGIN);
    } 
    
    public void findPassword(final FindIdForm findIdForm) {
        final Optional<UserEntity>  user = userDao.findByNameIsAndEmailIsAndLoginTypeIs(findIdForm.getName(), findIdForm.getEmail(),LoginTypeEnum.ORIGIN);
        user.ifPresent(this::sendEmail);
    } 
    private void sendEmail(final UserEntity user){
        // emailService.sendSimpleMessage(user.getEmail(), emailSubject(user), emailContent(user) );
        emailService.sendFindPasswordEmail(user);
    }


    public Page<SimpleUserDto> getUsers(final UserListForm userListForm) {
        return userDao.findAll(userListForm.toSpec(), userListForm.toPageable())
                        .map(SimpleUserDto::of);
    }
    public Page<SimpleUserDto> getCreators(final CreatorListForm creatorListForm) {
        return userDao.findAll(creatorListForm.toSpec(), creatorListForm.toPageable())
                        .map(SimpleUserDto::of);
    }
   

    public UserEntity addOriginUser(final SignUpUserForm user) throws DataIntegrityViolationException{
        final UserEntity userEntity = user.toEntity();
        userEntity.setLoginType(LoginTypeEnum.ORIGIN);
        return addUser(userEntity);
    }
    public UserEntity addNaverUser(final UserEntity user) throws DataIntegrityViolationException{
        return addUser(user);
    }
    private UserEntity addUser(final UserEntity user) throws DataIntegrityViolationException{
        userDao.save(user);
        return user;
    }    

    public AuthoritiesEntity addAuth(final int idx, final AuthEnum auth) throws DataIntegrityViolationException{
        final UserEntity user = userDao.getById(idx);
        final AuthoritiesEntity authEntity = AuthoritiesEntity.builder()
                                            .auth(auth)
                                            .user(user)
                                            .build();
        authDao.save(authEntity);
        return authEntity;
    }
    public boolean removeAuth(final int idx, final AuthEnum auth){
        final UserEntity user = userDao.getById(idx);
        final List<AuthoritiesEntity> grants = user.getAuths().stream()
                                            .filter(authL -> authL.getAuth().equals(auth))
                                            .collect(Collectors.toList());
        authDao.deleteAll(grants);
        return ! grants.isEmpty();
    }


    public UserEntity getUserOrThrow(){
        return getUser().orElseThrow(()-> new UnAuthorizedException());
    }
    public Optional<UserEntity> getUser(){         
        return SecurityUtil.getUserIdx().flatMap(this::getUser);
    }
    public Optional<UserEntity> getUser(final Integer userIdx){
        try {
            Optional<UserEntity> user = userDao.findById(userIdx);
            return user;
        } catch (final EntityNotFoundException e) {
            log.debug("",e);
            return Optional.empty();
        }
    }
}