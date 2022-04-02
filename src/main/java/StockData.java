public class StockData {
    public float prevClose;
    public float open;
    public float peRatio;
    public float currentPrice;
    public float percentMovement;
    public String ticker;
    public String companyName;

    public StockData(float prevClose, float open, float peRatio, float currentPrice, float percentMovement, String ticker, String companyName) {
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
        return "StockData for "+ticker+":" +
                "\nopen=" + open +
                "\npe ratio=" + peRatio +
                "\ncurrent price=" + currentPrice +
                "\nmovement from open price(%)=" + percentMovement +"%"+
                "\nTicker='" + ticker + '\'' +
                "\ncompanyName='" + companyName + "\'\n";
    }
}
