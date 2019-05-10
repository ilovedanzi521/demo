package com.yss.demo.controller;

import com.yss.demo.entity.User;
import com.yss.demo.dao.UserRepository;
import com.yss.demo.result.Result;
import org.dom4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    private static int i=100;
    private static Result SUCC =  new Result(200,true,"succ");
    private static Result ERR =   new Result(500,false,"error");
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "api")
    public String welcome(){
        return "welcome yss!";
    }

    //select
    @CrossOrigin
    @PostMapping(value = "api/list")
    @ResponseBody
    public List<User> findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return userRepository.findAll(sort);
    }

    /**
     * 新增功能
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/addUser")
    @ResponseBody
    public Object  addUser( User user){
        try {
         if(null==user.getId()||"".equals(user.getId())){
               user.setId(i++);
               userRepository.save(user);
         }else{
             userRepository.save(user);
         }
        }catch(Exception e){
            return ERR;
        }
        return SUCC;
    }

    /**
     * 删除功能
     * @param id
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/delUser")
    @ResponseBody
    public Result delUser(@RequestParam("id") Integer id){
//        Map<String,Object> result = new HashMap<String, Object>();
//        result.put("success", true);
//        result.put("msg", "登录成功");
        try {
            userRepository.deleteById(id);
        }catch (Exception e){
            return ERR;
        }
        return SUCC;
    }

    /**
     * 登录功能
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User user){
        String username =user.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User rstUser = userRepository.findByUsername(username);
        if (null == rstUser) {
            return ERR;
        } else {
            if(rstUser.getPassword().equals(user.getPassword())){
                return SUCC;
            }
            return ERR;
        }
    }

}
