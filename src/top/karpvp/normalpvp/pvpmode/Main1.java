package top.karpvp.normalpvp.pvpmode;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main1 extends JavaPlugin{
    @Override
    public void onEnable() {
        getLogger().info("成功载入插件");
//        Bukkit.getPluginManager().registerEvents(this, this);
//        CommandListener cmd = new CommandListener();
//        getCommand("KarUHCAdmin").setExecutor(cmd);
//        getCommand("KarUHC").setExecutor(cmd);
        saveDefaultConfig();
        reloadConfig();
    }
        @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("mode")) {
            if(args.length>0){
                for(int modeamount=0;modeamount<getConfig().getInt("ModeAmount");modeamount++){
                    if (args[0].equalsIgnoreCase(getConfig().getString("Mode"+(modeamount+1)+"Name"))) {
                        p.sendMessage("§7成功将你传送到§7§l"+getConfig().getString("Mode"+(modeamount+1)+"Name"));
                        switch (getConfig().getInt("Mode"+(modeamount+1)+"GameMode")){
                            case 0:
                                p.setGameMode(GameMode.SURVIVAL);
                            case 1:
                                p.setGameMode(GameMode.CREATIVE);
                            case 2:
                                p.setGameMode(GameMode.ADVENTURE);
                            case 3:
                                p.setGameMode(GameMode.SPECTATOR);
                            default:
                                p.setGameMode(GameMode.ADVENTURE);
                        }
                        double x = getConfig().getDouble("Mode"+(modeamount+1)+"_X");
                        double y = getConfig().getDouble("Mode"+(modeamount+1)+"_Y");
                        double z = getConfig().getDouble("Mode"+(modeamount+1)+"_Z");
                        float dy = (float)getConfig().getDouble("Mode"+(modeamount+1)+"_dY");
                        float dx = (float)getConfig().getDouble("Mode"+(modeamount+1)+"_dX");
                        Location lc = new Location(p.getWorld(),x,y,z,dy,dx);
                        p.teleport(lc);
                        for (int kitamount=0;kitamount<getConfig().getInt("Mode"+(modeamount+1)+"KitAmount");kitamount++){
                            List<String> lores = new ArrayList<String>();
                            ItemStack is = new ItemStack(getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"ID"),getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Amount"),(short)getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Data"));
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Name");
                            for (int kitenchamount=0;kitenchamount<getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"EnchAmount");kitenchamount++){
                                switch (getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"ID")){
                                    default:lores.add("§4错误:添加了一个不明的效果");
                                    case 0:im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 1:im.addEnchant(Enchantment.PROTECTION_FIRE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 2:im.addEnchant(Enchantment.PROTECTION_FALL, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 3:im.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 4:im.addEnchant(Enchantment.PROTECTION_PROJECTILE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 5:im.addEnchant(Enchantment.OXYGEN, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 6:im.addEnchant(Enchantment.WATER_WORKER, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 7:im.addEnchant(Enchantment.THORNS, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 8:im.addEnchant(Enchantment.DEPTH_STRIDER, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 16:im.addEnchant(Enchantment.DAMAGE_ALL, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 17:im.addEnchant(Enchantment.DAMAGE_UNDEAD, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 18:im.addEnchant(Enchantment.DAMAGE_ARTHROPODS, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 19:im.addEnchant(Enchantment.KNOCKBACK, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 20:im.addEnchant(Enchantment.FIRE_ASPECT, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 21:im.addEnchant(Enchantment.LOOT_BONUS_MOBS, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 32:im.addEnchant(Enchantment.DIG_SPEED, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 33:im.addEnchant(Enchantment.SILK_TOUCH, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 34:im.addEnchant(Enchantment.DURABILITY, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 35:im.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 48:im.addEnchant(Enchantment.ARROW_DAMAGE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 49:im.addEnchant(Enchantment.ARROW_KNOCKBACK, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 50:im.addEnchant(Enchantment.ARROW_FIRE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 51:im.addEnchant(Enchantment.ARROW_INFINITE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 61:im.addEnchant(Enchantment.LUCK, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                    case 62:im.addEnchant(Enchantment.LURE, getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Ench"+(kitenchamount+1)+"Level"), true);
                                }
                        }
                        for (int kitlores=0;kitlores<getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"LoreAmount");kitlores++){
                            lores.add(getConfig().getString("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Lore"+(kitlores+1)));
                        }
                        im.setLore(lores);
                        is.setItemMeta(im);
                        p.getInventory().setItem(getConfig().getInt("Mode"+(modeamount+1)+"Kit"+(kitamount+1)+"Location"),is);
                    }
                    for(int effectamount=0;effectamount<getConfig().getInt("Mode"+(modeamount+1)+"EffectAmount");effectamount++){
                        PotionEffect pe=null;
                        switch (getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"ID")){
                            default:p.sendMessage("斗笔腐竹把配置文件写错了,给你添加了一个不明的药水效果");
                            case 1: pe = new PotionEffect(PotionEffectType.SPEED,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 2: pe = new PotionEffect(PotionEffectType.SLOW,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 3: pe = new PotionEffect(PotionEffectType.FAST_DIGGING,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 4: pe = new PotionEffect(PotionEffectType.SLOW_DIGGING,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 5: pe = new PotionEffect(PotionEffectType.INCREASE_DAMAGE,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 6: pe = new PotionEffect(PotionEffectType.HEAL,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 7: pe = new PotionEffect(PotionEffectType.HARM,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 8: pe = new PotionEffect(PotionEffectType.JUMP,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 9: pe = new PotionEffect(PotionEffectType.CONFUSION,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 10: pe = new PotionEffect(PotionEffectType.REGENERATION,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 11: pe = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 12: pe = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 13: pe = new PotionEffect(PotionEffectType.WATER_BREATHING,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 14: pe = new PotionEffect(PotionEffectType.INVISIBILITY,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 15: pe = new PotionEffect(PotionEffectType.BLINDNESS,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 16: pe = new PotionEffect(PotionEffectType.NIGHT_VISION,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 17: pe = new PotionEffect(PotionEffectType.HUNGER,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 18: pe = new PotionEffect(PotionEffectType.WEAKNESS,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 19: pe = new PotionEffect(PotionEffectType.POISON,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 20: pe = new PotionEffect(PotionEffectType.WITHER,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 21: pe = new PotionEffect(PotionEffectType.HEALTH_BOOST,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 22: pe = new PotionEffect(PotionEffectType.ABSORPTION,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                            case 23: pe = new PotionEffect(PotionEffectType.SATURATION,getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Times"),getConfig().getInt("Mode"+(modeamount+1)+"Effect"+(modeamount+1)+"Level")-1);
                        }
                        p.addPotionEffect(pe);
                    }
                    return true;
                }
            }
            sendhelp(p);
            return true;
        }
        sendhelp(p);
        return true;
    }
        return true;
    }
        
    public void sendhelp(Player p){
        p.sendMessage("§e使用方法:");
        p.sendMessage("§e/mode <mode>");
        p.sendMessage("§e所有mode有:");
        for (int modeamount=0;modeamount<getConfig().getInt("ModeAmount");modeamount++){
            p.sendMessage("§e"+getConfig().getString("Mode"+(modeamount+1)+"Name"));
        }
    }
}
