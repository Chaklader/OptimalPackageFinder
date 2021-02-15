package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Chaklader on Feb, 2021
 */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {


    private final int id;

    private final double weight;

    private final double price;
}
