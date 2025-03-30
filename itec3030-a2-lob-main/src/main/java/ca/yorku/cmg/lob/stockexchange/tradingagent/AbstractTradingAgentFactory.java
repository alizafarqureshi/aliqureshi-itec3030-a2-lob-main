package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Factory class for creating TradingAgent objects based on given specifications.
 */
public abstract class AbstractTradingAgentFactory {
    
    /**
     * Creates a TradingAgent object based on the specified type and strategy.
     *
     * @param type  "Institutional" for creating an Institutional TradingAgent, 
     *              "Retail" for creating a Retail TradingAgent.
     * @param style "Aggressive" for an aggressive trading strategy, 
     *              "Conservative" for a conservative trading strategy.
     * @param trader The {@link Trader} associated with the TradingAgent.
     * @param exchange Reference to the {@link StockExchange} where the TradingAgent will trade.
     * @param newsBoard Reference to the {@link NewsBoard} that the TradingAgent will receive news from.
     * @return A {@link TradingAgent} instance constructed based on the specified parameters.
     */
    public abstract TradingAgent createAgent(String type, String style, Trader trader, StockExchange exchange, NewsBoard newsBoard);
}