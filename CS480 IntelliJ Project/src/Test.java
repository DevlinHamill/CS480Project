import java.time.*;

public class Test {
    public static void main(String[] args) {
        // Get the current time in EST
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime nowInEst = ZonedDateTime.now(estZone);

        // Define 4:00 PM in EST
        LocalTime marketCloseTime = LocalTime.of(16, 0); // 4:00 PM

        // Extract the current time
        LocalTime currentTimeInEst = nowInEst.toLocalTime();

        System.out.println("EST current time:" + currentTimeInEst);

        System.out.println("Local current time: " + LocalTime.now());

        // Check if the current time is past 4:00 PM
        if (currentTimeInEst.isAfter(marketCloseTime)) {
            System.out.println("Market closed. Use the last closing data.");
            // Use the last closing time series data point
        } else {
            System.out.println("Market still open. Use the most recent data.");
            // Use the most recent time series data point
        }

        //see if the current time in NY is after 4pm EST (market is closed)
        if(ZonedDateTime.now(ZoneId.of("America/New_York")).toLocalTime().isAfter(LocalTime.of(16, 0))) {

        }
    }
}
