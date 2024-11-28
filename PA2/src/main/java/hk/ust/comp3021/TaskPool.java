package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TaskPool {
  public class TaskQueue {
    private final ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private boolean terminated = false;
    private int working =0;
    private final Semaphore idle;

    public TaskQueue(int numThreads, Semaphore idle) {
      working = numThreads;
      this.idle = idle;
    }

    public synchronized Optional<Runnable> getTask() {
      // part 3: task pool
      while (queue.isEmpty() && !terminated) {
        try {
          wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return Optional.empty();
        }
      }
      if (queue.isEmpty()) {
        return Optional.empty();
      }
      working--;
      return Optional.of(queue.poll());
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTask(Runnable task) {
      // part 3: task pool
      if (!terminated) {
        queue.offer(task);
        notify();
      }
//      throw new UnsupportedOperationException();
    }

    public synchronized void terminate() {
      // part 3: task pool
      terminated = true;
      queue.clear();
      notifyAll();
    }
  }

  private final TaskQueue queue;
  private final Thread[] workers;
  private final Semaphore idle = new Semaphore(0);

  public TaskPool(int numThreads) {
    // part 3: task pool
    queue = new TaskQueue(numThreads, idle);
    workers = IntStream.range(0, numThreads)
            .mapToObj(i -> new Thread(() ->
                    Stream.generate(queue::getTask)
                            .takeWhile(Optional::isPresent)
                            .forEach(task -> {
                              task.get().run();
                              synchronized (queue) {
                                queue.working++;
                                if (queue.working == getWorkingNumber() && queue.queue.isEmpty())
                                  idle.release();
                              }
                            })))
            .toArray(Thread[]::new);


    Arrays.stream(workers).forEach(Thread::start);
//    throw new UnsupportedOperationException();
  }

  public void addTask(Runnable task) { queue.addTask(task); }

  public void addTasks(List<Runnable> tasks) {
    // part 3: task pool
    if (!tasks.isEmpty()) {
      tasks.forEach(queue::addTask);
      try {
        idle.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
  public int getWorkingNumber() { return workers.length; }
  public void terminate() {
    queue.terminate();
    for (Thread thread : workers) {
      thread.interrupt();
    }
    for (Thread thread : workers) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
        Thread.currentThread().interrupt();
      }
    }
    for (Thread thread : workers) {
      if (thread.isAlive()) {
        thread.interrupt();
      }
    }
  }
}
