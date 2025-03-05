package eu.chrost.streamgatherers.p05mapconcurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.stream.Gatherers;

enum TripType {
    THERE,
    BACK
}

@Slf4j
public class S09ParallelTasks {
    public static void main(String[] args) {
        EnumSet<TripType> tripTypes = EnumSet.allOf(TripType.class);
        tripTypes.stream()
                .gather(Gatherers.mapConcurrent(2, tripType -> bookTicket("Katowice", tripType)))
                .forEach(t -> log.info("{}", t));
    }

    @SneakyThrows
    private static String bookTicket(String destination, TripType tripType) {
        log.info("Before sleep {}", tripType);
        Thread.sleep(1000);
        log.info("After sleep {}", tripType);
        return String.format("Booked %s travel to: %s", tripType, destination);
    }
}
