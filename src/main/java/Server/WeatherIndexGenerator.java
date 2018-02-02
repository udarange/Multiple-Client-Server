package Server;

import java.util.Random;

/**
 * Generate Weather Status
 * Created by archana on 1/30/18.
 */
public class WeatherIndexGenerator {
    public String currentIndex() {
        /* Generate random weather state number*/
        Random r = new Random();

        int Low = 10;
        int High = 100;
        int state = r.nextInt(High - Low) + Low;

        String currentWeatherIndex = Integer.toString(state);
        System.out.println("SEND CURRENT WEATHER INDEX: " + currentWeatherIndex);

        return currentWeatherIndex;
    }
}
