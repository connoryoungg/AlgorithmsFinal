// Class representing an edge in the graph
class Edge {
    Currency from;
    Currency to;
    double originalRate;
    double cost;

    public Edge(Currency from, Currency to, double originalRate) {
        this.from = from;
        this.to = to;
        this.originalRate = originalRate;
        this.cost = -Math.log(originalRate); // Modify cost to -log(rate)
    }
}

