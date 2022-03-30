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
            TagNode tagNode = new HtmlCleaner(props).clean(
                    new URL("https://finance.yahoo.com/quote/"+ticker.toUpperCase()+"?p="+ticker.toUpperCase()+"&.tsrc=fin-srch")
            );
            TagNode prevCloseNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[1]/td[2]");
            try {
                prevClose = Float.parseFloat(prevCloseNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                prevClose= 0;
            }

            TagNode openNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[2]/td[2]");
            try {
                open = Float.parseFloat(openNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                open= 0;
            }

            // //*[@id="quote-summary"]/div[2]/table/tbody/tr[3]/td[2]

            TagNode peRatioNode = evalPath(tagNode,"//*[@id=\"quote-summary\"]/div[2]/table/tbody/tr[3]/td[2]");
            try {
                peRatio = Float.parseFloat(peRatioNode.getText().toString().replaceAll(",", ""));
            }catch (Exception e){
                peRatio= 0;
            }

            TagNode currentPriceNode = evalPath(tagNode,"//*[@id=\"quote-header-info\"]/div[3]/div[1]/div[1]/fin-streamer[1]");
            currentPrice=Float.parseFloat(currentPriceNode.getText().toString().replaceAll(",",""));


            TagNode percentMovementNode = (TagNode) tagNode.findElementByAttValue("data-field", "regularMarketChangePercent", true, true).getAllChildren().get(0);
            percentMovement=Float.parseFloat(percentMovementNode.getText().toString().replaceAll(",","").replace("(","").replace(")","").replace("+","").replace("%",""));

            TagNode companyNameNode = evalPath(tagNode,"//*[@id=\"quote-header-info\"]/div[2]/div[1]/div[1]/h1");
            companyName=companyNameNode.getText().toString();
            return new StockData(prevClose,open,peRatio,currentPrice,percentMovement,ticker,companyName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
