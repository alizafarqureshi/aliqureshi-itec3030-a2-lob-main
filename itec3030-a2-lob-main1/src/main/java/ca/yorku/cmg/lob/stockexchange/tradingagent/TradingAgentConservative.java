package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.orderbook.Bid;
import ca.yorku.cmg.lob.orderbook.Ask;
import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.BadNews;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.GoodNews;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.IOrder;

/**
 * A conservative trading agent that reacts cautiously to news events.
 */
public class TradingAgentConservative extends TradingAgent {

    private static final double GOOD_NEWS_PRICE_MULTIPLIER = 1.05;
    private static final double BAD_NEWS_PRICE_MULTIPLIER = 0.95;
    private static final double POSITION_MULTIPLIER = 0.2;

    public TradingAgentConservative(Trader trader, StockExchange exchange, NewsBoard newsBoard) {
        super(trader, exchange, newsBoard);
    }

    public TradingAgentConservative(String string, Trader trader, StockExchange exchange, NewsBoard newsBoard) {
        super(trader, exchange, newsBoard);
        // Additional initialization can be added here if needed
    }

    @Override
    protected void actOnEvent(Event event, int position, int price) {
        IOrder newOrder = null;

        // Determine the type of event and create appropriate orders
        if (event instanceof GoodNews) {
            newOrder = new Bid(
                this.trader, 
                event.getSecurity(),
                (int) Math.round(price * GOOD_NEWS_PRICE_MULTIPLIER), 
                (int) Math.round(position * POSITION_MULTIPLIER), 
                event.getTime()
            );
        } else if (event instanceof BadNews) {
            newOrder = new Ask(
                this.trader, 
                event.getSecurity(),
                (int) Math.round(price * BAD_NEWS_PRICE_MULTIPLIER), 
                (int) Math.round(position * POSITION_MULTIPLIER), 
                event.getTime()
            );
        } else {
            System.err.println("Warning: Unrecognized event type: " + event.getClass().getSimpleName());
            return;  // Exit early if event type is unknown
        }

        // Submit the order if created successfully
        if (newOrder != null) {
            this.exchange.submitOrder(newOrder, event.getTime());
        }
    }

    public void update(String news) {
        System.out.println(this.trader.getTitle() + " (Conservative) reacts cautiously to: " + news);
    }

    @Override
    public void update(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}