package br.com.tupinikimtecnologia.utils;

import br.com.tupinikimtecnologia.constants.GeralConstants;

import java.util.Random;

/**
 * Created by felipe on 14/08/15.
 */
public class Utils {

    public static String randomUserAgent(){
        Random rand = new Random();
        return GeralConstants.RandomData.USER_ANGET[rand.nextInt(GeralConstants.RandomData.USER_ANGET.length)];
    }

}
