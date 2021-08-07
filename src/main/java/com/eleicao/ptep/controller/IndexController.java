
package com.eleicao.ptep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping
    public String pageIndex() {
        return "Index";
    }
    
}
