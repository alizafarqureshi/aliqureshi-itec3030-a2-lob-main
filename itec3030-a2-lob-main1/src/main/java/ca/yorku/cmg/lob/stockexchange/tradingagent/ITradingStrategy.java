package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.Event;

/**
 * Interface defining the strategy for reacting to trading events.
 */
public interface ITradingStrategy {
    /**
     * Defines the action to be taken in response to a market event.
     *
     * @param event       The market event that occurred.
     * @param position    The number of units of the relevant security held by the trader.
     * @param currentPrice The current price of the relevant security.
     */
    void actOnEvent(Event event, int position, int currentPrice);
}
