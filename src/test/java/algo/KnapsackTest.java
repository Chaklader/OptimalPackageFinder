package algo;

import models.Product;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Chaklader on Feb, 2021
 */
class KnapsackTest {


    @Test
    public void testFindOptimalPackage_1() {

        // 7 : (1,1,€1) (2,3,€4) (3,4,€5) (4,5,€7)

        Product[] products = {

                new Product(1, 1, 1),
                new Product(2, 3, 4),
                new Product(3, 4, 5)
        };

        BitSet max = new Knapsack(products).findOptimalProductIndexes(7);
        String result = max.stream().map(index -> products[index].getId()).sorted().mapToObj(Objects::toString).collect(Collectors.joining(","));

        assertEquals("2,3", result);
    }

    @Test
    public void testFindOptimalPackage_2() {

        // 75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)

        Product[] products = {

                new Product(1, 85.31, 29),
                new Product(2, 14.55, 74),
                new Product(3, 3.98, 16),
                new Product(4, 26.24, 55),
                new Product(5, 63.69, 52),
                new Product(6, 76.25, 75),
                new Product(7, 60.02, 74),
                new Product(8, 93.18, 35),
                new Product(8, 89.95, 78)
        };

        BitSet max = new Knapsack(products).findOptimalProductIndexes(75);
        String result = max.stream().map(index -> products[index].getId()).sorted().mapToObj(Objects::toString).collect(Collectors.joining(","));

        assertEquals("2,7", result);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testFindOptimalEmptyProducts() {

        Product[] products = {};

        BitSet max = new Knapsack(products).findOptimalProductIndexes(32);

        String result = max.stream().map(index -> products[index].getId()).sorted().mapToObj(Objects::toString).collect(Collectors.joining(","));

        assertEquals(StringUtils.EMPTY, result);
    }

}