package util;

import java.util.regex.Pattern;

/**
 * Created by Chaklader on Feb, 2021
 */
public class Parameters {


    public static final Pattern DELIMITER = Pattern.compile("[ :(),€]+");

    public static final Pattern LINE_PATTERN =
            Pattern.compile("^([^ :(),€]+) +:( +\\(([^ :(),€]+),([^ :(),€]+),€([^ :(),€]+)\\))+$");



    public static final String NO_COMBINATION_MATCH = "-";


    public static final String LINE_STRUCTURE = "line structure";

    public static final String MAX_WEIGHT = "max weight";

    public static final String MAX_PRODUCTS = "max products";

    public static final String PRODUCT_NUMBER = "product number";

    public static final String PRODUCT_WEIGHT = "product weight";

    public static final String PRODUCT_PRICE = "product price";

}
