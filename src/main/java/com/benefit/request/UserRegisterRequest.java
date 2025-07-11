package com.benefit.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author feng_dai
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String account;

    private String password;

    private String checkPassword;

}
