package mainStuff;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class NeedsBan extends ListenerAdapter {

    //steam name, reason
    Map<String, String> reason = new HashMap<>();
    public EventWaiter waiter;

    public NeedsBan(EventWaiter waiter) {
        this.waiter = waiter;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String args[] = event.getMessage().getContentRaw().split(" ", 2);

        if (args[0].equalsIgnoreCase(Main.prefix + "add")) {
           try{
                Main.bans.add(args[1]);
                event.getChannel().sendMessage("Added **" + args[1] + "** to the ban list.\n__" + event.getAuthor().getName() + " what is the reason for the ban?__").queue();

               waiter.waitForEvent(
                       GuildMessageReceivedEvent.class,
                       (eventReason) -> {
                           return eventReason.getAuthor().equals(event.getAuthor()) && eventReason.getChannel().equals(event.getChannel()) && eventReason != event;
                       },
                       (eventReason) -> {
                                reason.put(args[1], eventReason.getMessage().getContentRaw());
                                System.out.println(eventReason.getMessage().getContentRaw());
                                event.getChannel().sendMessage("Added the reason for the **" + args[1] + "** ban.").queue();

                       },
                       30, TimeUnit.SECONDS,
                       () -> {
                           event.getChannel().sendMessage("30 second timeout with no ban reason entered, **" + args[1] + "** has been removed from the ban list.").queue();
                           Main.bans.remove(args[1]);
                       }
               );

           } catch (Exception e) {
                event.getChannel().sendMessage("Error: You must add the name of the user that you want to add to the ban list.").queue();
           }

        } else if (args[0].equalsIgnoreCase(Main.prefix + "remove")) {

            try{
                Main.bans.remove(Main.bans.indexOf(args[1]));
                event.getChannel().sendMessage("Removed **" + args[1] + "** from the ban list.").queue();
                reason.remove(args[1]);
            } catch (Exception e) {
                event.getChannel().sendMessage("Error: You must add the name of the user that needs to be removed from the ban list **OR** the user is not on the ban list.").queue();
            }

        }  else if (args[0].equalsIgnoreCase(Main.prefix + "get")) {
            event.getChannel().sendMessage("Current Ban List: " + Main.bans.toString()).queue();
            EmbedBuilder em = new EmbedBuilder();
            em.setTitle("Current Ban List:");
            em.setColor(Color.red);

            if(Main.bans.size() <= 0) {
                em.addField("", "No bans in ban list.", false);
            }

            for (int i = 0; i < Main.bans.size(); i++) {
                em.addField("Steam: **" + Main.bans.get(i) + "**", "Reason: " + reason.get(Main.bans.get(i)), false);
            }
            event.getChannel().sendMessage(em.build()).queue();

        }

        //Checks embed titles
        try {
            for (int i = 0; i < Main.bans.size(); i++) {
                if (event.getMessage().getEmbeds().get(0).getTitle().toLowerCase().contains(Main.bans.get(i).toLowerCase() + " is connecting")){
                    event.getGuild().getTextChannelById("664046832783065113").sendMessage(event.getGuild().getRoleById("567217830274138112").getAsMention()+ "@here" + " **" + Main.bans.get(i) + "** is in the city!").queue();
                    event.getGuild().getTextChannelById("664046832783065113").sendMessage("Reason for **" + Main.bans.get(i) + "** ban: \n ```" + reason.get(Main.bans.get(i) + "```")).queue();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: checking embed with ban list");

        }

    }



}
