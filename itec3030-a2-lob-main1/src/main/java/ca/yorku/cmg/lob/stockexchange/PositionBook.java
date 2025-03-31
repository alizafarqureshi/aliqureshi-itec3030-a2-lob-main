package ca.yorku.cmg.lob.stockexchange;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that represents a position book, which tracks the number of shares of various securities owned.
 * Positions are stored in a map with the security ticker as the key and the number of shares owned as the value.
 */
public class PositionBook {
    private final Map<String, Integer> positions = new ConcurrentHashMap<>();

    /**
     * Updates position in a security (number of security shares owned) to a given quantity.
     *
     * @param tkr      The ticker of the security.
     * @param quantity The new amount owned by the account.
     */
    public void updatePosition(final String tkr, final int quantity) {
        positions.put(tkr, quantity);
    }

    /**
     * Gets the number of shares in a security owned.
     *
     * @param tkr The ticker of the security.
     * @return The quantity of shares in the given security. It is 0 if the security is not part of the book.
     */
    public int getPosition(final String tkr) {
        return positions.getOrDefault(tkr, 0);
    }

    /**
     * Adds the specified quantity to the current position of a security.
     * If the security does not already exist in the book, it creates a new entry with the specified quantity.
     *
     * @param tkr      The ticker symbol of the security.
     * @param addedQty The quantity of shares to be added to the current position.
     */
    public void addToPosition(final String tkr, final int addedQty) {
        positions.put(tkr, positions.getOrDefault(tkr, 0) + addedQty);
    }

    /**
     * Deducts the specified quantity from the current position of a security.
     * If the security does not exist or if there are insufficient shares to remove, an exception is thrown.
     *
     * @param tkr       The ticker symbol of the security.
     * @param removeQty The quantity of shares to be removed from the current position.
     * @throws IllegalArgumentException if the security does not exist or the quantity to remove exceeds the current position.
     */
    public void deductFromPosition(final String tkr, final int removeQty) {
        int currPos = positions.getOrDefault(tkr, 0);

        if (currPos < removeQty) {
            throw new IllegalArgumentException("Error in PositionBook#deductFromPosition: Insufficient shares of " + tkr);
        }

        positions.put(tkr, currPos - removeQty);
    }
}
