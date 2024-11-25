package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ParEvaluator<T> implements Evaluator<T> {
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();
  private TaskPool pool;

  public ParEvaluator(int numThreads) { pool = new TaskPool(numThreads); }

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 4: parallel function evaluator
    listeners.computeIfAbsent(a, k -> Collections.synchronizedList(new ArrayList<>()))
            .add(result -> b.setInput(i, result)
                    .ifPresent(node -> pool.addTask(() -> evaluateNode(node))));
//    throw new UnsupportedOperationException();
  }

  public void terminate() { pool.terminate(); }

  public void start(List<FunNode<T>> nodes) {
    // part 4: parallel function evaluator
    AtomicInteger remainingTasks = new AtomicInteger(nodes.size());

    // Add tasks to pool
    nodes.forEach(node -> pool.addTask(() -> {
      evaluateNode(node);
      if (remainingTasks.decrementAndGet() == 0) {
        synchronized (this) {
          this.notifyAll(); // Notify when all tasks are done
        }
      }
    }));

    // Wait for all tasks to complete
    synchronized (this) {
      while (remainingTasks.get() > 0) {
        try {
          this.wait(); // Wait for notification when tasks are finished
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }

    // Ensure pool termination after tasks are complete
    terminate();
//    throw new UnsupportedOperationException();
  }
  private void evaluateNode(FunNode<T> node) {
    node.eval();
    T result = node.getResult();
    List<Consumer<T>> nodeListeners;
    synchronized (listeners) {
      nodeListeners = listeners.get(node);
    }
    if (nodeListeners != null) {
      nodeListeners.forEach(listener -> listener.accept(result));
    }
  }
}
