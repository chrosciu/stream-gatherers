package eu.chrost.streamgatherers.p06custom;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Gatherer;

@RequiredArgsConstructor
class MapNotNullGatherer<T, M> implements Gatherer<T, Void, M> {
    private final Function<T, M> mapper;

    @Override
    public Integrator<Void, T, M> integrator() {
        return Integrator.ofGreedy((_, item, downstream) -> {
            if (item != null) {
                return downstream.push(mapper.apply(item));
            }
            return true;
        });
    }
}

class S08MapNotNull {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(null);
        numbers.add(3);
        List<Integer> doubledNumbers = numbers.stream()
                .gather(new MapNotNullGatherer<>(i -> i * 2))
                .toList();
        System.out.println(doubledNumbers);
    }
}
