public class StockData {
    public double prevClose;
    public double open;
    public double peRatio;
    public double currentPrice;
    public double percentMovement;
    public String ticker;
    public String companyName;

    public StockData(double prevClose, double open, double peRatio, double currentPrice, double percentMovement, String ticker, String companyName) {
        this.prevClose = prevClose;
        this.open = open;
        this.peRatio = peRatio;
        this.currentPrice = currentPrice;
        this.percentMovement = percentMovement;
        this.ticker = ticker;
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "prevClose=" + prevClose +
                ", open=" + open +
                ", peRatio=" + peRatio +
                ", currentPrice=" + currentPrice +
                ", percentMovement=" + percentMovement +
                ", ticker='" + ticker + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
