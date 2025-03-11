package eu.chrost.streamgatherers.p06stateless;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;

class S11MapNotNull {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(null);
        numbers.add(3);
        List<Integer> doubledNumbers = numbers.stream()
                .gather(Gatherer.of(
                     Gatherer.Integrator.<Void, Integer, Integer>ofGreedy((_, item, downstream) -> {
                         if (item != null) {
                             return downstream.push(item * 2);
                         }
                         return true;
                     })
                ))
                .toList();
        System.out.println(doubledNumbers);
    }
}
