package eu.chrost.streamgatherers.p06custom;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

@RequiredArgsConstructor
class DistinctByGatherer<T, P> implements Gatherer<T, Set<P>, T> {
    private final Function<T, P> selector;

    @Override
    public Supplier<Set<P>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<P>, T, T> integrator() {
        return Integrator.ofGreedy((state, item, downstream) -> {
            P extracted = selector.apply(item);
            if (!state.contains(extracted)) {
                state.add(extracted);
                return downstream.push(item);
            }
            return true;
        });
    }
}

enum Color {
    RED,
    BLACK,
    WHITE;
}

record Car(String model, Color color) {}

class S11DistinctBySelector {
    public static void main(String[] args) {
        List<Car> cars = List.of(
                new Car("Polonez Caro", Color.RED),
                new Car("VW Passat", Color.BLACK),
                new Car("Fiat Punto", Color.WHITE),
                new Car("Fiat Seicento", Color.BLACK)
        );
        List<Car> carsWithDistinctColor = cars.stream()
                .gather(new DistinctByGatherer<>(Car::color))
                .toList();
        System.out.println(carsWithDistinctColor);
    }
}
