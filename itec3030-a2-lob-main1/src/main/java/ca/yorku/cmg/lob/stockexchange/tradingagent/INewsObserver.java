package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.Event;

/**
 * Interface to be implemented by objects that wish to receive events from a {@linkplain NewsBoard}.
 */
public interface INewsObserver {
    /**
     * Updates the observer when a new event occurs.
     * 
     * @param event The {@linkplain Event} received from the NewsBoard.
     */
    void update(Event event);
}
