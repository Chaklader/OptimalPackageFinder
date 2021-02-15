package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by Chaklader on Feb, 2021
 */
@Getter
@AllArgsConstructor
@ToString
public class Package {


    private final Set<Product> products;


    protected double getTotalPriceForPackage() {

        return products.stream().mapToDouble(Product::getPrice).reduce(Double::sum)
                   .orElse(0.0);
    }

    protected double getTotalWeightForPackage() {

        return products.stream().mapToDouble(Product::getWeight).reduce(Double::sum)
                   .orElse(0.0);
    }


    /*
     * This comparator definition used in case the total cost the of the packaged items
     * is the same for two sets of items, we take the set that has a lower total weight.
     *
     * */
    public static final Comparator<Package> OPTIMAL_PACKAGE =

        Comparator.comparingDouble(Package::getTotalPriceForPackage)
            .thenComparing
                 (Comparator.comparingDouble(Package::getTotalWeightForPackage).reversed());

}
