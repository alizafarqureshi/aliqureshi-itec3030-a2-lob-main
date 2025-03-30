package ca.yorku.cmg.lob.stockexchange;

import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.ITrade;

/**
 * Abstract class representing a financial account associated with a {@linkplain ca.yorku.cmg.lob.trader.Trader}.
 * Manages the account's balance, fee application, and positions in a {@linkplain ca.yorku.cmg.lob.exchange.PositionBook}.
 */
public abstract class Account {

    /** The {@linkplain ca.yorku.cmg.lob.trader.Trader} associated with this account. */
    private final Trader trader;

    /** The {@linkplain ca.yorku.cmg.lob.exchange.PositionBook} managing the positions for this account. */
    private final PositionBook book;

    /** The current balance of the account in monetary units. */
    private long balance;

    /**
     * Constructs an {@linkplain Account} with an associated {@linkplain Trader} and an initial balance.
     *
     * @param trader      the {@linkplain Trader} associated with this account
     * @param initBalance the initial balance of the account
     */
    protected Account(Trader trader, long initBalance) {
        this.trader = trader;
        this.balance = initBalance;
        this.book = new PositionBook();
    }

    /**
     * Returns the {@linkplain Trader} associated with this account.
     *
     * @return the {@linkplain Trader} associated with this account
     */
    public Trader getTrader() {
        return trader;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the current balance
     */
    public long getBalance() {
        return balance;
    }

    /**
     * Adds a specified amount to the account balance.
     *
     * @param amount the amount to add to the balance
     */
    public void addMoney(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /**
     * Withdraws a specified amount from the account balance, if sufficient funds are available.
     *
     * @param amount the amount to withdraw
     */
    public void withdrawMoney(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        }
    }

    /**
     * Applies a fee associated with a {@linkplain ITrade} to the account balance.
     *
     * @param trade the {@linkplain ITrade} for which the fee is applied
     */
    public void applyFee(ITrade trade) {
        int fee = this.getFee(trade);
        if (fee > 0) {
            balance -= fee;
        }
    }

    /**
     * Returns the fee associated with a given {@linkplain ITrade}.
     * This method must be implemented by subclasses.
     *
     * @param trade the {@linkplain ITrade} for which the fee is calculated
     * @return the fee amount
     */
    public abstract int getFee(ITrade trade);

    /**
     * Updates the quantity (number of units) of a specific position in the {@linkplain PositionBook}.
     *
     * @param ticker    the ticker symbol of the position
     * @param newQty the new quantity of the position (number of units) 
     */
    public void updatePosition(String ticker, int newQty) {
        if (book != null) {
            book.updatePosition(ticker, newQty);
        }
    }

    /**
     * Retrieves the current quantity (number of units) of a specific position from the {@linkplain PositionBook}.
     *
     * @param ticker the ticker symbol of the position
     * @return the quantity of the position (number of units) 
     */
    public int getPosition(String ticker) {
        return book != null ? book.getPosition(ticker) : 0;
    }

    /**
     * Adds a specified quantity to a position in the {@linkplain PositionBook}.
     *
     * @param ticker       the ticker symbol of the position
     * @param addedQty  the quantity to add
     */
    public void addToPosition(String ticker, int addedQty) {
        if (book != null) {
            book.addToPosition(ticker, addedQty);
        }
    }

    /**
     * Deducts a specified quantity from a position in the {@linkplain PositionBook}.
     *
     * @param ticker    the ticker symbol of the position
     * @param deductedQty the quantity to deduct
     */
    public void deductFromPosition(String ticker, int deductedQty) {
        if (book != null) {
            book.deductFromPosition(ticker, deductedQty);
        }
    }
}
