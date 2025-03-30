package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;

public class ObserverPatternTest {
    public static void main(String[] args) {
        NewsBoard newsBoard = new NewsBoard(null);

        TradingAgent agent1 = new TradingAgentAggressive("Agent A", null, null, newsBoard);
        TradingAgent agent2 = new TradingAgentConservative("Agent B", null, null, newsBoard);

        newsBoard.registerObserver(agent1);
        newsBoard.registerObserver(agent2);

        newsBoard.publishNews("Stock prices surged!");
        newsBoard.publishNews("Economic downturn ahead!");

        newsBoard.removeObserver(agent1);

        newsBoard.publishNews("Tech sector booming!");
    }
}

