package ca.yorku.cmg.lob.stockexchange;

import java.util.ArrayList;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * A class that manages a list of {@linkplain Account} objects.
 * Provides functionality for adding accounts, retrieving traders by ID,
 * finding accounts associated with specific traders, and printing balances for debugging.
 */
public class AccountsList {
    private final ArrayList<Account> accounts = new ArrayList<>();

    /**
     * Adds an {@linkplain Account} to the list.
     *
     * @param account the {@linkplain Account} to be added
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Retrieves a {@linkplain Trader} object by its ID.
     *
     * @param traderId The ID of the trader (as it appears in files and lists)
     * @return A {@linkplain Trader} object matching the ID, or {@code null} if none was found.
     */
    public Trader getTraderByID(int traderId) {
        for (Account account : accounts) {
            Trader trader = account.getTrader();
            if (trader.getID() == traderId) {
                return trader;
            }
        }
        return null;
    }

    /**
     * Retrieves the {@linkplain Account} object associated with a given {@linkplain Trader}.
     *
     * @param trader The {@linkplain Trader} whose account is being retrieved.
     * @return The {@linkplain Account} associated with the trader, or {@code null} if none exists.
     */
    public Account getTraderAccount(Trader trader) {
        for (Account account : accounts) {
            if (account.getTrader().equals(trader)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Generates a string representation of the balances of all accounts for debugging purposes.
     *
     * @param includeHeader {@code true} to include a header in the output, {@code false} otherwise
     * @return A formatted string displaying the balances of all accounts and their total.
     */
    public String debugPrintBalances(boolean includeHeader) {
        StringBuilder output = new StringBuilder();

        if (includeHeader) {
            output.append("[_Institution_____________________ Balance_________]\n");
        }

        long totalBalance = 0;

        for (Account account : accounts) {
            Trader trader = account.getTrader();
            String traderTitle = trader.getTitle();
            long balance = account.getBalance();
            totalBalance += balance;

            output.append(String.format("[%2d %-30s %16s]\n",
                    trader.getID(),
                    traderTitle.length() > 30 ? traderTitle.substring(0, 30) : traderTitle,
                    String.format("$%,.2f", balance / 100.0)
            ));
        }

        output.append(String.format("[%30s %16s]\n",
                "TOTAL:",
                String.format("$%,.2f", totalBalance / 100.0)
        ));

        return output.toString();
    }
}
