package hk.ust.comp3021;

import java.util.*;
import java.util.function.Consumer;

public class SeqContEvaluator<T> implements Evaluator<T> {
  private ArrayDeque<FunNode<T>> toEval = new ArrayDeque<>();
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 2: sequential function evaluator
    Consumer<T> consumer = result -> {
      b.setInput(i, result);
      // Add b to queue if it's not already evaluated
      if (!b.isEvaluated()) {
        toEval.add(b);
      }
    };

    // Add the consumer to a's listeners
    listeners.computeIfAbsent(a, k -> new ArrayList<>()).add(consumer);  }

  public void start(List<FunNode<T>> nodes) {
    // part 2: sequential function evaluator
    toEval.addAll(nodes);

    // Process queue until empty
    while (!toEval.isEmpty()) {
      FunNode<T> node = toEval.poll();

      // Skip if already evaluated
      if (node.isEvaluated()) {
        continue;
      }

      try {
        node.eval();
        // After successful evaluation, notify all listeners
        List<Consumer<T>> nodeListeners = listeners.getOrDefault(node, Collections.emptyList());
        T result = node.getResult();
        for (Consumer<T> listener : nodeListeners) {
          listener.accept(result);
        }
      } catch (IllegalStateException e) {
        // If evaluation fails due to missing inputs, add back to queue to try later
        toEval.add(node);
      }
    }
  }
}