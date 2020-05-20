package com.example.shardingjdbcmasterslave.mybatits;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MasterSalveController {
    @Autowired
    private MasterSalveDao masterSalveDao;

    @GetMapping("/a")
    public void ss() {
        List<String> list = masterSalveDao.getList();
        System.out.println(list);
    }
}
