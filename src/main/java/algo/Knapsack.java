package algo;

import models.Key;
import models.Product;
import models.Value;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Chaklader on Feb, 2021
 */
public class Knapsack {


    private final Product[] products;

    private final Map<Key, Value> customStorage = new HashMap<>();


    public Knapsack(Product... products) {

        this.products = products;
    }


    /**
     * this method takes the maximum weight and will return the indexes
     * of the products
     *
     * @param packageWeight
     * @return
     */
    public BitSet findOptimalProductIndexes(double packageWeight) {

        Key initializationKey = new Key(0, packageWeight);
        Value optimalValue = findOptimalValue(initializationKey);

        return optimalValue.getIndexes();
    }


    private Value findOptimalValue(Key key) {

        if (customStorage.containsKey(key)) {
            return customStorage.get(key);
        }

        Value value = findOptimalValueByKey(key);
        customStorage.put(key, value);

        return value;
    }


    /**
     * recursively find the optimal "Value" from the highest  (N-1)
     * to 0 index of the products using the Knapsack algorithm
     *
     * @param key
     * @return
     */
    private Value findOptimalValueByKey(Key key) {

        if (key.getPosition() == products.length) {

            Value value = new Value(products.length);
            return value;
        }

        Key nextKey = new Key(key.getPosition() + 1, key.getWeight());
        Value excludedOptimalValue = findOptimalValue(nextKey);

        Product product = products[key.getPosition()];
        double subtractedWeight = key.getWeight() - product.getWeight();

        if (subtractedWeight < 0) {

            return excludedOptimalValue;
        }

        Key keyWithUpdatedWeight = new Key(key.getPosition() + 1, subtractedWeight);

        Value includedOptimalValue = findOptimalValue(keyWithUpdatedWeight)
                                        .addNewProductData(product.getWeight(), product.getPrice(), key.getPosition());

        Value optimizedValue = Value.getMaximumValue(includedOptimalValue, excludedOptimalValue);
        return optimizedValue;
    }

}
