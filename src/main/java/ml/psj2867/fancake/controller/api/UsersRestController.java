package ml.psj2867.fancake.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.user.model.DetailUserDto;
import ml.psj2867.fancake.service.user.model.SimpleUserDto;
import ml.psj2867.fancake.service.user.model.UserListForm;
@RestController
@RequestMapping("api/users")
public class UsersRestController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public Page<DetailUserDto> getList(UserListForm userListForm){        
        return userService.getUsers(userListForm);
    }


  
    

}