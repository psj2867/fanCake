package ml.psj2867.demo.controller;

import java.io.File;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.demo.dao.UserEntityDao;

@Slf4j
@Controller
// @Secured("ROLE_TEST")
public class MainController {

    @RequestMapping("")
    public String getMain() throws SQLException {
        return "redirect:/static/fanCake/index.html";
    }

    @RequestMapping("template")
    public String getSecurity(String path) {
        return "layout/" + path;
    }

    @RequestMapping("security")
    public @ResponseBody Object getSecurity() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping("test")
    public @ResponseBody Object getTest() {
        return "test page";
    }
    @Autowired
    UserEntityDao userDao;
    @RequestMapping("chain")
    public @ResponseBody Object getChain() {
        // return userDao.find
        return null;
    }



    @RequestMapping("classpath")
    public @ResponseBody Object getClassPath() {
        final File f = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        return f.toString();
    }

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("webPath")
    public @ResponseBody Object getWebPath() {
        File f = new File( servletContext.getRealPath("/WEB-INF/") );
        return f.toString();
    }
    @RequestMapping("rootPath")
    public @ResponseBody Object getRootPath() {
        File f = new File( servletContext.getRealPath("/") );
        return f.toString();
    }

}