import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageActivity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;
import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class StockBot {
    public static void main(String[] args) throws FileNotFoundException {
        JDA builder = null;
        String authKey;
        boolean exists = Files.exists(Path.of(System.getProperty("user.dir")+ File.separator+"key.txt"));

        //if auth key is not found in files ask for one
        if(!exists){
            Scanner scn = new Scanner(System.in);
            System.out.print("Enter bot auth key: ");
            authKey = scn.nextLine();
            PrintWriter printer=  new PrintWriter("stockBot.txt");
            printer.print(authKey);
            printer.close();
        }else{
            //if found, read it
            Scanner scn =new Scanner(new File("key.txt"));
            authKey=scn.next();
        }

        //build bot
        try {
            builder = JDABuilder.createDefault(authKey).build().awaitReady();
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }

        //add commands to bot
        CommandListUpdateAction commands = builder.updateCommands();
        commands.addCommands(
                Commands.slash("stockbot", "Ask the bot to fetch stock data")
                        .addOptions(new OptionData(STRING, "ticker", "the company stock tikcer that you would like to get data for") // USER type allows to include members of the server or other users by id
                                .setRequired(true)));
        commands.queue();

        //add listener to bot
        builder.addEventListener(new StockBotListener());
    }
}
