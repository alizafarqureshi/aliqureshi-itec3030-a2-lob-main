package ca.yorku.cmg.lob.stockexchange.events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import ca.yorku.cmg.lob.security.Security;
import ca.yorku.cmg.lob.security.SecurityList;
import ca.yorku.cmg.lob.stockexchange.tradingagent.TradingAgent;

/**
 * A NewsBoard object generates and shares financial/economic events that affect specific securities.
 */
public class NewsBoard {
    // Events are queued ordered by time
    private final PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingLong(Event::getTime));
    private final SecurityList securities;

    private static final Set<String> VALID_EVENTS = new HashSet<>(Arrays.asList("Good", "Bad"));

    // List of observers (trading agents)
    private final List<TradingAgent> observers = new ArrayList<>();

    public NewsBoard(SecurityList securities) {
        this.securities = securities;
    }

    /**
     * Allows trading agents to subscribe to news updates.
     * @param observer The trading agent to register.
     */
    public void registerObserver(TradingAgent observer) {
        observers.add(observer);
    }

    /**
     * Allows trading agents to unsubscribe from news updates.
     * @param observer The trading agent to remove.
     */
    public void removeObserver(TradingAgent observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers (trading agents) about an event.
     * @param event The event to notify them about.
     */
    private void notifyObservers(Event event) {
        for (TradingAgent observer : observers) {
            observer.update(event);
        }
    }

    /**
     * Load events from a file. Format: [Time, Relevant Ticker, EventType], where EventType is "Good" or "Bad".
     * @param filePath The path of the file.
     */
    public void loadEvents(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }
                
                long time;
                try {
                    time = Long.parseLong(values[0].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid time value in line: " + line);
                    continue;
                }
                
                String ticker = values[1].trim();
                String eventType = values[2].trim();
                
                if (!VALID_EVENTS.contains(eventType)) {
                    System.err.println("Invalid event value: " + eventType + " in line: " + line);
                    continue;
                }

                Security security = securities.getSecurityByTicker(ticker);
                if (security == null) {
                    System.err.println("Unknown ticker: " + ticker + " in line: " + line);
                    continue;
                }

                Event event = eventType.equals("Good") ? new GoodNews(time, security) : new BadNews(time, security);
                eventQueue.add(event);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Returns the event that happened at the specified time.
     * @param time The time at which the event happened.
     * @return The event that happened at that time, or {@code null} if no event happened at that time.
     */
    public Event getEventAt(long time) {
        for (Event event : eventQueue) {
            if (event.getTime() == time) {
                return event;
            }
        }
        return null;
    }

    /**
     * Runs the event list and sends notifications to registered trading agents.
     */
    public void runEventsList() {
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            System.out.println("Processing event: " + event.getClass().getSimpleName() + " at time " + event.getTime());
            
            // Notify all registered trading agents about the event
            notifyObservers(event);
        }
    }

    public void publishNews(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'publishNews'");
    }
}
