package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ParEvaluator<T> implements Evaluator<T> {
  private final HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();
  private final TaskPool pool;

  public ParEvaluator(int numThreads) { pool = new TaskPool(numThreads); }

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 4: parallel function evaluator
    listeners.computeIfAbsent(a, k -> Collections.synchronizedList(new ArrayList<>())).add(value -> {

        Optional<FunNode<T>> readyNode = b.setInput(i, value);
        readyNode.ifPresent(node -> pool.addTask(() -> evaluate(node)));

    });
//    throw new UnsupportedOperationException();
  }

  public void terminate() { pool.terminate(); }

  @Override
  public void start(List<FunNode<T>> nodes) {
    // part 4: parallel function evaluator
    List<Runnable> tasks = nodes.stream()
            .map(node -> (Runnable) () -> evaluate(node))
            .collect(Collectors.toList());
    pool.addTasks(tasks);
//    throw new UnsupportedOperationException();
  }
  private void evaluate(FunNode<T> node) {
    node.eval();
    T result = node.getResult();
    List<Consumer<T>> nodeListeners = listeners.get(node);
    if (nodeListeners != null) {

        nodeListeners.forEach(listener -> listener.accept(result));

    }
  }
}
