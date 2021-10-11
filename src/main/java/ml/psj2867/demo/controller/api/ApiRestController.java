package ml.psj2867.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.user.UserService;
import ml.psj2867.demo.service.user.model.sign.SignUpUserForm;


@RestController
@RequestMapping("api")
public class ApiRestController {

    @Autowired
    private UserService userService;


}