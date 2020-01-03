package mainStuff;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static JDA jda;
    public static String prefix = "?";

    public static String twitchLink = "https://www.twitch.tv/p_o_g";


    public static List<String> bans = new ArrayList<String>();

    //mainStuff.Main Method
    public static void main (String[] args) throws LoginException{
        jda = new JDABuilder(AccountType.BOT).setToken("TOKEN").build();

        //Sets Bot Presence
        jda.getPresence().setGame(Game.listening("Hi"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new NeedsBan());

        try {
            while (true) {
                jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("imma ban u"));
                Thread.sleep(16000);
                jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Game.listening("bleh"));
                Thread.sleep(16000);
                jda.getPresence().setPresence(OnlineStatus.IDLE, Game.watching("You Sleep"));
                Thread.sleep(16000);
                jda.getPresence().setGame(Game.streaming("?twitch", twitchLink));
                Thread.sleep(16000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
