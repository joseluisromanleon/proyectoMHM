package org.iesvdm.mhm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api")
public class halController {

    @GetMapping("/swagger")
    private RedirectView mostrarSwagger(){
        return new RedirectView("http://localhost:8080/swagger-ui/index.html#/");
    }
    @GetMapping("/explorer")
    private RedirectView mostrarExplorer(){
        return new RedirectView("http://localhost:8080/data-api/explorer/index.html#uri=/data-api");
    }


}
