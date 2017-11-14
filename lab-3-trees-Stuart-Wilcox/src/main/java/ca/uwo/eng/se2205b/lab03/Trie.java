package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.SortedSet;

/**
 * Describes a Trie or prefix tree
 */
public interface Trie {

    /**
     * Get the number of words in the Trie
     * @return Number of words
     */
    int size();

    /**
     * True if there is no elements in the Trie.
     * @return {@code true} if there are no words
     */
    boolean isEmpty();

    /**
     * Places a word into the Trie.
     *
     * @param word Non-null or empty word to insert into the Trie.
     * @return {@code true} if the word was added
     */
    boolean put(String word);
    
    /**
     * Places all of the words into the Trie.
     *
     * The simplest algorithm for this is done in {@code O(n^2)}, however, because the incoming data is *sorted* you can
     * get an {@code O(nlogn)} solution. It's just quite tough.
     *
     * @param words Words to put into the Trie
     * @return Number of words inserted
     */
    int putAll(SortedSet<? extends String> words);

    /**
     * Gets at most {@code N} valid words based on the {@code prefix}, but it might be empty still.
     * @param prefix Prefix of characters to look-up, must not be empty or null
     * @param N Maximum number of prefixes to look-up, {@code -1} returns all.
     * @return Unique set of words
     *
     * @throws IllegalArgumentException if the {@code prefix} is {@code null} or empty
     */
    SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException;

    /**
     * {@code true} if a word is contained within the Trie
     * @param word Word to look for
     * @return {@code true if the word exists}
     *
     * @throws IllegalArgumentException if the word is {@code null} or empty
     */
    boolean contains(@Nonnull String word) throws IllegalArgumentException;
}
