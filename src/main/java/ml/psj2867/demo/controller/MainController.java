package ml.psj2867.demo.controller;

import java.io.File;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.demo.dao.TestEntityDao;

@Slf4j
@Controller
// @Secured("ROLE_TEST")
public class MainController {
    @Autowired
    private TestEntityDao testEntityDao;

    @RequestMapping("")
    public String getMain() throws SQLException {
        return "redirect:/static/theme/freelance/index.html";
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


    @RequestMapping("entity")
    public @ResponseBody Object getEntity() {
        log.info("test log");
        return testEntityDao.findAll();
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