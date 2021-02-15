import algo.Knapsack;
import algo.Strategy;
import com.google.common.collect.Sets;
import exceptions.ParsingException;
import exceptions.ValidationException;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;
import models.Package;
import models.Product;
import validator.Validator;

import java.util.BitSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static util.Parameters.*;


/**
 * Created by Chaklader on Feb, 2021
 */
@Getter
@Setter
public class PackageOrganizer {


    private final int lineNum;

    private double maxWeight;

    private Set<Product> products;


    PackageOrganizer(double maxWeight, Set<Product> products) throws ValidationException {

        this.maxWeight = maxWeight;
        this.products = products;
        this.lineNum = 1;
    }

    PackageOrganizer(double maxWeight, Product... products) throws ValidationException {

        this(maxWeight, Sets.newHashSet(products));
    }

    public PackageOrganizer(String lineData, int lineNum) throws ParsingException, ValidationException {

        this.lineNum = lineNum;

        readTokens(lineData);

        Validator validator = new Validator(lineNum, maxWeight, products);
        validator.validateTokens();
    }


    /**
     * this method will read the input file line by line and validate them for the desired pattern.
     * If the input line passed the validation, it will store the data for the maximum weight and
     * all the products for that respective line. Otherwise, it will throw ParsingException
     *
     * @param line
     * @throws ParsingException
     */
    private void readTokens(String line) throws ParsingException {

        boolean isMatched = LINE_PATTERN.matcher(line).matches();

        if (!isMatched) {

            throw new ParsingException(
                lineNum,
                LINE_STRUCTURE,
                line);
        }

        Scanner scanner = new Scanner(line).useDelimiter(DELIMITER);

        this.maxWeight = getTokenValueOrElseThrow(scanner::nextDouble, MAX_WEIGHT, scanner);
        this.products = Sets.newHashSet();

        do {

            int id = getTokenValueOrElseThrow(scanner::nextInt, PRODUCT_NUMBER, scanner);
            double weight = getTokenValueOrElseThrow(scanner::nextDouble, PRODUCT_WEIGHT, scanner);
            double price = getTokenValueOrElseThrow(scanner::nextDouble, PRODUCT_PRICE, scanner);

            Product product = new Product(id, weight, price);
            products.add(product);
        }

        while (scanner.hasNext());
    }


    private <T> T getTokenValueOrElseThrow(Supplier<T> supplier, String tokenName, Scanner scanner) throws ParsingException {

        return Try.ofSupplier(supplier).getOrElseThrow(

            () -> new ParsingException(
                lineNum,
                tokenName,
                scanner.hasNext() ? scanner.next() : "<EOL>"));
    }


    /**
     * this method will take the strategy to follow and execute the computation
     * based on that.
     *
     * @param strategy
     * @return
     */
    public Optional<Package> findOptimalPackage(Strategy strategy) {


        switch (strategy) {

            case KNAPSACK -> {

                Optional<Package> optionalPackage = findOptimalPackageWithKnapsackAlgo();
                return optionalPackage;
            }

            case COMBINATORICS_STRATEGY_1 -> {

                System.out.println("This strategy is in the process of development and still not supported");
                return Optional.empty();
            }

            case COMBINATORICS_STRATEGY_2 -> {

                System.out.println("This strategy is in the process of development and still not supported");
                return Optional.empty();
            }

            default -> System.out.println("We don't have any reference for this strategy, sorry!!");
        }

        return Optional.empty();
    }


    /**
     * this method will find the optimal package using the Knapsack algorithm
     */
    private Optional<Package> findOptimalPackageWithKnapsackAlgo() {

        Product[] productsArray = products.toArray(new Product[0]);
        Knapsack knapsack = new Knapsack(productsArray);

        BitSet max = knapsack.findOptimalProductIndexes(getMaxWeight());

        Set<Product> packageProducts = max.stream().mapToObj(index -> productsArray[index]).collect(Collectors.toSet());

        return Optional.of(new Package(packageProducts));
    }


}
