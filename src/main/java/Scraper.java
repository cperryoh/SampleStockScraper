import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class Scraper {
    static TagNode evalPath(TagNode node, String path) throws XPatherException {
        Object[] nodes = node.evaluateXPath(path);
        return (TagNode) (nodes[0]);
    }

    public static void main(String[] args) {
        System.out.println(getData("aapl"));
    }
    public static StockData getData(String ticker) {
        CleanerProperties props = new CleanerProperties();

        // set some properties to non-default values
        props.setTranslateSpecialEntities(true);
        props.setTransResCharsToNCR(true);
        props.setOmitComments(true);
        try {
            float prevClose;
            float open;
            float peRatio;
            float currentPrice;
            float percentMovement;
            String companyName;

            //For most of the numerical values, I have to remove the commas, I don't know if float.parse is ok with them, but i assumed it was not and removed them


            //pull html from web
            TagNode tagNode = new HtmlCleaner(props).clean(
                    new URL("https://finance.yahoo.com/quote/"+ticker.toUpperCase()+"?p="+ticker.toUpperCase()+"&.tsrc=fin-srch")
            );

            //parse prev close and convert to float
            TagNode prevCloseNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[1]/td[2]");
            try {
                prevClose = Float.parseFloat(prevCloseNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                prevClose= 0;
            }

            //parse open price and convert to float
            TagNode openNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[2]/td[2]");
            try {
                open = Float.parseFloat(openNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                open= 0;
            }


            //parse pe ratio and convert to float
            TagNode peRatioNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[2]/table/tbody/tr[3]/td[2]");
            try {
                peRatio = Float.parseFloat(peRatioNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                peRatio= 0;
            }

            //parse current price
            TagNode currentPriceNode = evalPath(tagNode,"//*[@id=\"quote-header-info\"]/div[3]/div[1]/div[1]/fin-streamer[1]");
            currentPrice=Float.parseFloat(currentPriceNode.getText().toString().replaceAll(",",""));

            //parse current percentage movement
            // for this one the xpath was pissy for whatever reason
            // I used a different feature called find element by attribute
            //I chose a unique attribute on the html node i was trying to reach and pulled it via that instead of xpath
            //I also had to remove a lot of characters from the string it comes in as (+1.00%), so i removed
            //the +,(,), and % characters so Float.parseFloat could make sense of it
            TagNode percentMovementNode = (TagNode) tagNode.findElementByAttValue("data-field", "regularMarketChangePercent", true, true).getAllChildren().get(0);
            percentMovement=Float.parseFloat(percentMovementNode.getText().toString().replaceAll(",","").replace("(","").replace(")","").replace("+","").replace("%",""));

            //parse full company name
            TagNode companyNameNode = evalPath(tagNode,"//*[@id=\"quote-header-info\"]/div[2]/div[1]/div[1]/h1");
            companyName=companyNameNode.getText().toString();

            //pass all that data into a class and return
            return new StockData(prevClose,open,peRatio,currentPrice,percentMovement,ticker,companyName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
