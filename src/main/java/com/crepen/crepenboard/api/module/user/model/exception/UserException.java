package com.crepen.crepenboard.api.module.user.model.exception;

import com.crepen.crepenboard.api.common.system.model.exception.ResponseException;

public class UserException extends ResponseException {
    UserException(int statusCode , String errorCode , String message){
        super(statusCode ,errorCode , message);
    }



    public static UserException ADD_USER_DUPLICATE_NAME = new UserException(403 , "ADD_USER_DUPLICATE_NAME" , "ADD_USER_DUPLICATE_NAME");
    public static UserException ADD_USER_DUPLICATE_ID = new UserException(403,  "ADD_USER_DUPLICATE_ID" , "ADD_USER_DUPLICATE_ID");
    public static UserException ADD_USER_DUPLICATE_EMAIL = new UserException(403,  "ADD_USER_DUPLICATE_EMAIL" , "ADD_USER_DUPLICATE_EMAIL");




    public static UserException USER_NOT_FOUND = new UserException(401 , "USER_NOT_FOUND" , "error.user.notfound");

    public static UserException ADD_USER_INVALID_EMAIL = new UserException(403,  "ADD_USER_INVALID_EMAIL" , "error.validation.email");
    public static UserException ADD_USER_INVALID_PASSWORD = new UserException(403,  "ADD_USER_INVALID_PASSWORD" , "error.validation.password");
    public static UserException ADD_USER_INVALID_USERID = new UserException(403,  "ADD_USER_INVALID_PASSWORD" , "error.validation.userid");
}