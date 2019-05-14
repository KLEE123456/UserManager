package com.klee.UserManager.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class User implements Serializable {
    private  int userId;

    private  String userName;

    private  String userPwd;

    private  String  userSex;

    private  String userPhone;
}
