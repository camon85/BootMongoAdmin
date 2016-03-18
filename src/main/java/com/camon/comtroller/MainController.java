package com.camon.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jooyong on 2016-02-25.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
