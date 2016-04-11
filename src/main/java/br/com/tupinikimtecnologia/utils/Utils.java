package br.com.tupinikimtecnologia.utils;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import com.github.javafaker.Faker;
import java.io.File;

import java.util.Random;

/**
 * Created by felipe on 14/08/15.
 */
public class Utils {
    
    public static void makeDbDir(){
        File file = new File(GeralConstants.Db.DB_DIR);
        file.mkdir();
    }

    public static String randomUserAgent(){
        Random rand = new Random();
        return GeralConstants.RandomData.USER_ANGET[rand.nextInt(GeralConstants.RandomData.USER_ANGET.length)];
    }
    
    //random first name
    public static String randomFirstName(){
        Faker randData = new Faker();
        return randData.name().firstName();
    }
    
    //random last name
    public static String randomLastName(){
        Faker randData = new Faker();
        return randData.name().lastName();
    }
    
    //random full name
    public static String randomFullName(){
        Faker randData = new Faker();
        return randData.name().fullName();
    }
    
    //random age
    public static String randomAge(){
        Random randAge = new Random();
        return Integer.toString(randAge.nextInt(80));
    }
    
    //random address
    public static String randomAddress(){
        Faker randData = new Faker();
        return randData.address().streetAddress(false);
    }
    
    //random city
    public static String randomCity(){
        Faker randData = new Faker();
        return randData.address().city();
    }
    
    //random country
    public static String randomCountry(){
        Faker randData = new Faker();
        return randData.address().country();
    }
    
    //random latitude
    public static String randomLatitude(){
        Faker randData = new Faker();
        return randData.address().latitude();
    }
    
    //random longitude
    public static String randomLongitude(){
        Faker randData = new Faker();
        return randData.address().longitude();
    }
    
    //random sentence
    public static String randomSentence(){
        Faker randData = new Faker();
        return randData.lorem().sentence();
    }
    
    //random email
    public static String randomEmail(){
        Faker randData = new Faker();
        return randData.internet().emailAddress();
    }

}
