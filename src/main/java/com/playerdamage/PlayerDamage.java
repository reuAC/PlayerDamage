package com.playerdamage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
import java.util.regex.Pattern;


public final class PlayerDamage extends JavaPlugin {
    private final Logger logger;
    public PlayerDamage() {
        this.logger = Logger.getLogger("PlayerDamage");
    }

    static PlayerDamage main;
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private boolean isValidNumber(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }
    private boolean isInteger(String str) {
        return INTEGER_PATTERN.matcher(str).matches();
    }

    @Override
    public void onEnable() {
        logger.info("Loading configurations....");

        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(),this);
        Bukkit.getPluginCommand("pdamage").setExecutor(new PlayerDamageCommands());

        saveDefaultConfig();

        initializePlugin_Damage();
        initializePlugin_Attack();

        main = this;
    }

    public void initializePlugin_Damage() {
        String Damage = getConfig().getString("Damage");

        if (isValidNumber(Damage)) {
            PlayerDamageListener.damageEnable = true;
            logger.info("Damage value setting loaded successfully with a value of " + Damage);
        } else {
            PlayerDamageListener.damageEnable = false;
            logger.warning("The damage value setting function is disabled and the profile content is " + Damage);
        }
    }

    public void initializePlugin_Attack() {
        String Attack = getConfig().getString("Attack");

        if (isInteger(Attack)) {
            PlayerDamageListener.attackEnable = true;
            logger.info("Attack value setting loaded successfully with a value of " + Attack);
        } else {
            PlayerDamageListener.attackEnable = false;
            logger.warning("The Attack value setting function is disabled and the profile content is " + Attack);
        }
    }

    @Override
    public void onDisable() {

    }
}
