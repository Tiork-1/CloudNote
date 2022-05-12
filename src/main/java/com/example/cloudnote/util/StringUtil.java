package com.example.cloudnote.util;

import java.util.List;

public class StringUtil {
    public static boolean check(String str){
        if (str==null){
            return false;
        }
        if (str.length()==0){
            return false;
        }
        return true;
    }
}
