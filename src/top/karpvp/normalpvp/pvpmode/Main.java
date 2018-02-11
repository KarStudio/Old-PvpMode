package top.karpvp.normalpvp.pvpmode;

/*
还剩下的任务：当玩家死亡后，遍历集合，删除集合中的它
当玩家杀死别的玩家后，遍历集合，判断玩家在哪个模式中，然后恢复背包（不能使用函数getkit，因为它会清理背包）
必须保留背包里的其他东西，恢复装备、金苹果等
*/

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener{
    List<String> ga = new ArrayList<String>();
    List<String> po = new ArrayList<String>();
    List<String> fd = new ArrayList<String>();
    ItemStack air = new ItemStack(0);
    static ItemStack sword = new ItemStack(276,1);
    static ItemStack sword2 = new ItemStack(276,1);
    static ItemStack sword1 = new ItemStack(276,1);
    static ItemStack fishing_rod = new ItemStack(346,1);
    static ItemStack helmet = new ItemStack(310,1);
    static ItemStack chest_plate = new ItemStack(311,1);
    static ItemStack leggings = new ItemStack(312,1);
    static ItemStack boots = new ItemStack(313,1);
    static ItemStack helmet2 = new ItemStack(310,1);
    static ItemStack chest_plate2 = new ItemStack(311,1);
    static ItemStack leggings2 = new ItemStack(312,1);
    static ItemStack boots2 = new ItemStack(313,1);
    static ItemStack golden_apple = new ItemStack(322,64);
    static ItemStack potion = new ItemStack(373,1,(short)16421);
    static ItemStack potion2 = new ItemStack(373,4,(short)8229);
    static ItemStack golden_carrot = new ItemStack(396,64);
    static ItemStack bow = new ItemStack(261,1);
    static ItemStack ender_pearl = new ItemStack(368,4);
    static ItemStack arrow = new ItemStack(262,64);
    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,99999,0);
    
    @Override
    public void onEnable() {
        getLogger().info("成功载入插件");
        setupKit();
        Bukkit.getPluginManager().registerEvents((Listener)this, this);
//        CommandListener cmd = new CommandListener();
//        getCommand("KarUHCAdmin").setExecutor(cmd);
//        getCommand("KarUHC").setExecutor(cmd);
//        saveDefaultConfig();
//        reloadConfig();
    }
    
//    @EventHandler
//    public void LeftClickontheItem(PlayerInteractEvent event){
//        if ((event.getAction() == Action.LEFT_CLICK_AIR)||(event.getAction() == Action.LEFT_CLICK_BLOCK)){
//            Player p=event.getPlayer();
//            if (p.getItemInHand().getTypeId()==276){
//                ItemStack is = p.getItemInHand();
//                p.setItemInHand(is);
//            }else if (p.getItemInHand().getTypeId()==346){
//                ItemStack is = p.getItemInHand();
//                p.setItemInHand(is);
//            }else if (p.getItemInHand().getTypeId()==261){
//                ItemStack is = p.getItemInHand();
//                p.setItemInHand(is);
//            }
//        }
//    }
    
