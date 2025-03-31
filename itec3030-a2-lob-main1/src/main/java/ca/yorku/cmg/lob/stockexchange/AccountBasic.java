package ca.yorku.cmg.lob.stockexchange;

import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.ITrade;

/**
 * A basic exchange {@linkplain Account}.
 * This type of account applies a fixed fee for all trades.
 */
public class AccountBasic extends Account {

    private static final int FIXED_FEE = 450;

    /**
     * Constructs an {@linkplain AccountBasic} with an associated {@linkplain Trader}
     * and an initial balance.
     *
     * @param trader      the {@linkplain Trader} associated with this account
     * @param initBalance the initial balance of the account
     */
    public AccountBasic(Trader trader, long initBalance) {
        super(trader, initBalance);
    }

    /**
     * Returns the fixed fee for all trades.
     *
     * @param trade the {@linkplain ITrade} for which the fee is applied (unused in this implementation)
     * @return the fixed fee amount, which is always {@value #FIXED_FEE}
     */
    @Override
    public int getFee(ITrade trade) {
        return FIXED_FEE;
    }
}
