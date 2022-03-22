package brickset;

import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * Prints each LEGO set names, in which the first and the last letters are the same, ignoring case to the console.
     */
    public void printLegoSetsWithSameBeginningAndEnding() {
        getAll().stream()
                .map(LegoSet::getName)
                .filter(name -> name.toLowerCase().charAt(0) == name.charAt(name.length()-1))
                .forEach(System.out::println);
    }

    /**
     * Prints each LEGO set names, which start with the given string specified, ignoring case to the console.
     *
     * @param beginning the beginning of the needed LEGO set names.
     */
    public void printLegoSetsStartingWithGivenString(String beginning) {
        getAll().stream()
                .map(LegoSet::getName)
                .filter(name -> name.toLowerCase().startsWith(beginning.toLowerCase()))
                .forEach(System.out::println);
    }

    /**
     * Returns a List of the LEGO set numbers, which have less than or equal tags, than the specified
     * number of tags.
     *
     * @param numberOfMaxTags the maximum number of tags.
     * @return a {@code List<String>} of the LEGO set numbers, which have less than or equal tags, than the specified
     * number of tags.
     */
    public List<String> getNumbersWithLessThanGivenTags(int numberOfMaxTags) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().size() <= numberOfMaxTags)
                .map(LegoSet::getNumber)
                .collect(Collectors.toList());
    }

    /**
     * Returns a Map object wrapping a summary of the used packaging types and their frequency among LEGO sets.
     *
     * @return a {@code Map<PackagingType, Long>} object wrapping a summary of the used packaging types
     * and their frequency among LEGO sets.
     */
    public Map<PackagingType, Long> getPackagingTypeSummary() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getPackagingType, Collectors.counting()));
    }

    /**
     * Prints the sum of all LEGO set pieces to the console.
     */
    public void printSumOfLegoPieces() {
        getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        System.out.println("Lego names starting with the String 'lava':");
        repository.printLegoSetsStartingWithGivenString("lava");

        System.out.println("\nLego names with the same character at the beginning and the end:");
        repository.printLegoSetsWithSameBeginningAndEnding();

        System.out.println("\nLego numbers with less than 5 tags:");
        System.out.println(repository.getNumbersWithLessThanGivenTags(5));

        System.out.println("\nA summary of the packaging types of legos:");
        System.out.println(repository.getPackagingTypeSummary());

        System.out.println("\nThe sum of all lego pieces:");
        repository.printSumOfLegoPieces();
    }
}
