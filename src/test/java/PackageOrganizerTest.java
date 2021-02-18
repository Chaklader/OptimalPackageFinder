import algo.Strategy;
import com.google.common.collect.Sets;
import exceptions.ParsingException;
import models.Package;
import models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static util.Parameters.*;


/**
 * Created by Chaklader on Feb, 2021
 */
class PackageOrganizerTest {


    @Test
    public void testValidInputLine() {

        PackageOrganizer organizer = new PackageOrganizer("81 : (1,53.38,€45) (2,88.62,€98)", 1);

        assertEquals(81.0, organizer.getMaxWeight());

        Set<Product> expectedProducts = Sets.newHashSet(
                new Product(1, 53.38, 45.0),
                new Product(2, 88.62, 98.0)
        );

        assertEquals(expectedProducts, organizer.getProducts());
    }

    @Test
    public void testWrongDelimiters1() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 , (1,53.38,€45)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongDelimiters2() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : ,1,53.38,€45)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongDelimiters3() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1(53.38,€45)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongDelimiters4() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,53.38(€45)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongDelimiters5() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,53.38,(45)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongDelimiters6() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,53.38,€45X", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @Test
    public void testWrongMaxWeight() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("XXX : (1,53.38,€45)", 1)
        );

        assertEquals(MAX_WEIGHT, parsingEx.getTokenName());
    }

    @Test
    public void testWrongProductNumber() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (XXX,53.38,€45)", 1)
        );

        assertEquals(PRODUCT_NUMBER, parsingEx.getTokenName());
    }

    @Test
    public void testWrongProductWeight() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,XXX,€45)", 1)
        );

        assertEquals(PRODUCT_WEIGHT, parsingEx.getTokenName());
    }

    @Test
    public void testWrongProductPrice() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,53.38,€XXX)", 1)
        );

        assertEquals(PRODUCT_PRICE, parsingEx.getTokenName());
    }

    @Test
    public void testMissingProductPrice() {

        ParsingException parsingEx = assertThrows(ParsingException.class,
                () -> new PackageOrganizer("81 : (1,53.38,€)", 1)
        );

        assertEquals(LINE_STRUCTURE, parsingEx.getTokenName());
    }

    @ParameterizedTest
    @EnumSource(Strategy.class)
    public void testFindPackage1() {

        PackageOrganizer organizer = new PackageOrganizer(81.0,

                new Product(1, 53.38, 45.0),
                new Product(2, 88.62, 98.0),
                new Product(3, 78.48, 3.0),
                new Product(4, 72.30, 76.0),
                new Product(5, 30.18, 9.0),
                new Product(6, 46.34, 48.0)
        );

        Strategy strategy = Strategy.KNAPSACK;
        Optional<Package> aPackage = organizer.findOptimalPackage(strategy);

        assertTrue(aPackage.isPresent());
        Set<Product> expectedProducts = Sets.newHashSet(
                new Product(4, 72.30, 76.0)
        );

        assertEquals(expectedProducts, aPackage.get().getProducts());
    }

    @ParameterizedTest
    @EnumSource(Strategy.class)
    public void testFindPackage2() {

        PackageOrganizer organizer = new PackageOrganizer(8.0,
                new Product(1, 15.3, 34.0)
        );

        Strategy strategy = Strategy.KNAPSACK;
        Optional<Package> aPackage = organizer.findOptimalPackage(strategy);

        assertTrue(aPackage.isPresent());

        Set<Product> expectedProducts = Sets.newHashSet();
        assertEquals(expectedProducts, aPackage.get().getProducts());
    }

    @ParameterizedTest
    @EnumSource(Strategy.class)
    public void testFindPackage3() {

        PackageOrganizer organizer = new PackageOrganizer(

                75.0,

                new Product(1, 85.31, 29.0),
                new Product(2, 14.55, 74.0),
                new Product(3, 3.98, 16.0),
                new Product(4, 26.24, 55.0),
                new Product(5, 63.69, 52.0),
                new Product(6, 76.25, 75.0),
                new Product(7, 60.02, 74.0),
                new Product(8, 93.18, 35.0),
                new Product(9, 89.95, 78.0)
        );

        Optional<Package> aPackage = organizer.findOptimalPackage(Strategy.KNAPSACK);

        assertTrue(aPackage.isPresent());

        Set<Product> expectedProducts = Sets.newHashSet(
                new Product(2, 14.55, 74.0),
                new Product(7, 60.02, 74.0)
        );

        assertEquals(expectedProducts, aPackage.get().getProducts());
    }

    @ParameterizedTest
    @EnumSource(Strategy.class)
    public void testFindPackage4() {

        PackageOrganizer organizer = new PackageOrganizer(

                56.0,

                new Product(1, 90.72, 13.0),
                new Product(2, 33.80, 40.0),
                new Product(3, 43.15, 10.0),
                new Product(4, 37.97, 16.0),
                new Product(5, 46.81, 36.0),
                new Product(6, 48.77, 79.0),
                new Product(7, 81.80, 45.0),
                new Product(8, 19.36, 79.0),
                new Product(9, 6.76, 64.0)
        );

        Optional<Package> aPackage = organizer.findOptimalPackage(Strategy.KNAPSACK);
        assertTrue(aPackage.isPresent());

        Set<Product> expectedProducts = Sets.newHashSet(
                new Product(8, 19.36, 79.0),
                new Product(9, 6.76, 64.0)
        );

        assertEquals(expectedProducts, aPackage.get().getProducts());
    }

}