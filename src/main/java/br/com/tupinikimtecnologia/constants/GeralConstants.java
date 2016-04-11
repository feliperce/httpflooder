package br.com.tupinikimtecnologia.constants;

/**
 * Created by felipe on 14/08/15.
 */
public class GeralConstants {
    
    public static final String HOWTO_URL = "https://github.com/feliperce/httpflooder/wiki";

    public static final class RandomData{
        public static final String USER_ANGET[] = {
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0",
                "Mozilla/5.0 (X11; Linux) KHTML/4.9.1 (like Gecko) Konqueror/4.9",
                "Mozilla/5.0 (X11; U; Linux i686; fr-fr) AppleWebKit/525.1+ (KHTML, like Gecko, Safari/525.1+) midori/1.19",
                "Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16",
                "Galaxy/1.0 [en] (Mac OS X 10.5.6; U; en)",
                "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                "Googlebot/2.1 (+http://www.googlebot.com/bot.html)",
                "Googlebot-Image/1.0",
                "iaskspider/2.0(+http://iask.com/help/help_index.html)",
                "Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)",
                "Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)"
        };
        
        public static final String RAND_NAME_FULL = "{%full_name}";
        public static final String RAND_NAME_FIRST = "{%first_name}";
        public static final String RAND_NAME_LAST = "{%last_name}";
        public static final String RAND_AGE = "{%age}";
        public static final String RAND_EMAIL = "{%email}";
        public static final String RAND_ADDRESS = "{%address}";
        public static final String RAND_CITY = "{%city}";
        public static final String RAND_COUNTRY = "{%country}";
        public static final String RAND_LATITUDE = "{%latitude}";
        public static final String RAND_LONGITUDE = "{%longitude}";
        public static final String RAND_SENTENCE = "{%sentence}";
        
        public static final String[] RAND_DATA_ALL = {
            
        };
    }

    public static final class Debug{
        public static final boolean SQL_SHOW = false;
        public static final boolean HTTP_REQUEST_SHOW = true;
    }
}
