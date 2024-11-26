package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class TaskPool {
  public class TaskQueue {
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private boolean terminated = false;
    private int working =0;
    private Semaphore idle;

    public TaskQueue(int numThreads, Semaphore idle) {
      working = 0;
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
      if (queue.isEmpty() && terminated) {
        return Optional.empty();
      }
      working++;
      return Optional.of(queue.poll());
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTask(Runnable task) {
      // part 3: task pool
      queue.add(task);
      notifyAll();
//      throw new UnsupportedOperationException();
    }

    public synchronized void terminate() {
      // part 3: task pool
      terminated = true;
      notifyAll();
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTasks(List<Runnable> tasks) {
      //use foreach to replace for-loop
      queue.addAll(tasks);
      notifyAll();
    }
    public synchronized void taskFinished() {
      working--;
      if (working == 0 && queue.isEmpty() && !terminated) {
        idle.release();
      }
    }
  }

  private TaskQueue queue;
  private Thread workers[];
  private Semaphore idle = new Semaphore(0);

  public TaskPool(int numThreads) {
    // part 3: task pool
    idle = new Semaphore(0);
    queue = new TaskQueue(numThreads, idle);
    workers = new Thread[numThreads];

    Arrays.setAll(workers, i -> new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        Optional<Runnable> task = queue.getTask();
        if (task.isEmpty()) break; // Exit when no tasks are left and termination is signaled
        try {
          task.get().run();
        } finally {
          queue.taskFinished();
        }
      }
    }));
    Arrays.stream(workers).forEach(Thread::start);
//    throw new UnsupportedOperationException();
  }

  public void addTask(Runnable task) { queue.addTask(task); }

  public void addTasks(List<Runnable> tasks) {
    // part 3: task pool
    if (tasks.isEmpty()) {
      return;
    }
    queue.addTasks(tasks);
    try {
      idle.acquire();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void terminate() {
    queue.terminate();
    Arrays.stream(workers).forEach(thread -> {
      thread.interrupt();
      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
  }
}
