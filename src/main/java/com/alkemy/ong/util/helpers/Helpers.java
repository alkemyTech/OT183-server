package com.alkemy.ong.util.helpers;

import java.util.regex.Pattern;

public class Helpers {


    public static Boolean controlNameString(String name){

        Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+");
        return NAME_PATTERN.matcher(name.trim().replaceAll("\\s+","")).matches();
    }

    public static Boolean controlEmptyField(String name){
        return (
            name == null || name.replaceAll("\\s+","").isEmpty()
        )? true : false;
    }



}
