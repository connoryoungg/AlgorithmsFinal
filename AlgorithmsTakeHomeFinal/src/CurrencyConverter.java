import java.util.*;

// Class representing the currency converter
class CurrencyConverter {
    List<Currency> currencies;
    List<Edge> edges;

    public CurrencyConverter() {
        this.currencies = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    // Method to add a new currency
    public void addCurrency(String currencyName) {
        currencies.add(new Currency(currencyName, Double.POSITIVE_INFINITY));
    }

    // Method to add a new exchange rate
    public void addExchangeRate(String fromCurrency, String toCurrency, double rate) {
        Currency from = findCurrency(fromCurrency);
        Currency to = findCurrency(toCurrency);
        if (from != null && to != null) {
            edges.add(new Edge(from, to, rate));
        }
    }

    // Helper method to find a currency by name
    private Currency findCurrency(String currencyName) {
        for (Currency currency : currencies) {
            if (currency.name.equals(currencyName)) {
                return currency;
            }
        }
        return null;
    }

    // Method to find the conversion rate that leads to the most money


    public List<Edge> findShortestPath(String fromCurrency, String toCurrency) {
        Currency from = findCurrency(fromCurrency);
        Currency to = findCurrency(toCurrency);
        if (from == null || to == null) {
            System.out.println("Currency not found");
            return null;
        }

        // Dijkstra's algorithm
        Map<Currency, Double> dist = new HashMap<>();
        Map<Currency, Edge> prev = new HashMap<>();
        PriorityQueue<Currency> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (Currency currency : currencies) {
            dist.put(currency, Double.POSITIVE_INFINITY);
            prev.put(currency, null);
        }
        dist.put(from, 0.0);
        pq.add(from);

        while (!pq.isEmpty()) {
            Currency curr = pq.poll();
            // if this is true, we reached our destination
            if (curr == to) {
                break;
            }
            for (Edge edge : edges) {
                if (edge.from.equals(curr)) {
                    double newDist = dist.get(curr) + edge.cost;
                    if (newDist < dist.get(edge.to)) {
                        dist.put(edge.to, newDist);
                        prev.put(edge.to, edge);
                        pq.add(edge.to);
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<Edge> shortestPath = new ArrayList<>();
        Edge edge = prev.get(to);
        while (edge != null) {
            shortestPath.add(edge);
            edge = prev.get(edge.from);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    public double findMaxConversionRate(String from, String to) {
        List<Edge> shortestPath = findShortestPath(from, to);
        double maxRate = 1;
        for(Edge edge : shortestPath) {
            maxRate = maxRate * edge.originalRate;
        }
        return maxRate;
    }




//
//        // Run modified Dijkstra's algorithm
//
//        HashMap<Double, Edge> minPath = new HashMap<>();
//        Arrays.fill(maxMoney, Double.NEGATIVE_INFINITY);
//        maxMoney[currencies.indexOf(from)] = 0; // Distance from source to itself is 0
//
//        for (int i = 0; i < currencies.size() - 1; i++) {
//            for (Edge edge : edges) {
//                int fromIndex = currencies.indexOf(edge.from);
//                int toIndex = currencies.indexOf(edge.to);
//                double newAmount = maxMoney[fromIndex] + edge.modifiedCost;
//                maxMoney[toIndex] = Math.max(maxMoney[toIndex], newAmount);
//            }
//        }
//
//        return Math.exp(maxMoney[currencies.indexOf(to)]); // Return the product of rates




    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();

        // Adding currencies
        converter.addCurrency("USD");
        converter.addCurrency("EUR");
        converter.addCurrency("GBP");
        converter.addCurrency("JPY");

        // Adding exchange rates
        converter.addExchangeRate("USD", "EUR", 0.85);
        converter.addExchangeRate("EUR", "USD", 1.17);
        converter.addExchangeRate("USD", "GBP", 0.75);
        converter.addExchangeRate("GBP", "USD", 1.33);
        converter.addExchangeRate("EUR", "GBP", 0.88);
        converter.addExchangeRate("GBP", "EUR", 1.13);
        converter.addExchangeRate("EUR", "JPY", 169.47);
        converter.addExchangeRate("JPY", "EUR", 0.0059);


        // Example usage
        double maxConversionRate = converter.findMaxConversionRate("GBP", "EUR");
        System.out.println("Maximum conversion rate: " + maxConversionRate);
    }
}

