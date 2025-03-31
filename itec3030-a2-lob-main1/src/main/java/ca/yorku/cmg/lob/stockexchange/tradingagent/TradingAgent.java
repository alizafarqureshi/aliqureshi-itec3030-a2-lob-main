package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * A trading agent that receives news and reacts by submitting ask or bid orders.
 */
public abstract class TradingAgent {
    protected Trader trader;
    protected StockExchange exchange;
    protected NewsBoard newsBoard;

    /**
     * Constructor
     *
     * @param trader    The {@linkplain Trader} object associated with the agent.
     * @param exchange  The {@linkplain StockExchange} object where the agent trades.
     * @param newsBoard The {@linkplain NewsBoard} object that generates news events.
     */
    public TradingAgent(Trader trader, StockExchange exchange, NewsBoard newsBoard) {
        this.trader = trader;
        this.exchange = exchange;
        this.newsBoard = newsBoard;
    }

    /**
     * Method called as time advances to {@code time}. 
     * The TradingAgent will poll the NewsBoard for events at this time.
     *
     * @param time The time to advance to.
     */
    public void timeAdvancedTo(long time) {
        pollForEvents(time);
    }

    /**
     * Examines if an event is relevant for the agent, i.e., if the agent has a position on it.
     *
     * @param event The {@linkplain Event} to examine.
     */
    private void examineEvent(Event event) {
        String ticker = event.getSecurity().getTicker();

        // Get trader's position for the security, defaulting to 0 if not found
        int positionInSecurity = exchange.getAccounts()
                                         .getTraderAccount(trader)
                                         .getPosition(ticker);

        // Act only if the trader has a position in this security
        if (positionInSecurity > 0) {
            int currentPrice = exchange.getPrice(ticker);
            actOnEvent(event, positionInSecurity, currentPrice);
        }
    }

    /**
     * Polls the {@linkplain NewsBoard} for an event at time {@code time}.
     * If an event exists, it will be sent for examination.
     *
     * @param time The time for which to poll for an event (unit: days).
     */
    private void pollForEvents(long time) {
        Event event = newsBoard.getEventAt(time); // Retrieve a single event

        if (event != null) {
            examineEvent(event);
        }
    }

    /**
     * Acts in response to a news {@linkplain Event}. 
     * The exact reaction strategy must be implemented by specialized agents.
     *
     * @param event    The {@linkplain Event} in question.
     * @param position The number of units of the relevant security held by the trader.
     * @param price    The current price of the relevant security.
     */
    protected abstract void actOnEvent(Event event, int position, int price);

    public abstract void update(Event event);
}
