package com.playerdamage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.regex.Pattern;

public class PlayerDamageCommands implements CommandExecutor {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private boolean isValidNumber(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }
    private boolean isInteger(String str) {
        return INTEGER_PATTERN.matcher(str).matches();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            return handleReloadCommand(commandSender);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("d") && args[1].equalsIgnoreCase("set")) {
            return handleDamageSetCommand(commandSender, args[2]);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("a") && args[1].equalsIgnoreCase("set")) {
            return handleAttackSetCommand(commandSender, args[2]);
        } else {
            commandSender.sendMessage("Usage:");
            commandSender.sendMessage("/pdamage reload - Reloads the configuration");
            commandSender.sendMessage("/pdamage d set <value> - Sets the damage value, 'false' to disabled");
            commandSender.sendMessage("/pdamage a set <value> - Sets the attack value, 'false' to disabled");
            return false;
        }
    }

    private boolean handleReloadCommand(CommandSender sender) {
        try {
            PlayerDamage.main.reloadConfig();
            PlayerDamage.main.initializePlugin_Damage();
            PlayerDamage.main.initializePlugin_Attack();
            sender.sendMessage("[PlayerDamage] Reload complete");
            return true;
        } catch (Exception e) {
            sender.sendMessage("[PlayerDamage] An error has occurred in the reloading");
            e.printStackTrace();
            return false;
        }
    }

    private boolean handleDamageSetCommand(CommandSender sender, String value) {
        FileConfiguration config = PlayerDamage.main.getConfig();
        if ("false".equalsIgnoreCase(value)){
            config.set("Damage", value);
            PlayerDamage.main.saveConfig();
            PlayerDamage.main.initializePlugin_Damage();
            sender.sendMessage("[PlayerDamage] The Damage value setting function is disabled.");
            return true;
        }
        if (isValidNumber(value)){
            config.set("Damage", value);
            PlayerDamage.main.saveConfig();
            PlayerDamage.main.initializePlugin_Damage();

            sender.sendMessage("[PlayerDamage] Damage value set to " + value);
            return true;
        }else {
            sender.sendMessage("[PlayerDamage] An error has occurred, you have entered something that is not an integer or floating point number, the configuration change is cancelled.");
            return false;
        }
    }

    private boolean handleAttackSetCommand(CommandSender sender, String value) {
        FileConfiguration config = PlayerDamage.main.getConfig();

        if ("false".equalsIgnoreCase(value)){
            config.set("Attack", value);
            PlayerDamage.main.saveConfig();
            PlayerDamage.main.initializePlugin_Attack();
            sender.sendMessage("[PlayerDamage] The Attack value setting function is disabled.");
            return true;
        }

        if (isInteger(value)){
            config.set("Attack", value);
            PlayerDamage.main.saveConfig();
            PlayerDamage.main.initializePlugin_Attack();

            sender.sendMessage("[PlayerDamage] Attack value set to " + value);
            return true;
        }else {
            sender.sendMessage("[PlayerDamage] An error has occurred, you have entered something that is not an integer, the configuration change is cancelled.");
            return false;
        }
    }
}
