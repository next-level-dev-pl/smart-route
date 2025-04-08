package pl.nextleveldev.smart_route.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Gatherers {
    public static <T> Collector<T, List<List<T>>, Stream<List<T>>> groupingByFixedSize(int size) {
        return Collector.of(
                ArrayList::new,
                (acc, t) -> {
                    if (acc.isEmpty() || acc.getLast().size() == size) {
                        acc.add(new ArrayList<>());
                    }
                    acc.getLast().add(t);
                },
                (acc1, acc2) -> {
                    if (!acc2.isEmpty()) {
                        if (acc1.isEmpty()) {
                            return acc2;
                        }
                        List<T> last = acc1.getLast();
                        List<T> first = acc2.getFirst();
                        if (last.size() < size && last.size() + first.size() <= size) {
                            last.addAll(first);
                            acc2.removeFirst();
                            acc1.addAll(acc2);
                        } else {
                            acc1.addAll(acc2);
                        }
                    }
                    return acc1;
                },
                Collection::stream);
    }
}
