package br.com.tupinikimtecnologia.utils;

import br.com.tupinikimtecnologia.constants.GeralConstants;
import com.github.javafaker.Faker;
import java.io.File;

import java.util.Random;

/**
 * Static methods for utils things
 */
public class Utils {
    
    /**
     * Array of chars and numbers, for use on the randomPassword method
     */
    public static final char[] character = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    
    /**
     * Create the DB dir if not exists
     */
    public static void makeDbDir(){
        File file = new File(GeralConstants.Db.DB_DIR);
        file.mkdir();
    }

    /**
     * Set the User Agent using the static array of String
     * @return String with User Agent
     */
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

    /**
     * Set a random password from array of char, the password lenght are randomly
     * generated
     * @return Return the password String
     */
    public static String randomPassword(){
        Random rand = new Random();
        int qtCharacter = rand.nextInt(9) + 5;
        String password = "";
        for(int i=0; i<qtCharacter; i++){
            password += character[rand.nextInt(character.length)];
        }
        return password;
    }
}
