import models.Package;
import models.Product;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static util.Parameters.NO_COMBINATION_MATCH;


@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OutputLine {


    private final Optional<Package> optionalPackage;


    @Override
    public String toString() {

        if (optionalPackage.isPresent()) {

            Set<Product> products = (optionalPackage.get()).getProducts();

            if (products.isEmpty()) {

                return NO_COMBINATION_MATCH;
            }

            var collect = products.stream().map(Product::getId).sorted().map(Object::toString).collect(Collectors.joining(","));
            return collect;
        }

        return NO_COMBINATION_MATCH;
    }

    public OutputLine(Optional<Package> optionalPackage) {

        this.optionalPackage = optionalPackage;
    }
}
