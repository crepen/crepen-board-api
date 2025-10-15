package com.crepen.crepenboard.api.common.exception;

import org.apache.catalina.User;

public class UserException extends ResponseException{
    UserException(int statusCode , String errorCode , String message){
        super(statusCode ,errorCode , message);
    }


    public static UserException USER_NOT_FOUND = new UserException(401 , "USER_NOT_FOUND" , "USER_NOT_FOUND");
    public static UserException ADD_USER_DUPLICATE_NAME = new UserException(403 , "ADD_USER_DUPLICATE_NAME" , "ADD_USER_DUPLICATE_NAME");
    public static UserException ADD_USER_DUPLICATE_ID = new UserException(403,  "ADD_USER_DUPLICATE_ID" , "ADD_USER_DUPLICATE_ID");
    public static UserException ADD_USER_DUPLICATE_EMAIL = new UserException(403,  "ADD_USER_DUPLICATE_EMAIL" , "ADD_USER_DUPLICATE_EMAIL");

}