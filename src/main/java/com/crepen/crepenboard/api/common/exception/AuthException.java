package com.crepen.crepenboard.api.common.exception;

public class AuthException extends ResponseException{
    AuthException(int statusCode , String errorCode , String message){
        super(statusCode ,errorCode , message);
    }


    public static AuthException FAILED_LOGIN = new AuthException(401 , "FAILED_LOGIN" , "FAILED_LOGIN");
    public static AuthException BLOCK_USER = new AuthException(401, "BLOCK_USER" , "BLOCK_USER");
    public static AuthException UNMATCHED_GRANT_TYPE = new AuthException(403 , "UNMATCHED_GRANT_TYPE" , "UNMATCH_GRANT_TYPE");
    public static AuthException UNMATCHED_TOKEN_TYPE = new AuthException(401 , "UNMATCHED_TOKEN_TYPE" , "UNMATCHED_TOKEN_TYPE");
    public static AuthException EXPIRE_TOKEN = new AuthException(401 , "EXPIRE_TOKEN" , "EXPIRE_TOKEN");
    public static AuthException ACCESS_DENIED = new AuthException(403 , "ACCESS_DENIED" , "ACCESS_DENIED");
    public static AuthException UNAUTHORIZED = new AuthException(401 , "UNAUTHORIZED" , "UNAUTHORIZED");
}
