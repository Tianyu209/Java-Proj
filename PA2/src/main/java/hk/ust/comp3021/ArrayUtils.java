package hk.ust.comp3021;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayUtils {
  // HINT: try to use this chunk size to partition your work.
  static final int CHUNK_SIZE = 1 << 16;

  public static int[] seqMap(int[] input, IntUnaryOperator map) {
    int[] output = new int[input.length];
    IntStream.range(0, input.length).forEach(i -> {
      output[i] = map.applyAsInt(input[i]);
    });
    return output;
  }

  public static int[] parMap(int[] input, IntUnaryOperator map, TaskPool pool) {
    // Bonus part
    int[] output = new int[input.length];
    int numChunks = (input.length + CHUNK_SIZE - 1) / CHUNK_SIZE;

    pool.addTasks(IntStream.range(0, numChunks).mapToObj(chunk -> (Runnable) () -> {
      int start = chunk * CHUNK_SIZE;
      int end = Math.min(start + CHUNK_SIZE, input.length);
      for (int i = start; i < end; i++) {
        output[i] = map.applyAsInt(input[i]);
      }
    }).toList());

    return output;
//    throw new UnsupportedOperationException();
  }

  public static void seqInclusivePrefixSum(int[] input, IntBinaryOperator op) {
    IntStream.range(1, input.length).forEach(i -> {
      input[i] = op.applyAsInt(input[i - 1], input[i]);
    });
  }

  public static void parInclusivePrefixSum(int[] input, IntBinaryOperator op,
                                           TaskPool pool) {
    // Bonus part
    int n = input.length;
    if (n == 0) return;

    int numChunks = (n + CHUNK_SIZE - 1) / CHUNK_SIZE;
    int[] chunkResults = new int[numChunks];

    // Step 1: Compute local prefix sums for each chunk
    pool.addTasks(IntStream.range(0, numChunks).mapToObj(chunk -> (Runnable) () -> {
      int start = chunk * CHUNK_SIZE;
      int end = Math.min(start + CHUNK_SIZE, n);
      for (int i = start + 1; i < end; i++) {
        input[i] = op.applyAsInt(input[i - 1], input[i]);
      }
      chunkResults[chunk] = input[end - 1]; // Store the last value of each chunk
    }).toList());

    // Step 2: Compute global prefix sum of chunk results sequentially
    for (int i = 1; i < chunkResults.length; i++) {
      chunkResults[i] = op.applyAsInt(chunkResults[i - 1], chunkResults[i]);
    }

    // Step 3: Adjust values in each chunk with the global prefix sum of previous chunks
    pool.addTasks(IntStream.range(1, numChunks).mapToObj(chunk -> (Runnable) () -> {
      int start = chunk * CHUNK_SIZE;
      int end = Math.min(start + CHUNK_SIZE, n);
      int adjustment = chunkResults[chunk - 1];
      for (int i = start; i < end; i++) {
        input[i] = op.applyAsInt(adjustment, input[i]);
      }
    }).toList());
//    throw new UnsupportedOperationException();
  }
}
