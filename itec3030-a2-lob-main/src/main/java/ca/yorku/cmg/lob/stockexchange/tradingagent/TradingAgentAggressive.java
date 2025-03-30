package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.orderbook.Ask;
import ca.yorku.cmg.lob.orderbook.Bid;
import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.BadNews;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.GoodNews;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.IOrder;

/**
 * An aggressive trading agent that reacts strongly to market news.
 */
public class TradingAgentAggressive extends TradingAgent {

    // Constants for order price multipliers
    private static final double GOOD_NEWS_PRICE_MULTIPLIER = 1.05;
    private static final double BAD_NEWS_PRICE_MULTIPLIER = 0.90;

    // Constants for order position multipliers
    private static final double GOOD_NEWS_POSITION_MULTIPLIER = 0.5;
    private static final double BAD_NEWS_POSITION_MULTIPLIER = 0.8;

    public TradingAgentAggressive(Trader t, StockExchange e, NewsBoard n) {
        super(t, e, n);
    }

    public TradingAgentAggressive(String string, Trader t, StockExchange e, NewsBoard n) {
        super(t, e, n);
        System.out.println("TradingAgentAggressive initialized with string: " + string);
    }

    @Override
    protected void actOnEvent(Event e, int pos, int price) {
        IOrder newOrder = null;

        if (e instanceof GoodNews) {
            newOrder = new Bid(
                this.trader,
                e.getSecurity(),
                (int) Math.round(price * GOOD_NEWS_PRICE_MULTIPLIER),
                (int) Math.round(pos * GOOD_NEWS_POSITION_MULTIPLIER),
                e.getTime()
            );
        } else if (e instanceof BadNews) {
            newOrder = new Ask(
                this.trader,
                e.getSecurity(),
                (int) Math.round(price * BAD_NEWS_PRICE_MULTIPLIER),
                (int) Math.round(pos * BAD_NEWS_POSITION_MULTIPLIER),
                e.getTime()
            );
        } else {
            System.out.println("Unknown event type: " + e.getClass().getSimpleName());
        }

        // Submit order only if it's valid
        if (newOrder != null) {
            this.exchange.submitOrder(newOrder, e.getTime());
        }
    }

    public void update(String news) {
        System.out.println(this.trader.getTitle() + " (Aggressive) reacts aggressively to: " + news);
    }

    @Override
    public void update(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
