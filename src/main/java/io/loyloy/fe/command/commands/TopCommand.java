package io.loyloy.fe.command.commands;

import io.loyloy.fe.Fe;
import io.loyloy.fe.Phrase;
import io.loyloy.fe.command.CommandType;
import io.loyloy.fe.command.SubCommand;
import io.loyloy.fe.database.Account;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TopCommand extends SubCommand
{
    private final Fe plugin;

    public TopCommand( Fe plugin )
    {
        super( "top", "fe.top", "top", Phrase.COMMAND_TOP, CommandType.CONSOLE );

        this.plugin = plugin;
    }

    public boolean onCommand( CommandSender sender, Command cmd, String commandLabel, String[] args )
    {
        List<Account> topAccounts = plugin.getAPI().getTopAccounts();

        if( topAccounts.size() < 1 )
        {
            Phrase.NO_ACCOUNTS_EXIST.sendWithPrefix( sender );

            return true;
        }

        sender.sendMessage( plugin.getEqualMessage( Phrase.RICH.parse(), 10 ) );

        for( int i = 0; i < topAccounts.size(); i++ )
        {
            Account account = topAccounts.get( i );

            String two = Phrase.SECONDARY_COLOR.parse();

            sender.sendMessage( two + ( i + 1 ) + ". " + Phrase.PRIMARY_COLOR.parse() + account.getName() + two + " - " + plugin.getAPI().format( account ) );
        }

        sender.sendMessage( plugin.getEndEqualMessage( 28 ) );

        return true;
    }
}