//    @EventHandler
//    public void onPlayerDamage (EntityDamageEvent event){
//        if ((event.getEntity() instanceof Player)) {
//            Player p = (Player)event.getEntity();
//            try{
////            ItemStack a = p.getInventory().getItem(36);
//                ItemStack a = new ItemStack(313,1,(short)0);
//                ItemMeta aim = p.getInventory().getItem(36).getItemMeta();
//                a.setItemMeta(aim);
//                p.getInventory().setItem(36, a);
//            }catch(Exception e){}try{
//                ItemStack b = new ItemStack(312,1,(short)0);
//                ItemMeta bim = p.getInventory().getItem(37).getItemMeta();
//                b.setItemMeta(bim);
//                p.getInventory().setItem(37, b);
//            }catch(Exception e){}try{
//                ItemStack c = new ItemStack(311,1,(short)0);
//                ItemMeta cim = p.getInventory().getItem(38).getItemMeta();
//                c.setItemMeta(cim);
//                p.getInventory().setItem(38, c);
//            }catch(Exception e){}try{
//                ItemStack d = new ItemStack(313,1,(short)0);
//                ItemMeta dim = p.getInventory().getItem(39).getItemMeta();
//                d.setItemMeta(dim);
//                p.getInventory().setItem(39, d);
//            }catch(Exception e){}
//        }
//    }
    
    
    //此处的异常待处理
    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent event){
        Player p =event.getEntity();
        if (p.getKiller()!=null){
            Player killer = p.getKiller();
            for (int index=0;index<ga.size();index++){
                if (ga.get(index)==killer.getDisplayName()){
                    for (int i=0;i<40;i++){
//                        try{
                            if (killer.getInventory().getItem(i)!=null){
//                                killer.sendMessage(i+""+p.getInventory().getItem(i).getType());
                                if(killer.getInventory().getItem(i).getTypeId()==322){
                                    killer.getInventory().setItem(i, air);
                                }else if(killer.getInventory().getItem(i).getTypeId()==276){
                                    killer.getInventory().setItem(i, sword);
                                }else if(killer.getInventory().getItem(i).getTypeId()==310){
                                    killer.getInventory().setItem(i, helmet);
                                }else if(killer.getInventory().getItem(i).getTypeId()==311){
                                    killer.getInventory().setItem(i, chest_plate);
                                }else if(killer.getInventory().getItem(i).getTypeId()==312){
                                    killer.getInventory().setItem(i, leggings);
                                }else if(killer.getInventory().getItem(i).getTypeId()==313){
                                    killer.getInventory().setItem(i, boots);
                                }else if(killer.getInventory().getItem(i).getTypeId()==346){
                                    killer.getInventory().setItem(i, fishing_rod);
                                }
                            }
//                        }catch(Exception e){}
                    }
                    killer.getInventory().addItem(golden_apple);killer.getInventory().addItem(golden_apple);
                    return;
                }
            }
        }
        quit(p.getDisplayName());
    }
    
    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event){
        Player p =event.getPlayer();
        quit(p.getDisplayName());
    }
    
    public void setupKit(){
        getLogger().info("生成装备信息...");
        try {
             ItemMeta sharpness = sword.getItemMeta();
             ItemMeta sharpness3 = sword.getItemMeta();
             ItemMeta sharpness1 = sword.getItemMeta();
             ItemMeta fr = fishing_rod.getItemMeta();
             ItemMeta protetion1 = helmet.getItemMeta();
             ItemMeta protetion2 = chest_plate.getItemMeta();
             ItemMeta nj = helmet.getItemMeta();
             ItemMeta power = bow.getItemMeta();
            sharpness.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
            sharpness.addEnchant(Enchantment.DURABILITY, 10000, true);
            sharpness3.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
            sharpness3.addEnchant(Enchantment.DURABILITY, 10000, true);
            sharpness1.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            sharpness1.addEnchant(Enchantment.DURABILITY, 10000, true);
            nj.addEnchant(Enchantment.DURABILITY, 10000, true);
            fr.addEnchant(Enchantment.DURABILITY, 10000, true);
            protetion1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            protetion1.addEnchant(Enchantment.DURABILITY, 10000, true);
            protetion2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            protetion2.addEnchant(Enchantment.DURABILITY, 10000, true);
            power.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
            power.addEnchant(Enchantment.DURABILITY, 10000, true);
            sword.setItemMeta(sharpness);
            sword2.setItemMeta(sharpness3);
            fishing_rod.setItemMeta(fr);
            helmet.setItemMeta(protetion1);
            chest_plate.setItemMeta(protetion2);
            leggings.setItemMeta(protetion1);
            boots.setItemMeta(protetion1);
            helmet2.setItemMeta(nj);
            chest_plate2.setItemMeta(nj);
            leggings2.setItemMeta(nj);
            boots2.setItemMeta(nj);
            bow.setItemMeta(power);
            getLogger().info("装备生成成功!");
        }catch (Exception e){
            getLogger().info("装备生成失败,已自动卸载插件");
            try {
                getServer().getPluginManager().disablePlugin(this);
            }catch(Exception ex){
                getLogger().info("插件卸载失败");
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("mode")) {
            if(args.length>0){
//                Player p = (Player)sender;
                if (args[0].equalsIgnoreCase("gapple")) {
                    if (args.length==1){
                        if ((sender instanceof Player)) {
                            Player p = (Player)sender;
                            getGappleKit(p);
                            Location gapple = new Location(p.getWorld(),410.5,56,0.5,270,0);
                            p.teleport(gapple);
                            quit(p.getDisplayName());
                            ga.add(p.getDisplayName());
                            return true;
                        }else{sender.sendMessage("§4必须是玩家");return true;}
                    }if (args.length>1)
                        if (args.length==2){
                            try{
                                Player pl = getPlayer(args[1]);
                                getGappleKit(pl);
                                Location gapple = new Location(pl.getWorld(),410.5,56,0.5,270,0);
                                pl.teleport(gapple);
                                quit(pl.getDisplayName());
                                ga.add(pl.getDisplayName());
                                sender.sendMessage("§a成功将此玩家加入此模式！");
                                pl.sendMessage("§6你被强制加入了模式Gapple");
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                            return true;
                        }
                    sender.sendMessage("/mode gapple <player>");
                    return true;
                }if (args[0].equalsIgnoreCase("potion")){
                    if (args.length==1){
                        if ((sender instanceof Player)) {
                            Player p = (Player)sender;
                            getPotionKit(p);
                            Location potion = new Location(p.getWorld(),-299.5,69,0.5,180,0);
                            p.teleport(potion);
                            quit(p.getDisplayName());
                            po.add(p.getDisplayName());
                            return true;
                        }else{sender.sendMessage("§4必须是玩家");return true;}
                    }if (args.length>1)if(args.length==2){
                        try{
                            Player pl = getPlayer(args[1]);
                            getPotionKit(pl);
                            Location potion = new Location(pl.getWorld(),-299.5,69,0.5,180,0);
                            pl.teleport(potion);
                            sender.sendMessage("§a成功将此玩家加入此模式！");
                            pl.sendMessage("§6你被强制加入了模式Potion");
                            quit(pl.getDisplayName());
                            po.add(pl.getDisplayName());
                        }catch(Exception e){
                            sender.sendMessage("§4找不到该玩家");
                            return true;
                        }
                        return true;
                    }
                    sender.sendMessage("/mode potion <player>");
                    return true;
                }if (args[0].equalsIgnoreCase("fulldia")){
                    if (args.length==1){
                        if ((sender instanceof Player)) {
                            Player p = (Player)sender;
                            getFullDiaKit(p);
                            Location fulldia = new Location(p.getWorld(),0.5,22,0.5,180,0);
                            p.teleport(fulldia);
                            quit(p.getDisplayName());
                            fd.add(p.getDisplayName());
                            return true;
                        }else{sender.sendMessage("§4必须是玩家");return true;}
                    }if (args.length>1)if(args.length==2){
                        try{
                            Player pl = getPlayer(args[1]);
                            getFullDiaKit(pl);
                            Location fulldia = new Location(pl.getWorld(),0.5,22,0.5,180,0);
                            pl.teleport(fulldia);
                            sender.sendMessage("§a成功将此玩家加入此模式！");
                            pl.sendMessage("§6你被强制加入了模式FullDia");
                            quit(pl.getDisplayName());
                            fd.add(pl.getDisplayName());
                        }catch(Exception e){
                            sender.sendMessage("§4找不到该玩家");
                            return true;
                        }
                        return true;
                    }
                    sender.sendMessage("/mode fulldia <player>");
                    return true;
                }
                sender.sendMessage("§6§l目前模式 §7> §eGapple §f| §cPotion §f| §9FullDia");
                return true;
            }
            sender.sendMessage("/mode <mode> [player]");
            return true;
        }if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (args.length==1){
                try{
                    Player pr=getPlayer(args[0]);
                    Location lc = new Location(pr.getWorld(),0,56,0);
                    pr.teleport(lc);
                    sender.sendMessage("§a成功将此玩家传送到出生点");
                    pr.sendMessage("§6你被强制传送到了出生点");
                    quit(pr.getDisplayName());
                    for (int i= 0;i<40;i++){
                        try{
                            pr.getInventory().setItem(i, air);
                        }catch(Exception e){}
                    }
                }catch (Exception e){
                    sender.sendMessage("§4找不到该玩家");
                    return true;
                }
                return true;
            }
            if(args.length==0){
                if ((sender instanceof Player)) {
                    Player p = (Player)sender;
                    Location lc = new Location(p.getWorld(),0,56,0);
                    p.teleport(lc);
                    quit(p.getDisplayName());
                    for (int i= 0;i<40;i++){
                        try{
                            p.getInventory().setItem(i, air);
                        }catch(Exception e){}
                    }
                    return true;
                }else{sender.sendMessage("§4必须是玩家");return true;}
            }
            sender.sendMessage("§e/spawn [player]");
            return true;
        }if (cmd.getName().equalsIgnoreCase("kit")){
            if(args.length>0){
//                    Player p = (Player)sender;
                    if (args[0].equalsIgnoreCase("gapple")) {
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getGappleKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length>1)if(args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getGappleKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成Gapple！");
                                pl.sendMessage("§6你被强制替换装备为Gapple");
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                            return true;
                        }
                        sender.sendMessage("/kit gapple <player>");
                        return true;
                    }else if (args[0].equalsIgnoreCase("potion")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getPotionKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length>1)if (args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getPotionKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成Potion！");
                                pl.sendMessage("§6你被强制替换装备为Potion");
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                            return true;
                        }
                        sender.sendMessage("/kit potion <player>");
                        return true;
                    }else if (args[0].equalsIgnoreCase("fulldia")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getFullDiaKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length>1)if(args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getFullDiaKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成FullDia！");
                                pl.sendMessage("§6你被强制替换装备为FullDia");
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                            return true;
                        }
                        sender.sendMessage("/kit fulldia <player>");
                        return true;
                    }
                    sender.sendMessage("§6§l目前模式 §7> §eGapple §f| §cPotion §f| §9FullDia");
                    return true;
                }
            sender.sendMessage("/kit <kit> [player]");
            return true;
        }
        return true;
    }
    
    public void getGappleKit(Player p){
        p.addPotionEffect(speed);
        p.getInventory().setItem(0,sword);
        p.getInventory().setItem(1,fishing_rod);
        p.getInventory().setItem(2,golden_apple);
        p.getInventory().setItem(3,golden_apple);
        p.getInventory().setItem(39,helmet);
        p.getInventory().setItem(38,chest_plate);
        p.getInventory().setItem(37,leggings);
        p.getInventory().setItem(36,boots);
        try{
            for (int x=4;x<36;x++){
                p.getInventory().setItem(x,air);
            }
        }catch(Exception e){}
    }
    public void getPotionKit(Player p){
        p.addPotionEffect(speed);
        p.getInventory().setItem(0,sword2);
        p.getInventory().setItem(1,fishing_rod);
        p.getInventory().setItem(8,golden_carrot);
        p.getInventory().setItem(39,helmet);
        p.getInventory().setItem(38,chest_plate);
        p.getInventory().setItem(37,leggings);
        p.getInventory().setItem(36,boots);
        try{
            for (int x=2;x<8;x++){
                p.getInventory().setItem(x,potion);
            }
            for (int x=9;x<36;x++){
                p.getInventory().setItem(x,potion);
            }
        }catch(Exception ex){}
    }
    public void getFullDiaKit(Player p){
        p.removePotionEffect(PotionEffectType.SPEED);
        p.getInventory().setItem(0,sword1);
        p.getInventory().setItem(1,fishing_rod);
        p.getInventory().setItem(2,bow);
        p.getInventory().setItem(3,potion2);
        p.getInventory().setItem(4,air);
        p.getInventory().setItem(5,air);
        p.getInventory().setItem(6,air);
        p.getInventory().setItem(7,golden_carrot);
        p.getInventory().setItem(8,ender_pearl);
        p.getInventory().setItem(9,arrow);
        p.getInventory().setItem(10,arrow);
        p.getInventory().setItem(39,helmet2);
        p.getInventory().setItem(38,chest_plate2);
        p.getInventory().setItem(37,leggings2);
        p.getInventory().setItem(36,boots2);
        for (int x=11;x<36;x++){
            p.getInventory().setItem(x,air);
        }
    }
    
    public void quit (String p){
        try{
            for (int index=0;index<ga.size();index++){
                if (ga.get(index)==p){
                    ga.remove(index);
                    return;
                }
            }for (int index=0;index<po.size();index++){
                if (po.get(index)==p){
                    po.remove(index);
                    return;
                }
            }for (int index=0;index<fd.size();index++){
                if (fd.get(index)==p){
                    fd.remove(index);
                    return;
                }
            }
        }catch (Exception e){}
    }
}
