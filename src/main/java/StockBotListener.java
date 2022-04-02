import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StockBotListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("stockbot")){
            try{
                event.reply("On it!").complete();
                String ticker = event.getOption("ticker").getAsString().toUpperCase();
                StockData data=Scraper.getData(ticker);
                event.getChannel().sendMessage(data.toString()).complete();
            }catch (Exception e){
                event.reply("Sorry that is not a valid ticker. Please try again");
            }
        }
    }
}
