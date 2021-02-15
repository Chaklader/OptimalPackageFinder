package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.BitSet;
import java.util.Comparator;

/**
 * Created by Chaklader on Feb, 2021
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Value {


    private final double weight;

    private final double price;

    private final BitSet indexes;


    public Value(int length) {

        this.weight = 0;
        this.price = 0;
        this.indexes = new BitSet(length);
    }

    /**
     * This method will find the "Value" with the highest price. If there are multiple
     * "Value" with same price, find the "Value" with the smallest weight
     */
    private static final Comparator<Value> OPTIMAL_VALUE =

        Comparator.comparingDouble(Value::getPrice)
            .thenComparing(
                Comparator.comparingDouble(Value::getWeight).reversed()
            );

    public Value addNewProductData(double weight, double price, int index) {

        BitSet clonedIndexes = (BitSet) indexes.clone();
        clonedIndexes.set(index);

        Value updatedValue = new Value(this.weight + weight, this.price + price, clonedIndexes);
        return updatedValue;
    }

    /*
     * This method will take 2 "Value" and return the optimal value
     * based on the definition written in the "OPTIMAL_VALUE" comparator
     * */
    public static Value getMaximumValue(Value v1, Value v2) {

        if (OPTIMAL_VALUE.compare(v1, v2) < 0) {
            return v2;
        }

        return v1;
    }

}
