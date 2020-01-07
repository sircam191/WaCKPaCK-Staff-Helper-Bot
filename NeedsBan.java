package mainStuff;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class NeedsBan extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String args[] = event.getMessage().getContentRaw().split(" ", 2);

        if (args[0].equalsIgnoreCase(Main.prefix + "add")) {
           try{
                Main.bans.add(args[1]);
                event.getChannel().sendMessage("Added **" + args[1] + "** to the ban list.").queue();
           } catch (Exception e) {
                event.getChannel().sendMessage("Error: you gotta add the name of the user that you want to add to the ban list.").queue();
           }
        } else if (args[0].equalsIgnoreCase(Main.prefix + "remove")) {

            try{
                Main.bans.remove(Main.bans.indexOf(args[1]));
                event.getChannel().sendMessage("Removed **" + args[1] + "** from the ban list.").queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("Error: you gotta add the name of the user that needs tp be removed from the ban list.").queue();
            }

        }  else if (args[0].equalsIgnoreCase(Main.prefix + "get")) {
            event.getChannel().sendMessage("Current Ban List: " + Main.bans.toString()).queue();

        }

        //Checks embed titles
        try {
            for (int i = 0; i < Main.bans.size(); i++) {
                if (event.getMessage().getEmbeds().get(0).getTitle().toLowerCase().contains(Main.bans.get(i).toLowerCase() + " is connecting")){
                    event.getChannel().sendMessage(event.getGuild().getRoleById("567217830274138112").getAsMention()+ "@here" + " **" + Main.bans.get(i) + "** is in the city!").queue();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: checking embed with ban list");

        }




    }


}
