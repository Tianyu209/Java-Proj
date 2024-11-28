package hk.ust.comp3021;

import java.util.*;
import java.util.function.Consumer;

public class SeqContEvaluator<T> implements Evaluator<T> {
  private final ArrayDeque<FunNode<T>> toEval = new ArrayDeque<>();
  private final HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 2: sequential function evaluator
    listeners.computeIfAbsent(a, k -> new ArrayList<>()).add(result -> {
      Optional<FunNode<T>> ready = b.setInput(i, result);
      ready.ifPresent(toEval::add);
    });
  }

  public void start(List<FunNode<T>> nodes) {
    // part 2: sequential function evaluator
    toEval.addAll(nodes);

    while (!toEval.isEmpty()) {
      Optional.ofNullable(toEval.poll())
              .filter(node -> !node.isEvaluated())
              .ifPresent(this::evaluateNode);
    }
  }
  private void evaluateNode(FunNode<T> node) {
    try {
      node.eval();
      Optional.ofNullable(listeners.get(node)).stream().flatMap(Collection::stream)
              .forEach(listener -> listener.accept(node.getResult()));
    } catch (IllegalStateException e) {
      toEval.add(node);
    }
  }
}