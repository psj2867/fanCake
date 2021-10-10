package ml.psj2867.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.user.UserService;
import ml.psj2867.demo.service.user.model.SimpleUserDto;
import ml.psj2867.demo.service.user.model.UserListForm;
@RestController
@RequestMapping("api/users")
public class UsersRestController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Page<SimpleUserDto>> getList(UserListForm userListForm){        
        return ResponseEntity.ok(userService.getUsers(userListForm));
    }


  
    

}