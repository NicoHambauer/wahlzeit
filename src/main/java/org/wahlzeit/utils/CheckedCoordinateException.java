package org.wahlzeit.utils;

public class CheckedCoordinateException extends Exception{

    public CheckedCoordinateException(String msg){
        super(msg);
    }

    /**
     * Additional wraps an Error/Exception into the new CheckedCoordinateException
     * @param msg
     * @param th
     */
    public CheckedCoordinateException(String msg, Throwable th){
        super(msg, th);
    }
}
