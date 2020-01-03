package mainStuff;

import net.dv8tion.jda.core.EmbedBuilder;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Random;

import static mainStuff.Main.jda;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //PING
        if (args[0].equalsIgnoreCase(Main.prefix + "ping")) {
            event.getChannel().sendMessage("Pong!" + "\n> WebSocket Latency: " + Long.toString(jda.getPing()) + "ms").queue();
        }



            //TWITCH
            if (args[0].equalsIgnoreCase(Main.prefix + "twitch")) {

                try {
                    if (!args[1].isEmpty() && args[1].startsWith("https://www.twitch.tv/")) {
                        Main.twitchLink = args[1];
                        event.getChannel().sendMessage("Setting my twitch link to: ``" + Main.twitchLink + "``").queue();
                    } else {
                        event.getChannel().sendMessage("Looks like that's not a twitch link dude.").queue();
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("You gotta provide me with a twitch link my dude ").queue();
                }
            }

            //POLL
            if (args[0].equalsIgnoreCase(Main.prefix + "poll")) {
                String pollQ;

                try {
                    if (!args[1].isEmpty()) {
                        pollQ = String.join(" ", args).substring(5);

                        event.getMessage().delete().queue();

                        EmbedBuilder emb = new EmbedBuilder();

                        emb.setColor(Color.BLACK);
                        emb.setFooter("Poll by: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());

                        emb.setTitle("**" + pollQ + "**");

                        //adds reactions to poll message
                        event.getChannel().sendMessage(emb.build()).queue(m -> {
                            m.addReaction("\ud83d\udc4d").queue();
                            m.addReaction("\uD83D\uDC4E").queue();
                        });
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("You gotta tell me the thing u wanna poll").queue();
                }
            }

            //DICE ROLL
            if (args[0].equalsIgnoreCase(Main.prefix + "dice") || args[0].equalsIgnoreCase(Main.prefix + "roll")) {
                int dice1 = (int) (Math.random() * 6 + 1);
                int dice2 = (int) (Math.random() * 6 + 1);
                EmbedBuilder emb = new EmbedBuilder();

                //emb.setTitle("Two Dice Rolled");
                emb.addField("Dice 1:    **" + Integer.toString(dice1) + "**", "", false);
                emb.addField("Dice 2:   **" + Integer.toString(dice2) + "**", "", false);
                emb.addField("**TOTAL:**   **" + Integer.toString(dice1 + dice2) + "**", "", false);
                emb.setColor(Color.RED);
                emb.setThumbnail("https://media.giphy.com/media/5nxHFn5888nrq/giphy.gif");
                event.getChannel().sendMessage(emb.build()).queue();

            }

        if (args[0].equalsIgnoreCase(Main.prefix + "ask")) {
            String answers[] = {"Fuck No", "No one loves you, kys", "It is possible", "100% yes", "Eat some ass then ask again", "I guess", "Hell naw", "Yessir", "naww dogg", "Ask me again...", "Suck my wee wee", "You will never know", "Yes", "No", "Prob nawt", "I think yes", "I think nope", "Impossible"};
            //18 options

            //if the used command but did not ask a question
            if(event.getMessage().getContentRaw().length() <= 4) {
                event.getChannel().sendMessage("You gotta ask my a question you dingle berry").queue();
            } else {
                Random rand = new Random();
                int randomInt = rand.nextInt(17);
                event.getChannel().sendMessage(answers[randomInt]).queue();
            }

        }

        }

    }
