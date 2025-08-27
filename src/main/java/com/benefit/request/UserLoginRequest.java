package com.benefit.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author feng_dai
 * @author Allen
 * @create 2025-06-07-14:30
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String account;

    private String password;
}
