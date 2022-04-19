package brickset;

import repository.Repository;

import java.util.*;
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
     * Returns a Map object wrapping the summary of the used packaging types and their frequency among LEGO sets.
     *
     * @return a {@code Map<PackagingType, Long>} object wrapping the summary of the used packaging types
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

    /**
     * Returns whether each legoset has fewer pieces than five hundred.
     *
     * @return whether every legoset has fewer pieces than five hundred.
     */
    public boolean returnIfAllSetsHaveFewerPiecesThanFiveHundred() {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .allMatch(piece -> piece <= 500);
    }

    /**
     * Prints every distinct tag sorted to the console, that have no subthemes.
     */
    public void printAllSortedDistinctTagsThatHaveNoSubtheme() {
        getAll().stream()
                .filter(brickset -> brickset.getSubtheme() == null && brickset.getTags() != null)
                .flatMap(brickset ->  brickset.getTags().stream())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Returns the theme, that has the longest name.
     *
     * @return {@code Optional<String>} object, that should contain the theme with the longest name.
     */
    public Optional<String> getThemeWithTheLongestName() {
        return getAll().stream()
                .map(LegoSet::getTheme)
                .reduce((firstTheme, secondTheme) ->
                        firstTheme.length() > secondTheme.length() ? firstTheme : secondTheme);
    }

    /**
     * Returns a Map object, that contains a summary of the legosets' themes and their frequency.
     *
     * @return {@code Map<String, Long>} object wrapping how many legosets have the same theme.
     */
    public Map<String, Long> getNumberOfSetsForEachTheme() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getTheme, Collectors.counting()));
    }

    /**
     * Returns a Map object, that contains every theme and their distinct subthemes.
     *
     * @return {@code Map<String, Set<String>>} object wrapping the legosets' themes and their subthemes.
     */
    public Map<String, Set<String>> getMapOfThemesWithTheirSubthemes() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getTheme,
                        Collectors.mapping(LegoSet::getSubtheme,
                        Collectors.filtering(Objects::nonNull,
                        Collectors.toSet()))));
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        /* Previous homework
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
        */

        System.out.println("\nDo all sets have less pieces than five hundred?");
        System.out.println(repository.returnIfAllSetsHaveFewerPiecesThanFiveHundred());

        System.out.println("\nSorted, distinct tags of legosets, that have no subtheme:");
        repository.printAllSortedDistinctTagsThatHaveNoSubtheme();

        System.out.println("\nGet the longest theme name:");
        repository.getThemeWithTheLongestName().ifPresent(System.out::println);

        System.out.println("\nGet number of sets for each theme:");
        System.out.println(repository.getNumberOfSetsForEachTheme());

        System.out.println("\nGet each theme with their distinct subthemes:");
        System.out.println(repository.getMapOfThemesWithTheirSubthemes());
    }
}
