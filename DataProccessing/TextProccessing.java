package com.introtoandroid.samplematerial.DataProccessing;

/**
 * Created by jmonaste on 14/03/2017.
 */
public class TextProccessing {

    public static String firstLetterToUpperCase(String string) {
        string = string.toUpperCase();
        String firstChar = string.substring(0,1);
        String endPart = string.substring(1,string.length());
        endPart = endPart.toLowerCase();
        string = firstChar.concat(endPart);
        return string;
    }
    public static String allFirstLettersToUpperCase(String string){
        String finalString = new String("");
        String[] substring = string.split(" ");

        for(int i=0; i<substring.length; i++){
            finalString = finalString.concat(firstLetterToUpperCase(substring[i]));
            if(i<substring.length){
                finalString = finalString.concat(" ");
            }
        }
        return finalString;
    }

}
