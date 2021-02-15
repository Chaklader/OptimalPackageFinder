import com.google.common.collect.Sets;
import models.Package;
import models.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Parameters.NO_COMBINATION_MATCH;


/**
 * Created by Chaklader on Feb, 2021
 */
class OutputLineTest {


    @Test
    public void testTwoProducts() {

        Product aProduct = new Product(2, 10.0, 20.0);
        Product bProduct = new Product(1, 10.0, 20.0);

        Package aPackage = new Package(Sets.newHashSet(aProduct, bProduct));


        OutputLine outputLine = new OutputLine(Optional.of(aPackage));

        assertEquals("1,2", outputLine.toString());
    }

    @Test
    public void testNoProduct() {

        OutputLine outputLine = new OutputLine(
                Optional.of(new Package(Sets.newHashSet()))
        );

        assertEquals(NO_COMBINATION_MATCH, outputLine.toString());
    }

    @Test
    public void testNoPackage() {

        OutputLine outputLine = new OutputLine(
                Optional.empty()
        );

        assertEquals(NO_COMBINATION_MATCH, outputLine.toString());
    }
}