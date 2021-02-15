import algo.Strategy;
import exceptions.BaseException;
import models.Package;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Created by Chaklader on Feb, 2021
 */
@Command
public class OptimalPackageApp implements Runnable {


    @Option(names = {"-f", "--file"}, required = true, description = "Filename")
    private static String fileLoc;


    public static void main(String[] args) {

        CommandLine.run(new OptimalPackageApp(), System.err, args);
    }

    @Override
    public void run() {

        if (fileLoc == null || fileLoc.isEmpty()) {
            System.err.println("Invalid command arguments. One argument is required: the input file path.");
        }

        computation();
    }

    private void computation() {

        Strategy strategy = Strategy.KNAPSACK;

        try {
            Stream<String> linesStream = Files.lines(Path.of(fileLoc), StandardCharsets.UTF_8);

            io.vavr.collection.Stream.ofAll(linesStream)
                    .zipWithIndex()
                    .filter(lineWithNumber -> StringUtils.isNotBlank(lineWithNumber._1()))
                    .forEach(lineWithNumber -> executeProcessor(lineWithNumber._1(), lineWithNumber._2(), strategy));

        } catch (BaseException | IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void executeProcessor(String line, int lineNumber, Strategy strategy) {

        try {
            PackageOrganizer organizer = new PackageOrganizer(line, lineNumber);

            Optional<Package> optimalPackage = organizer.findOptimalPackage(strategy);
            OutputLine outputLine = new OutputLine(optimalPackage);

            System.out.println(outputLine);

        } catch (BaseException e) {

            System.out.println("-");
            System.err.println("Line " + lineNumber + " cannot be processed because :" + System.lineSeparator() + e.getMessage());
        }
    }
}
