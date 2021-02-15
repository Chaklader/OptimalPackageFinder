package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Chaklader on Feb, 2021
 */
@EqualsAndHashCode
@Getter
@ToString
@AllArgsConstructor
public class Key {

    private final int position;

    private final double weight;
}
