package hk.ust.comp3021;

import java.util.*;
import java.util.function.Consumer;

public class SeqEvaluator<T> implements Evaluator<T> {
  private final HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();

  @Override
  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 2: sequential function evaluator
    listeners.computeIfAbsent(a, k -> new ArrayList<>()).add(result -> {
      Optional<FunNode<T>> ready = b.setInput(i, result);
      ready.ifPresent(this::evaluate);
    });
  }

  public void start(List<FunNode<T>> nodes) {
    // part 2: sequential function evaluator
    nodes.forEach(this::evaluate);
  }
  private void evaluate(FunNode<T> node) {
    // Implement recursive evaluation logic here
    node.eval();
    Optional.ofNullable(listeners.get(node))
            .orElse(Collections.emptyList())
            .forEach(listener -> listener.accept(node.getResult()));
  }
}
