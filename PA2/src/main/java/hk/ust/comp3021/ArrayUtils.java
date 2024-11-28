package hk.ust.comp3021;

import java.util.Arrays;
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

      pool.addTasks(
              IntStream.range(0, numChunks)
                      .mapToObj(chunk -> (Runnable) () -> {
                          int start = chunk * CHUNK_SIZE;
                          int end = Math.min((chunk + 1) * CHUNK_SIZE, input.length);
                          IntStream.range(start, end)
                                  .forEach(i -> output[i] = map.applyAsInt(input[i]));
                      })
                      .toList()
      );

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
    if (input.length == 0) return;
    int[] chunkResults = new int[(input.length + CHUNK_SIZE - 1) / CHUNK_SIZE];
    pool.addTasks(
            IntStream.range(0, chunkResults.length)
                    .mapToObj(chunk -> (Runnable) () -> {
                      IntStream.range(chunk * CHUNK_SIZE + 1,
                                      Math.min((chunk + 1) * CHUNK_SIZE, input.length))
                              .forEach(i -> input[i] = op.applyAsInt(input[i - 1], input[i]));
                      chunkResults[chunk] = input[Math.min((chunk + 1) * CHUNK_SIZE, input.length) - 1];
                    })
                    .toList());

    IntStream.range(1, chunkResults.length)
            .forEach(i -> chunkResults[i] = op.applyAsInt(chunkResults[i - 1], chunkResults[i]));

    if (chunkResults.length > 1) {
      pool.addTasks(
              IntStream.range(1, chunkResults.length)
                      .mapToObj(chunk -> (Runnable) () -> {
                        final int offset = chunkResults[chunk - 1];
                        IntStream.range(chunk * CHUNK_SIZE,
                                        Math.min((chunk + 1) * CHUNK_SIZE, input.length))
                                .forEach(i -> input[i] = op.applyAsInt(offset, input[i]));
                      })
                      .toList());
    }
//    throw new UnsupportedOperationException();
  }
}
