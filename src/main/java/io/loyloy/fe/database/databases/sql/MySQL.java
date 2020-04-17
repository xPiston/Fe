package io.loyloy.fe.database.databases.sql;

import io.loyloy.fe.Fe;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL extends SQLFunctions
{
    public MySQL( Fe plugin )
    {
        super( plugin );
    }

    protected Connection getNewConnection()
    {
        ConfigurationSection config = getConfigSection();

        setAccountTable( config.getString( "tables.accounts" ) );

        setAccountsColumnUser( config.getString( "columns.accounts.username" ) );

        setAccountsColumnMoney( config.getString( "columns.accounts.money" ) );

        setAccountsColumnUUID( config.getString( "columns.accounts.uuid" ) );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );

            String url = "jdbc:mysql://" + config.getString( "host" ) + ":" + config.getString( "port" ) + "/" + config.getString( "database" ) + "?autoReconnect=true";

            return DriverManager.getConnection( url, config.getString( "user" ), config.getString( "password" ) );
        }
        catch( Exception e )
        {
            return null;
        }
    }

    private ConfigurationSection getSection( ConfigurationSection parent, String childName )
    {
        ConfigurationSection child = parent.getConfigurationSection( childName );

        if( child == null )
        {
            child = parent.createSection( childName );
        }

        return child;
    }

    public void getConfigDefaults( ConfigurationSection section )
    {
        section.addDefault( "host", "localhost" );

        section.addDefault( "port", 3306 );

        section.addDefault( "user", "root" );

        section.addDefault( "password", "minecraft" );

        section.addDefault( "database", "Fe" );

        ConfigurationSection tables = getSection( section, "tables" );

        tables.addDefault( "accounts", "fe_accounts" );

        ConfigurationSection columns = getSection( section, "columns" );

        ConfigurationSection columnsAccounts = getSection( columns, "accounts" );

        columnsAccounts.addDefault( "username", "name" );

        columnsAccounts.addDefault( "money", "money" );

        columnsAccounts.addDefault( "uuid", "uuid" );
    }

    public String getName()
    {
        return "MySQL";
    }
}
