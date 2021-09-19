package ml.psj2867.demo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.demo.dao.TestDao;
import ml.psj2867.demo.dao.TestEntityDao;

@Slf4j
@Controller
public class MainController {
    @Autowired
    private TestDao tesdao;
    @Autowired
    private TestEntityDao testEntityDao;

    @RequestMapping("")
    public @ResponseBody Object getMain() throws SQLException {
        // Test
        return "root page";
    }

    @RequestMapping("test")
    public @ResponseBody Object getTest() {
        return "test page";
    }

    @RequestMapping("dao")
    public @ResponseBody Object getDao() {
        return tesdao.getData1();
    }


    @RequestMapping("entity")
    public @ResponseBody Object getEntity() {
        log.info("test log");
        return testEntityDao.findAll();
    }

}