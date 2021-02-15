package validator;

import com.google.common.collect.ImmutableList;
import exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.Product;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.Parameters.*;


/**
 * Created by Chaklader on Feb, 2021
 */

@Getter
@Setter
@AllArgsConstructor
public class Validator {


    private final int lineNum;

    private double packageWeight;

    private Set<Product> products;


    /**
     * this method will combine both the primary and products related errors
     * and throw validation exception if any.
     *
     * @throws ValidationException
     */
    public void validateTokens() throws ValidationException {

        Stream<String> primaryErrors = validatePrimaryConstraints();

        Stream<String> productsErrors = getProducts().stream()
                .flatMap(this::validateProductConstraints);

        String errors = Stream.concat(primaryErrors, productsErrors).collect(Collectors.joining(System.lineSeparator()));

        if (errors.isEmpty()) {
            return;
        }

        throw new ValidationException(errors, lineNum);
    }

    /*
     * this method ensures that the maximum weight that a package can hold must be <= 100
     * and there may be up to 15 items you can to choose from. If this is not maintained,
     * the method will return custom error message.
     * */
    private Stream<String> validatePrimaryConstraints() {

        return ImmutableList.of(
                validateToken(getPackageWeight() <= 100.0, MAX_WEIGHT, getPackageWeight()),
                validateToken(getProducts().size() <= 15, MAX_PRODUCTS, getProducts().size())
        ).stream().filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * This method ensures that maximum weight of a product should be <= 100
     * and the maximum cost of an item should be <= €100. If this is not maintained,
     * the method will return custom error message.
     *
     * @param product
     * @return
     */
    private Stream<String> validateProductConstraints(Product product) {

        return ImmutableList.of(
                validateToken(product.getWeight() <= 100.0, PRODUCT_WEIGHT, product.getWeight()),
                validateToken(product.getPrice() <= 100.0, PRODUCT_PRICE, product.getPrice())
        )
                .stream().filter(Optional::isPresent)
                .map(Optional::get);
    }


    private Optional<String> validateToken(boolean expression, String tokenName, Object tokenValue) {

        if (expression) {
            return Optional.empty();
        }

        String s = "On line " + lineNum + " the '" + tokenName + "' has an invalid value = '" + tokenValue + "'.";
        return Optional.of(s);
    }
}