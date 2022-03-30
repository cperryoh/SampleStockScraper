import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        while(true){
            System.out.print("Enter a stock ticker: ");
            String ticker = scn.nextLine();

            StockData stock = Scraper.getData(ticker);
            System.out.println(stock+"\n");
        }
    }
}
