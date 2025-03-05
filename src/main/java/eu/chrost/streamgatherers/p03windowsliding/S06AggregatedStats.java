package eu.chrost.streamgatherers.p03windowsliding;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Gatherers;

public class S06AggregatedStats {
    public static void main(String[] args) {
        List<Double> data = List.of(22.1, 22.5, 22.4, 23.0, 25.1, 24.8, 24.9);
        data.stream()
                .gather(Gatherers.windowSliding(4))
                .forEach(window -> {
                    DoubleSummaryStatistics stats = window.stream().mapToDouble(d -> d).summaryStatistics();
                    System.out.printf("Min: %.1f, Max: %.1f, Avg: %.1f\n", stats.getMin(), stats.getMax(), stats.getAverage());
                });
    }
}
