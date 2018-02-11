package top.karpvp.normalpvp.pvpmode;

import java.text.NumberFormat;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Set;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getWorld;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main3 extends JavaPlugin implements Listener{
    public static List<String> ga = new ArrayList<String>();//Gapple Mode
    public static List<String> po = new ArrayList<String>();//Potion Mode
    public static List<String> fd = new ArrayList<String>();//FullDiamond Mode
    public static List<String> cb = new ArrayList<String>();//Combo Mode
    Map damagemap = new HashMap<String,String>();
    List<String> damagelist = new ArrayList<String>();
//    Set<String> set = cool.keySet();
//    Iterator<String> it = set.iterator();
//    Collection<String> damagelist = cool.values();
    List<String> cpsTestPlayer = new ArrayList<String>();
    Map cpsTestAmount = new HashMap<String,String>();
    static ItemStack air = new ItemStack(0);
    static ItemStack sword = new ItemStack(276,1);
    static ItemStack sword2 = new ItemStack(276,1);
    static ItemStack sword1 = new ItemStack(276,1);
    static ItemStack combosword = new ItemStack(276,1);
    static ItemStack fishing_rod = new ItemStack(346,1);
    static ItemStack helmet = new ItemStack(310,1);
    static ItemStack chest_plate = new ItemStack(311,1);
    static ItemStack leggings = new ItemStack(312,1);
    static ItemStack boots = new ItemStack(313,1);
    static ItemStack helmet2 = new ItemStack(310,1);
    static ItemStack chest_plate2 = new ItemStack(311,1);
    static ItemStack leggings2 = new ItemStack(312,1);
    static ItemStack boots2 = new ItemStack(313,1);
    static ItemStack potionhelmet = new ItemStack(310,1);
    static ItemStack potionchest_plate = new ItemStack(311,1);
    static ItemStack potionleggings = new ItemStack(312,1);
    static ItemStack potionboots = new ItemStack(313,1);
    static ItemStack combohelmet = new ItemStack(310,1);
    static ItemStack combochest_plate = new ItemStack(311,1);
    static ItemStack comboleggings = new ItemStack(312,1);
    static ItemStack comboboots = new ItemStack(313,1);
    static ItemStack golden_apple = new ItemStack(322,64);
    static ItemStack xgolden_apple = new ItemStack(322,32,(short)1);
    static ItemStack potion = new ItemStack(373,1,(short)16421);
    static ItemStack potion2 = new ItemStack(373,1,(short)8229);
    static ItemStack beef = new ItemStack(364,64);
    static ItemStack bow = new ItemStack(261,1);
    static ItemStack ender_pearl = new ItemStack(368,4);
    static ItemStack ender_pearl16 = new ItemStack(368,16);
    static ItemStack arrow = new ItemStack(262,64);
    static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,99999,0);
    
    static ItemStack cpsTest = new ItemStack(347,1);
    public static ItemStack function = new ItemStack(388,1);
    
    
    static Location lobbyLocation;
    static Location gappleLocation;
    static Location potionLocation;
    static Location fulldiaLocation;
    static Location comboLocation;
    static int lobbyGameMode;
    static int reSpawnGameMode;
    static int reSpawnTime;
    
    @Override
    public void onEnable() {
        getLogger().info("成功载入插件");
        setup();
//        addDamageList();
        Bukkit.getPluginManager().registerEvents((Listener)this, this);
        saveDefaultConfig();
        reloadConfig();
    }
    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            if (p.getGameMode()==GameMode.SPECTATOR){
                p.setGameMode(GameMode.SURVIVAL);
                try{
                backLobby(p);
                }catch(Exception e){}
            }
        }
//        getServer().broadcastMessage("§c§l插件更新 §f| 插件更新中，可能有所影响，抱歉。");
        getServer().broadcastMessage("§c§l插件Debug §f| 插件在Dubug中，会持续更新，可能有所影响，抱歉。");
        getLogger().info("成功卸载插件");
    }
    
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){
        backLobby(event.getPlayer());
        event.getPlayer().setMaximumNoDamageTicks(15);
        damagelist.add(event.getPlayer().getName());
        damagemap.put(event.getPlayer().getName(),0);
    }
    
    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event){
        backLobby(event.getPlayer());
        damagelist.remove(event.getPlayer().getName());
        damagemap.remove(event.getPlayer().getName());
        for (int index=0;index<cpsTestPlayer.size();index++){
            if (cpsTestPlayer.get(index)==event.getPlayer().getName()){
                cpsTestAmount.remove(cpsTestPlayer.get(index));
                cpsTestAmount.put(cpsTestPlayer.get(index),200);
                return;
            }
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        p.setHealth(20.0);
        p.setFoodLevel(100);
        p.setGameMode(GameMode.SPECTATOR);
        damagemap.remove(p.getName());
        damagemap.put(p.getName(), 0);
        new BukkitRunnable()
        {
            int t = 3;
            @Override
            public void run() 
            {
                if (t != 3&&t!=0){
                    TitleAPI.sendTitle(p, Integer.valueOf(0), Integer.valueOf(20), Integer.valueOf(0), "&4&l你死了", "&f你将在&l"+t+"&f秒后重生");
                    t--;
                }else if(t == 3){
                    if (p.getKiller()!=null)
//                            p.sendTitle("§7你被§l§4"+p.getKiller().getName()+"§7杀死了",t+"秒后重生");
                        TitleAPI.sendTitle(p, Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(0), "&4&l你死了", "§7你被§l§4"+p.getKiller().getName()+"§7杀死了");
                    else
//                            p.sendTitle("§7你死了","");
                        TitleAPI.sendTitle(p, Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(0), "&4&l你死了", "");
                    t--;
                }else if(t == 0){
//                    if (getConfig().getInt("GameModeChange")==2)
//                        p.setGameMode(GameMode.ADVENTURE);
//                    else if (getConfig().getInt("GameModeChange")==0)
                        p.setGameMode(GameMode.SURVIVAL);
//                    else 
//                        p.setGameMode(GameMode.ADVENTURE);
//                    Location lc = p.getWorld().getSpawnLocation();
                    if (inGapple(p)){
                        p.teleport(gappleLocation);
                        getGappleKit(p);
                    }else if (inPotion(p)){
                        p.teleport(potionLocation);
                        getPotionKit(p);
                    }else if (inFullDia(p)){
                        p.teleport(fulldiaLocation);
                        getFullDiaKit(p);
                    }else if (inCombo(p)){
                        p.teleport(comboLocation);
                        getComboKit(p);
                    }else {p.teleport(lobbyLocation);}
                    
//                        p.sendTitle("已重生", "");
                    TitleAPI.sendTitle(p, Integer.valueOf(0), Integer.valueOf(20), Integer.valueOf(10), "&9&l已重生", "");
//                        TitleActionBarAPI.sendFullTitle(evt.getPlayer(), 20, 30, 20, TITLE, SUBTITLE);
                    cancel();  // 终止线程
//                     return;
                }else{cancel();}

            }
        }.runTaskTimer((Plugin)this , /*(getConfig().getInt("RespawnDelay"))*1L*/0L ,20L );
        if (true){
            try{
            ItemStack air = new ItemStack(0);
            for (int x=0;x<40;x++){
                p.getInventory().setItem(x,air);
            }
            }catch(Exception e){}
        }
        
        
        //golden_head
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta headowner = (SkullMeta)head.getItemMeta();
        headowner.setOwner(p.getName());
        head.setItemMeta(headowner);
        ItemMeta im = head.getItemMeta();
        im.setDisplayName("§c§l"+p.getName()+" §7的头§7§l(右键使用)");
        List<String> lores = new ArrayList<String>();
        try{
            if (p.getKiller()!=null){
                
                lores.add("§7Ta死在了§f§l"+p.getKiller().getName()+"§7的手上");
            }else{
                EntityDamageEvent ede = p.getLastDamageCause();
                EntityDamageEvent.DamageCause dc = ede.getCause();
                if(dc == EntityDamageEvent.DamageCause.PROJECTILE)
                    lores.add("§7这斗笔是被射♂死的");
                else if(dc == EntityDamageEvent.DamageCause.SUFFOCATION)
                    lores.add("§7这斗笔是被方块艹死的");
                else if(dc == EntityDamageEvent.DamageCause.FALL)
                    lores.add("§7这斗笔自己§2§m衰§2摔死的");
                else if((dc == EntityDamageEvent.DamageCause.FIRE) || (dc == EntityDamageEvent.DamageCause.MELTING))
                    lores.add("§7这斗笔自己§2§m骚§2烧死了");
                else if(dc == EntityDamageEvent.DamageCause.LAVA)
                    lores.add("§7这斗笔泡了个温泉");
                else if(dc == EntityDamageEvent.DamageCause.DROWNING)
                    lores.add("§7这斗笔被水怪抓走了");
                else if((dc == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)||(dc == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
                    lores.add("§7这斗笔被炸死了");
                else if(dc == EntityDamageEvent.DamageCause.VOID)
                    lores.add("§7这斗笔掉入了深坑(你是怎么拿到他的头的XD)");
                else if(dc == EntityDamageEvent.DamageCause.LIGHTNING)
                    lores.add("§7这斗笔人品极差，被雷劈死了");
                else if(dc == EntityDamageEvent.DamageCause.STARVATION)
                    lores.add("§7这斗笔§2§m二§2饿死了");
                else if(dc == EntityDamageEvent.DamageCause.FALLING_BLOCK)
                    lores.add("§7这斗笔被砸死了");
                else
                    lores.add("§7这斗笔是莫名其妙死的");
            }
        }catch(Exception e){}
        im.setLore(lores);
        head.setItemMeta(im);
        p.getWorld().dropItem(p.getLocation(), head);
    }
    
    
    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player p=(Player)event.getEntity();
//            for(int index=0;index<damagelist.size();index++){
//                if (damagelist.get(index)==event.getEntity().getName()){
//                    break;
//                }else if(index==damagelist.size()&&damagelist.get(index)!=event.getEntity().getName()){
//                    damagelist.add(event.getEntity().getName());
//                    damagemap.put(event.getEntity().getName(),20);
//                }
//            }
            
            
            if (!event.isCancelled()){
                try{
                    damagemap.get(p.getName());
                }catch(Exception e){
                    damagelist.add(p.getName());
                    damagemap.put(p.getName(),50);
                }
                damagemap.remove(p.getName());
                damagemap.put(p.getName(),50);
                if (inPotion(p))
                    p.setVelocity(p.getLocation().getDirection().multiply(0.5D).setY(-0.2D));
            }
        }
        if (event.getDamager() instanceof Player){
//            try{
//                cool.remove(event.getDamager().getName());
//            }catch(Exception e){}
//            cool.put(event.getDamager().getName(), 20);
//        }if(event.getEntity() instanceof Player){
//            try{
//                cool.remove(event.getEntity().getName());
//            }catch(Exception e){}
//            cool.put(event.getEntity().getName(), 20);
            
            
            if (!event.isCancelled()){
                try{
                    int x = (int)damagemap.get(event.getDamager().getName());
                }catch(Exception e){
                    damagelist.add(event.getDamager().getName());
                    damagemap.put(event.getDamager().getName(),50);
                }
                damagemap.remove(event.getDamager().getName());
                damagemap.put(event.getDamager().getName(),50);
            }
            
            
//            for(int index=0;index<damagelist.size();index++){
//                if (damagelist.get(index)==event.getDamager().getName()){
//                    break;
//                }else if(index==damagelist.size()&&damagelist.get(index)!=event.getDamager().getName()){
//                    damagelist.add(event.getDamager().getName());
//                    event.getDamager().sendMessage("（开发者信息）你被添加到了Damage的Map中");
//                    damagemap.put(event.getDamager().getName(),20);
//                }
//            }
        }
    }
    
//    @EventHandler
//    public void onPlayerDamage(EntityDamageEvent event){
//        if (event.getEntity() instanceof Player){
//            try{
//                damagemap.get(event.getEntity().getName());
//            }catch(Exception e){
//                damagelist.add(event.getEntity().getName());
//                damagemap.put(event.getEntity().getName(),100);
//            }
//            damagemap.remove(event.getEntity().getName());
//            damagemap.put(event.getEntity().getName(),100);
//        }
//    }
    
    @EventHandler
    public void ClickontheItem (PlayerInteractEvent event){
        Player p = event.getPlayer();
        if(p.getGameMode()!=GameMode.CREATIVE){
            if (((p.getItemInHand().getTypeId() == 397) && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) || ((p.getItemInHand().getTypeId() == 397) && (event.getAction() == Action.RIGHT_CLICK_AIR))){
    //            int itemtype = p.getItemInHand().getTypeId();
                int speedtime = 15;
                int speedlevel = 2;
                int healthtime = 0;
                int healthlevel = 0;
    //            getServer().dispatchCommand(Bukkit.getConsoleSender(),"effect "+p.getName()+" clear");
                p.removePotionEffect(PotionEffectType.SPEED);
                p.removePotionEffect(PotionEffectType.REGENERATION);
                int itemamount = p.getItemInHand().getAmount();
                if (itemamount==1){
    //                p.getItemInHand().setTypeId(0);
                    ItemStack is = new ItemStack(0,1);
                    p.setItemInHand(is);
                }else{
                    p.getItemInHand().setAmount(itemamount-1);
                }
                event.setCancelled(true);
                addEffect(p,speedtime,speedlevel,healthtime,healthlevel);
            }
        }
        if(p.getItemInHand().getTypeId() == 347&&!inGame(p)&&(event.getAction() == Action.RIGHT_CLICK_BLOCK||event.getAction() == Action.RIGHT_CLICK_AIR)){
            if(p.getItemInHand().getItemMeta().getDisplayName()=="§6§lCPS测试§8(右键开始)"){
                p.chat("/cpstest");
            }
        }if (!inGame(p)&&(event.getAction() == Action.LEFT_CLICK_BLOCK||event.getAction() == Action.LEFT_CLICK_AIR)){
            for (int index=0;index<cpsTestPlayer.size();index++){
                if (cpsTestPlayer.get(index)==p.getName()){
                    int amount = (int)cpsTestAmount.get(p.getName());
                    cpsTestAmount.remove(p.getName());
                    cpsTestAmount.put(p.getName(),++amount);
                    return;
                }
            }
        }
    }
    
    public void cpsTest(Player p){
        for (int index=0;index<cpsTestPlayer.size();index++){
            if (cpsTestPlayer.get(index)==p.getName()){
                p.sendMessage("§4你已经在测试中了");
                return;
            }
        }
        p.sendMessage("§e§lCPS测试 §7> 开始测试,请疯狂左键吧!!!");
        for (int index=0;index<9;index++){
            p.getInventory().setItem(index,air);
        }
        cpsTestPlayer.add(p.getName());
        cpsTestAmount.put(p.getName(), 0);
        new BukkitRunnable(){
            int x = 0;
            boolean end = false;
            String time ="§f➤§f■■■■■■■■■■";
            String clicktext = "§f§l| §e已点击 §f§l";
            int click = 0;
            double cps = 0;
            @Override
            public void run() {
                if (x<200){
//                    try{
                        click = (int)cpsTestAmount.get(p.getName());
//                    }catch(Exception e){}
                    if (x%20==0){
                        if (inGame(p)){
                            cps = click;
                            double y = x;
                            cps=cps/(y/20);
                            end = true;
                        }
                    }
                    switch(x){
                        case 20:
                            time = "§f➤§5■§f■■■■■■■■■";break;
                        case 40:
                            time = "§f➤§5■■§f■■■■■■■■";break;
                        case 60:
                            time = "§f➤§5■■■§f■■■■■■■";break;
                        case 80:
                            time = "§f➤§5■■■■§f■■■■■■";break;
                        case 100:
                            time = "§f➤§5■■■■■§f■■■■■";break;
                        case 120:
                            time = "§f➤§5■■■■■■§f■■■■";break;
                        case 140:
                            time = "§f➤§5■■■■■■■§f■■■";break;
                        case 160:
                            time = "§f➤§5■■■■■■■■§f■■";break;
                        case 180:
                            time = "§f➤§5■■■■■■■■■§f■";break;
                    }
                    try{
                        ActionBar.send(p,time+clicktext+click);
                    }catch(Exception e){p.sendMessage("§4错误:无法测试CPS");cancel();}
                    x++;
                }else{
                    cps = click;
                    cps=cps/10;
                    end = true;
                }if (end){
                    try{
                        ActionBar.send(p,"§e本次平均CPS §f§l"+cps);
                    }catch(Exception e){p.sendMessage("§4错误:无法输出结果");cancel();}
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(1);
                    p.sendMessage("§e§lCPS测试 §7> §f平均CPS "+nf.format(cps)+" §7(MineCraft限制 数据不一定准确)");
                    cpsTestPlayer.remove(p.getName());
                    cpsTestAmount.remove(p.getName());
                    if (!inGame(p)){
                        ItemStack modeChoose = new ItemStack(267,1);
                        ItemMeta im = modeChoose.getItemMeta();
                        im.setDisplayName("§6§l模式选择§8(右键打开)");
                        List<String> lore = new ArrayList<String>();
                        lore.add("§7模式菜单");
                        im.setLore(lore);
                        modeChoose.setItemMeta(im);
                        p.getInventory().setItem(3, modeChoose);
                        ItemStack menu = new ItemStack(340,1);
                        ItemMeta im2 = menu.getItemMeta();
                        im2.setDisplayName("§6§lKarPVP主菜单§8(右键打开)");
                        List<String> lore2 = new ArrayList<String>();
                        lore2.add("§7游戏主菜单");
                        im2.setLore(lore2);
                        menu.setItemMeta(im2);
                        p.getInventory().setItem(5, menu);
                        p.getInventory().setItem(1, cpsTest);
                        p.getInventory().setItem(7, function);
                    }
                    cancel();
                }
            }
        }.runTaskTimer((Plugin)this , 1L ,1L );
    }
    
    public void addEffect (Player p,int speedtime,int speedlevel,int healthtime,int healthlevel){
        new BukkitRunnable(){
            int x = 0;
            @Override
            public void run() {
                if(x == 0){
//                    int speedtime = getConfig().getInt("SpeedTimes");
//                    int speedlevel = getConfig().getInt("SpeedLevel");
//                    int healthtime = getConfig().getInt("HealthTimes");
//                    int healthlevel = getConfig().getInt("HealthLevel");这些变量在函数参数中已被定义，获取方式在被调用的函数中。
                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,speedtime*20,speedlevel-1);
                    PotionEffect health = new PotionEffect(PotionEffectType.REGENERATION,healthtime*20,healthlevel-1);
                    if (speedlevel != 0) {
                        p.addPotionEffect(speed);
                    }
                    if (healthlevel != 0){
                    p.addPotionEffect(health);
                    }
                    x++;
//                     return;
                }
                if(x != 0){
                    x++;
                }
//                    if(x==(getConfig().getInt("SpeedTimes")+2)){
//                        p.removePotionEffect(PotionEffectType.SPEED);
//                }
                    if(x==17){
                        if (!inFullDia(p) && !inCombo(p)){
                            PotionEffect pe = new PotionEffect(PotionEffectType.SPEED,99999,0);
                            p.addPotionEffect(pe);
                        }if (inCombo(p)){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,99999,1));
                    }
                }if(x==18){
                    if (!inFullDia(p) && !inCombo(p)){
                            PotionEffect pe = new PotionEffect(PotionEffectType.SPEED,99999,0);
                            p.addPotionEffect(pe);
                        }if (inCombo(p)){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,99999,1));
                    }
                    cancel();// 终止线程
                }
            }
        }.runTaskTimer((Plugin)this , 1L ,20L );
    }
    
//    public void addDamageList(){
//        new BukkitRunnable(){
//            @Override
//            public void run() {
////                for (Map.Entry<String, String> entry : cool.entrySet()) {
////                    int amount=0;
////                    amount=(int)cool.get(p.getName());
////                    cool.remove(p.getName());
////                    if((int)cool.get(p.getName())!=0)
////                    cool.put(p.getName(), amount-1);
////                }
//                for(Map.Entry<String, int> entry:cool.entrySet()){
//                    int amount=0;
//                    amount=cool.get(Integer.parseInt(entry.getKey()));
//                    cool.remove(p.getName());
//                    if((int)cool.get(p.getName())!=0)
//                    cool.put(p.getName(), amount-1);
//                    System.out.println(entry.getKey()+"--->"+entry.getValue());    
//               }   
//            }
//        }.runTaskTimer((Plugin)this ,0L ,20L );
//        new BukkitRunnable(){
//            @Override
//            public void run() {
////                while(it.hasNext()){
////                    Player p = getPlayer(it.next());
////                    String pn = p.getName();
////                    if (inGapple(p)){
////                        Location lc = new Location(gappleLocation.getWorld(),7.5,48,7.5);
////                        p.sendBlockChange(lc, Material.GLASS, (byte)4);
////                    }
////                }
//                for (Player p : Bukkit.getOnlinePlayers()){
//                    try{
//                        if (cool.get(p.getName())!="0"){
//                            Location lc = new Location(gappleLocation.getWorld(),7.5,48,7.5);
//                            p.sendBlockChange(lc, Material.GLASS, (byte)4);
//                        }
//                    }catch(Exception e){}
//                }
//            }
//        }.runTaskTimer((Plugin)this ,0L ,5L );
//    }
    
    public void setup(){
        getLogger().info("生成装备信息...");
        long startTime = System.currentTimeMillis();
        try {
            ItemMeta im = function.getItemMeta();
            im.setDisplayName("§a§l功能§8(右键打开)");
            function.setItemMeta(im);
            
            ItemMeta cpsTestMeta = cpsTest.getItemMeta();
            cpsTestMeta.setDisplayName("§6§lCPS测试§8(右键开始)");
            cpsTest.setItemMeta(cpsTestMeta);
            ItemMeta sharpness = sword.getItemMeta();
            ItemMeta sharpness3 = sword.getItemMeta();
            ItemMeta sharpness1 = sword.getItemMeta();
            ItemMeta sharpness5 = sword.getItemMeta();
            ItemMeta fr = fishing_rod.getItemMeta();
            ItemMeta protetion1 = helmet.getItemMeta();
            ItemMeta protetion2 = chest_plate.getItemMeta();
            ItemMeta potionprotetion = chest_plate.getItemMeta();
            ItemMeta protetion4 = chest_plate.getItemMeta();
            ItemMeta nj = helmet.getItemMeta();
            ItemMeta power = bow.getItemMeta();
            sharpness.spigot().setUnbreakable(true);sharpness1.spigot().setUnbreakable(true);sharpness3.spigot().setUnbreakable(true);sharpness5.spigot().setUnbreakable(true);
            fr.spigot().setUnbreakable(true);nj.spigot().setUnbreakable(true);power.spigot().setUnbreakable(true);
            protetion1.spigot().setUnbreakable(true);protetion2.spigot().setUnbreakable(true);
            sharpness.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
//            sharpness.addEnchant(Enchantment.DURABILITY, 3, true);
            sharpness5.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            sharpness5.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
//            sharpness5.addEnchant(Enchantment.DURABILITY, 3, true);
            sharpness3.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
//            sharpness3.addEnchant(Enchantment.DURABILITY, 3, true);
            sharpness1.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
//            sharpness1.addEnchant(Enchantment.DURABILITY, 3, true);
//            nj.addEnchant(Enchantment.DURABILITY, 3, true);
//            fr.addEnchant(Enchantment.DURABILITY, 3, true);
            protetion1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
//            protetion1.addEnchant(Enchantment.DURABILITY, 3, true);
            protetion2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            potionprotetion.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            potionprotetion.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
            potionprotetion.spigot().setUnbreakable(true);
//            protetion2.addEnchant(Enchantment.DURABILITY, 3, true);
            protetion4.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
//            protetion4.addEnchant(Enchantment.DURABILITY, 3, true);
            power.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
//            power.addEnchant(Enchantment.DURABILITY, 3, true);
            sword.setItemMeta(sharpness);
            sword1.setItemMeta(sharpness1);
            sword2.setItemMeta(sharpness3);
            combosword.setItemMeta(sharpness5);
            fishing_rod.setItemMeta(fr);
            helmet.setItemMeta(protetion1);
            chest_plate.setItemMeta(protetion2);
            leggings.setItemMeta(protetion1);
            boots.setItemMeta(protetion1);
            helmet2.setItemMeta(nj);
            chest_plate2.setItemMeta(nj);
            leggings2.setItemMeta(nj);
            boots2.setItemMeta(nj);
            potionhelmet.setItemMeta(potionprotetion);
            potionchest_plate.setItemMeta(potionprotetion);
            potionleggings.setItemMeta(potionprotetion);
            potionboots.setItemMeta(potionprotetion);
            combohelmet.setItemMeta(protetion4);
            combochest_plate.setItemMeta(protetion4);
            comboleggings.setItemMeta(protetion4);
            comboboots.setItemMeta(protetion4);
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
        getLogger().info("生成配置信息...");
        try{
//            String lobbyworldname = getConfig().getString("LobbyLocation_world");
//            World lobbyworld = getWorld(lobbyworldname);
//            double lobbyx = getConfig().getDouble("LobbyLocation_x");
//            double lobbyy = getConfig().getDouble("LobbyLocation_y");
//            double lobbyz = getConfig().getDouble("LobbyLocation_z");
//            float lobbyyam = (float)getConfig().getDouble("LobbyLocation_yam");
//            float lobbydx = (float)getConfig().getDouble("LobbyLocation_dx");
//            lobbyLocation = new Location(lobbyworld,lobbyx,lobbyy,lobbyz,lobbyyam,lobbydx);
            lobbyLocation = new Location(getWorld("lobby"),0.5,52,0.5,180,0);
//            String gappleworldname = getConfig().getString("GappleLocation_world");
//            World gappleworld = getWorld(gappleworldname);
//            double gapplex = getConfig().getDouble("GappleLocation_x");
//            double gappley = getConfig().getDouble("GappleLocation_y");
//            double gapplez = getConfig().getDouble("GappleLocation_z");
//            float gappleyam = (float)getConfig().getDouble("GappleLocation_yam");
//            float gappledx = (float)getConfig().getDouble("GappleLocation_dx");
//            gappleLocation = new Location(gappleworld,gapplex,gappley,gapplez,gappleyam,gappledx);
            gappleLocation = new Location(getWorld("Gapple"),0.5,66,0.5,270,0);
//            String potionworldname = getConfig().getString("PotionLocation_world");
//            World potionworld = getWorld(potionworldname);
//            double potionx = getConfig().getDouble("PotionLocation_x");
//            double potiony = getConfig().getDouble("PotionLocation_y");
//            double potionz = getConfig().getDouble("PotionLocation_z");
//            float potionyam = (float)getConfig().getDouble("PotionLocation_yam");
//            float potiondx = (float)getConfig().getDouble("PotionLocation_dx");
//            potionLocation = new Location(potionworld,potionx,potiony,potionz,potionyam,potiondx);
            potionLocation = new Location(getWorld("Potion"),0.5,66,0.5,270,0);
//            String fulldiaworldname = getConfig().getString("FullDiaLocaiton_world");
//            World fulldiaworld = getWorld(fulldiaworldname);
//            double fulldiax = getConfig().getDouble("FullDiaLocaiton_x");
//            double fulldiay = getConfig().getDouble("FullDiaLocaiton_y");
//            double fulldiaz = getConfig().getDouble("FullDiaLocaiton_z");
//            float fulldiayam = (float)getConfig().getDouble("FullDiaLocaiton_yam");
//            float fulldiadx = (float)getConfig().getDouble("FullDiaLocaiton_dx");
//            fulldiaLocation = new Location(fulldiaworld,fulldiax,fulldiay,fulldiaz,fulldiayam,fulldiadx);
            fulldiaLocation = new Location(getWorld("FullDia"),0.5,48,0.5,180,0);
            comboLocation = new Location(getWorld("Combo"),0.5,66,0.5,270,0);
            lobbyGameMode = getConfig().getInt("LobbyGameMode");
            reSpawnGameMode = getConfig().getInt("RespawnGameMode");
            reSpawnTime = getConfig().getInt("RespawnTime");
            getLogger().info("配置信息生成成功!");
        }catch(Exception e){
            getLogger().info("配置信息生成失败,已自动卸载插件");
            try {
                getServer().getPluginManager().disablePlugin(this);
            }catch(Exception ex){
                getLogger().info("插件卸载失败");
            }
        }
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        getLogger().info("插件信息已全部生成完毕!用时"+time+"ms");
        new BukkitRunnable(){
            @Override
            public void run() {
                for(int index=0;index<damagelist.size();index++){
                    int amount = (int)damagemap.get(damagelist.get(index).toString());
                    if (amount!=0){
                        damagemap.put(damagelist.get(index).toString(),amount-1);
                    }
                    if ((int)damagemap.get(damagelist.get(index).toString())>1){
                        sendBlockChange(damagelist.get(index));
                    }else if((int)damagemap.get(damagelist.get(index).toString())==1){
                        removeBlockChange(damagelist.get(index));
                    }
                }
            }
        }.runTaskTimer((Plugin)this ,0L ,5L );
        for (Player p : Bukkit.getOnlinePlayers()){
            for (int index=0;index<damagelist.size();index++){
                if (damagelist.get(index)==p.getName()){
                    break;
                }if(index==damagelist.size()&&damagelist.get(index)!=p.getName()){
                    damagelist.add(p.getName());
                }
            }
        }
    }
    
    public void sendBlockChange(String name){
        Player p = getPlayer(name);
        Location lc = p.getLocation();
        if (inGapple(p)){
            for (double x=-6.5;x<8.5;x++){
                if (lc.getBlockX()>-6&&lc.getBlockX()<7&&lc.getBlockZ()>7&&lc.getBlockZ()<12){
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),62,7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),63,7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),64,7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),65,7), Material.STAINED_GLASS, (byte)4);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,62,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,63,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,64,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,65,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,62,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,63,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,64,7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,65,7), Material.STAINED_GLASS, (byte)4);
                    }
                }if (lc.getBlockX()>7&&lc.getBlockX()<12&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),7,62,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),7,63,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),7,64,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),7,65,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,62,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,63,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,64,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,65,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,62,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,63,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,64,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),7,65,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                    }
                }if (lc.getBlockZ()>-11&&lc.getBlockZ()<-7&&lc.getBlockX()<7&&lc.getBlockX()>-6){
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),62,-7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),63,-7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),64,-7), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX(),65,-7), Material.STAINED_GLASS, (byte)4);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,62,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,63,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,64,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()+1,65,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,62,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,63,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,64,-7), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),lc.getBlockX()-1,65,-7), Material.STAINED_GLASS, (byte)4);
                    }
                }if (lc.getBlockX()>-11&&lc.getBlockX()<-7&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,62,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,63,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,64,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,65,lc.getBlockZ()), Material.STAINED_GLASS, (byte)4);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,62,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,63,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,64,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,65,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,62,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,63,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,64,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                        p.sendBlockChange(new Location(gappleLocation.getWorld(),-7,65,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)4);
                    }
                }
//                if(lc.getBlockX()<7&&lc.getBlockX()>-7&&lc.getBlockZ()<7&&lc.getBlockZ()>-7){
////                    p.teleport(gappleLocation);
//                    Location lc2 = p.getLocation();
//                    lc2.setY(lc2.getY()+3);
//                    lc2.setX(lc2.getX()+3);
//                    lc2.setZ(lc2.getZ()+3);
////                    p.setVelocity(p.getLocation().getDirection().setY(0.3).multiply(8));
////                    p.setVelocity(lc2.getDirection().setZ(-0.3).multiply(2));
////                    p.setVelocity(lc2.getDirection().setY(0.2).multiply(2));
//                    p.setVelocity(p.getLocation().getDirection().multiply(-3D).setY(2D));
//                    if ((int)(Math.random()*2)==0)
//                        p.playSound(p.getLocation(), org.bukkit.Sound.VILLAGER_YES, 0.5f, 1f);
//                    else
//                        p.playSound(p.getLocation(), org.bukkit.Sound.VILLAGER_NO, 0.5f, 1f);
//                }
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,48,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,49,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,50,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,51,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,48,7.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,49,7.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,50,7.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,51,7.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,48,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,49,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,50,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,51,x), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,48,-6.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,49,-6.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,50,-6.5), Material.STAINED_GLASS, (byte)4);
//                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,51,-6.5), Material.STAINED_GLASS, (byte)4);
            }
        }else if (inPotion(p)){
//            Location lc = p.getLocation();
            for (double x=-6.5;x<8.5;x++){
                if (lc.getBlockX()>-6&&lc.getBlockX()<7&&lc.getBlockZ()>7&&lc.getBlockZ()<12){
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),48,7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),49,7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),50,7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),51,7), Material.STAINED_GLASS, (byte)14);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,48,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,49,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,50,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,51,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,48,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,49,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,50,7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,51,7), Material.STAINED_GLASS, (byte)14);
                    }
                }if (lc.getBlockX()>7&&lc.getBlockX()<12&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(potionLocation.getWorld(),7,48,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),7,49,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),7,50,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),7,51,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,48,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,49,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,50,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,51,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,48,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,49,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,50,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),7,51,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                    }
                }if (lc.getBlockZ()>-11&&lc.getBlockZ()<-7&&lc.getBlockX()<7&&lc.getBlockX()>-6){
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),48,-7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),49,-7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),50,-7), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX(),51,-7), Material.STAINED_GLASS, (byte)14);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,48,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,49,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,50,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()+1,51,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,48,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,49,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,50,-7), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),lc.getBlockX()-1,51,-7), Material.STAINED_GLASS, (byte)14);
                    }
                }if (lc.getBlockX()>-11&&lc.getBlockX()<-7&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(potionLocation.getWorld(),-7,48,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),-7,49,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),-7,50,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    p.sendBlockChange(new Location(potionLocation.getWorld(),-7,51,lc.getBlockZ()), Material.STAINED_GLASS, (byte)14);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,48,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,49,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,50,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,51,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,48,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,49,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,50,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                        p.sendBlockChange(new Location(potionLocation.getWorld(),-7,51,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)14);
                    }
                }
            }
        }else if (inFullDia(p)){
            for (double x=-6.5;x<8.5;x++){
//                Location lc = p.getLocation();
                if (lc.getBlockX()>-6&&lc.getBlockX()<7&&lc.getBlockZ()>7&&lc.getBlockZ()<12){
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),44,7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),45,7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),46,7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),57,7), Material.STAINED_GLASS, (byte)3);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,44,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,45,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,46,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,47,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,44,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,45,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,46,7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,47,7), Material.STAINED_GLASS, (byte)3);
                    }
                }if (lc.getBlockX()>7&&lc.getBlockX()<12&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,44,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,45,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,46,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,47,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,44,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,45,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,46,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,47,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,44,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,45,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,46,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7,47,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                    }
                }if (lc.getBlockZ()>-11&&lc.getBlockZ()<-7&&lc.getBlockX()<7&&lc.getBlockX()>-6){
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),44,-7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),45,-7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),46,-7), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX(),46,-7), Material.STAINED_GLASS, (byte)3);
                    if(lc.getBlockX()>-6&&lc.getBlockX()<7){
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,44,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,45,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,46,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()+1,47,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,44,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,45,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,46,-7), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),lc.getBlockX()-1,47,-7), Material.STAINED_GLASS, (byte)3);
                    }
                }if (lc.getBlockX()>-11&&lc.getBlockX()<-7&&lc.getBlockZ()<7&&lc.getBlockZ()>-6){
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,44,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,45,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,46,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,47,lc.getBlockZ()), Material.STAINED_GLASS, (byte)3);
                    if(lc.getBlockZ()>-6&&lc.getBlockZ()<7){
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,44,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,45,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,46,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,47,lc.getBlockZ()+1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,44,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,45,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,46,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                        p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-7,47,lc.getBlockZ()-1), Material.STAINED_GLASS, (byte)3);
                    }
                }
            }
        }
        if((lc.getBlockX()<7&&lc.getBlockX()>-7&&lc.getBlockZ()<7&&lc.getBlockZ()>-7)&&(lc.getWorld()==gappleLocation.getWorld()||lc.getWorld()==potionLocation.getWorld()||lc.getWorld()==fulldiaLocation.getWorld())){
//                    p.teleport(gappleLocation);
//            Location lc2 = p.getLocation();
//            lc2.setY(lc2.getY()+3);
//            lc2.setX(lc2.getX()+3);
//            lc2.setZ(lc2.getZ()+3);
//                    p.setVelocity(p.getLocation().getDirection().setY(0.3).multiply(8));
//            p.setVelocity(lc2.getDirection().setZ(-0.3).multiply(2));
//            p.setVelocity(lc2.getDirection().setY(0.2).multiply(2));
            p.setVelocity(p.getLocation().getDirection().multiply(-3D).setY(0.3D));
            if ((int)(Math.random()*2)==0)
                p.playSound(p.getLocation(), org.bukkit.Sound.VILLAGER_YES, 0.5f, 1f);
            else
                p.playSound(p.getLocation(), org.bukkit.Sound.VILLAGER_NO, 0.5f, 1f);
            if (inFullDia(p)){
                if (p.getLocation().getBlockY()==49){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,60,1));
                }
            }else{
                if (p.getLocation().getBlockY()==68){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,60,1));
                }
            }
        }
    }
    public void removeBlockChange(String name){
        Player p = getPlayer(name);
        if (inGapple(p)){
            for (double x=-6.5;x<8.5;x++){
                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,48,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,49,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,50,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),7.5,51,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,48,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,49,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,50,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,51,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,48,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,49,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,50,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),-6.5,51,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,48,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,49,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,50,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(gappleLocation.getWorld(),x,51,-6.5), Material.AIR, (byte)4);
            }
        }else if (inPotion(p)){
            for (double x=-6.5;x<8.5;x++){
                p.sendBlockChange(new Location(potionLocation.getWorld(),7.5,48,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),7.5,49,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),7.5,50,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),7.5,51,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,48,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,49,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,50,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,51,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),-6.5,48,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),-6.5,49,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),-6.5,50,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),-6.5,51,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,48,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,49,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,50,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(potionLocation.getWorld(),x,51,-6.5), Material.AIR, (byte)4);
            }
        }else if (inFullDia(p)){
            for (double x=-6.5;x<8.5;x++){
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7.5,44,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7.5,45,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7.5,46,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),7.5,47,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,44,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,45,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,46,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,47,7.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-6.5,44,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-6.5,45,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-6.5,46,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),-6.5,47,x), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,44,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,45,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,46,-6.5), Material.AIR, (byte)4);
                p.sendBlockChange(new Location(fulldiaLocation.getWorld(),x,47,-6.5), Material.AIR, (byte)4);
            }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("cpstest")) {
            if (sender instanceof Player){
                if (inGame((Player)sender)){
                    sender.sendMessage("§4请在大厅里使用");
                    return true;
                }
                cpsTest((Player)sender);
            }else{
                sender.sendMessage("§4不能在控制台使用");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("backlobby")) {
            if (sender instanceof Player){
                Player p = (Player)sender;
                backLobbyByPlayer(p);
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("ffam")) {
            sender.sendMessage("inGapple"+ga);
            sender.sendMessage("inPotion"+po);
            sender.sendMessage("inFullDia"+fd);
            sender.sendMessage("inCombo"+cb);
            sender.sendMessage("§4inDamage§f"+damagemap);
            sender.sendMessage("§4inDamageList§f"+damagelist);
            Player p = (Player)sender;
            return true;
        }if (cmd.getName().equalsIgnoreCase("fd")) {
            if(args.length==1){
                Player p;
                try{
                p = getPlayer(args[0]);
                }catch(Exception e){sender.sendMessage("§4玩家不在线");return true;}
                if (inGapple(p)){
                    sender.sendMessage("§e§l"+args[0]+"§7在§eGapple§7里");
                }else if(inPotion(p)){
                    sender.sendMessage("§e§l"+args[0]+"§7在§cPotion§7里");
                }else if(inFullDia(p)){
                    sender.sendMessage("§e§l"+args[0]+"§7在§1FullDia§7里");
                }else if(inCombo(p)){
                    sender.sendMessage("§e§l"+args[0]+"§7在§4Combo§7里");
                }else{
                    sender.sendMessage("§e§l"+args[0]+"§7在§fLobby§7里");
                }
                return true;
            }
            sender.sendMessage("/find <name>");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("mode")) {
            if(args.length>0){
                if (args[0].equalsIgnoreCase("gapple")) {
                    if (args.length==1){
                        if ((sender instanceof Player)) {
                            joinGapple((Player)sender);
                            return true;
                        }else{sender.sendMessage("§4必须是玩家");return true;}
                    }if (args.length==2){
                        try{
                            Player pl = getPlayer(args[1]);
                            joinGapple(pl);
                            sender.sendMessage("§a成功将此玩家加入此模式！");
                            pl.sendMessage("§6你被强制加入了模式Gapple");
                            return true;
                        }catch(Exception e){
                            sender.sendMessage("§4找不到该玩家");
                            return true;
                        }
                    }
                    sender.sendMessage("§e/mode gapple <player>");
                    return true;
                }if (args[0].equalsIgnoreCase("potion")) {
                    if (args.length==1){
                        if ((sender instanceof Player)) {
                            joinPotion((Player)sender);
                            return true;
                        }else{sender.sendMessage("§4必须是玩家");return true;}
                    }if (args.length==2){
                        try{
                            Player pl = getPlayer(args[1]);
                            joinPotion(pl);
                            sender.sendMessage("§a成功将此玩家加入此模式！");
                            pl.sendMessage("§6你被强制加入了模式Potion");
                            return true;
                        }catch(Exception e){
                            sender.sendMessage("§4找不到该玩家");
                            return true;
                        }
                    }
                    sender.sendMessage("§e/mode potion <player>");
                    return true;
                }
                if (args[0].equalsIgnoreCase("fulldia")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                joinFullDia((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length==2){
                            try{
                                Player pl = getPlayer(args[1]);
                                joinFullDia(pl);
                                sender.sendMessage("§a成功将此玩家加入此模式！");
                                pl.sendMessage("§6你被强制加入了模式FullDiamond");
                                return true;
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                        }
                        sender.sendMessage("§e/mode fulldia <player>");
                        return true;
                    }
                if (args[0].equalsIgnoreCase("combo")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                joinCombo((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length==2){
                            try{
                                Player pl = getPlayer(args[1]);
                                joinCombo(pl);
                                sender.sendMessage("§a成功将此玩家加入此模式！");
                                pl.sendMessage("§6你被强制加入了模式Combo");
                                return true;
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                        }
                        sender.sendMessage("§e/mode combo <player>");
                        return true;
                    }
                    sender.sendMessage("§6§l目前模式 §7> §eGapple §f| §cPotion §f| §9FullDia §f| §6Combo");
                    return true;
                }
                sender.sendMessage("/mode <mode> [player]");
                return true;
            }if (cmd.getName().equalsIgnoreCase("kit")){
                if(args.length>0){
                    if (args[0].equalsIgnoreCase("gapple")) {
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getGappleKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if(args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getGappleKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成Gapple！");
                                pl.sendMessage("§6你被强制替换装备为Gapple");
                                return true;
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                        }
                        sender.sendMessage("§e/kit gapple <player>");
                        return true;
                    }if (args[0].equalsIgnoreCase("potion")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getPotionKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if (args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getPotionKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成Potion！");
                                pl.sendMessage("§6你被强制替换装备为Potion");
                                return true;
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                        }
                        sender.sendMessage("§e/kit potion <player>");
                        return true;
                    }if (args[0].equalsIgnoreCase("fulldia")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getFullDiaKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if(args.length==2){
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
                        sender.sendMessage("§e/kit fulldia <player>");
                        return true;
                    }if (args[0].equalsIgnoreCase("combo")){
                        if (args.length==1){
                            if ((sender instanceof Player)) {
                                getComboKit((Player)sender);
                                return true;
                            }else{sender.sendMessage("§4必须是玩家");return true;}
                        }if(args.length==2){
                            try{
                                Player pl=getPlayer(args[1]);
                                getComboKit(pl);
                                sender.sendMessage("§a成功将此玩家的装备替换成Combo！");
                                pl.sendMessage("§6你被强制替换装备为Combo");
                            }catch(Exception e){
                                sender.sendMessage("§4找不到该玩家");
                                return true;
                            }
                            return true;
                        }
                        sender.sendMessage("§e/kit combo <player>");
                        return true;
                    }
                    sender.sendMessage("§6§l目前模式 §7> §eGapple §f| §cPotion §f| §9FullDia §f| §6Combo");
                    return true;
                }
                sender.sendMessage("§e/kit <kit> [player]");
                return true;
            }if (cmd.getName().equalsIgnoreCase("spawn")){
                if(args.length==0){
                    if ((sender instanceof Player)) {
                        backLobby((Player)sender);
                        sender.sendMessage("§6成功将您传送到主出生点");
                        return true;
                    }else{sender.sendMessage("§4必须是玩家");return true;}
                }if(args.length==1){
                    Player pr=getPlayer(args[0]);
                    backLobby(pr);
                    sender.sendMessage("§a成功将此玩家传送到出生点");
                    pr.sendMessage("§6你被强制传送到了出生点");
                    return true;
                }
                sender.sendMessage("§e/spawn [player]");
                return true;
            }if (cmd.getName().equalsIgnoreCase("setspawn")){
                if(args.length>0){
                    if ((sender instanceof Player)) {
                        if (args[0].equalsIgnoreCase("lobby")){
                            Player p = (Player)sender;
                            Location here = p.getLocation();
                            World world = p.getWorld();
                            String worldname = world.getName();
                            double x = here.getX();
                            double y = here.getY();
                            double z = here.getZ();
                            float yam = here.getYaw();
                            float dx = here.getPitch();
                            getConfig().set("LobbyLocation_world", worldname);
                            getConfig().set("LobbyLocation_x", x);
                            getConfig().set("LobbyLocation_y", y);
                            getConfig().set("LobbyLocation_z", z);
                            getConfig().set("LobbyLocation_yam", yam);
                            getConfig().set("LobbyLocation_dx", dx);
                            lobbyLocation = new Location(world,x,y,z,yam,dx);
                            sender.sendMessage("§e成功设置lobby的出生点");
                            return true;
                        }if (args[0].equalsIgnoreCase("gapple")){
                            Player p = (Player)sender;
                            Location here = p.getLocation();
                            World world = p.getWorld();
                            String worldname = world.getName();
                            double x = here.getX();
                            double y = here.getY();
                            double z = here.getZ();
                            float yam = here.getYaw();
                            float dx = here.getPitch();
                            getConfig().set("GappleLocation_world", worldname);
                            getConfig().set("GappleLocation_x", x);
                            getConfig().set("GappleLocation_y", y);
                            getConfig().set("GappleLocation_z", z);
                            getConfig().set("GappleLocation_yam", yam);
                            getConfig().set("GappleLocation_dx", dx);
                            gappleLocation = new Location(world,x,y,z,yam,dx);
                            sender.sendMessage("§e成功设置gapple的出生点");
                            return true;
                        }if (args[0].equalsIgnoreCase("potion")){
                            Player p = (Player)sender;
                            Location here = p.getLocation();
                            World world = p.getWorld();
                            String worldname = world.getName();
                            double x = here.getX();
                            double y = here.getY();
                            double z = here.getZ();
                            float yam = here.getYaw();
                            float dx = here.getPitch();
                            getConfig().set("PotionLocation_world", worldname);
                            getConfig().set("PotionLocation_x", x);
                            getConfig().set("PotionLocation_y", y);
                            getConfig().set("PotionLocation_z", z);
                            getConfig().set("PotionLocation_yam", yam);
                            getConfig().set("PotionLocation_dx", dx);
                            gappleLocation = new Location(world,x,y,z,yam,dx);
                            sender.sendMessage("§e成功设置potion的出生点");
                            return true;
                        }if (args[0].equalsIgnoreCase("fulldia")){
                            Player p = (Player)sender;
                            Location here = p.getLocation();
                            World world = p.getWorld();
                            String worldname = world.getName();
                            double x = here.getX();
                            double y = here.getY();
                            double z = here.getZ();
                            float yam = here.getYaw();
                            float dx = here.getPitch();
                            getConfig().set("FullDiaLocaiton_world", String.valueOf(worldname));
                            getConfig().set("FullDiaLocaiton_x", Double.valueOf(x));
                            getConfig().set("FullDiaLocaiton_y", Double.valueOf(y));
                            getConfig().set("FullDiaLocaiton_z", Double.valueOf(z));
                            getConfig().set("FullDiaLocaiton_yam", Float.valueOf(yam));
                            getConfig().set("FullDiaLocaiton_dx", Float.valueOf(dx));
                            gappleLocation = here;
                            sender.sendMessage(""+gappleLocation);
                            sender.sendMessage("§e成功设置fulldia的出生点");
                            return true;
                        }
                        sender.sendMessage("§e/setspawn <lobby/gapple/potion/fulldia/combo>  在你当前的位置设置某模式出生点");
                        return true;
                    }else{sender.sendMessage("§4必须是玩家");return true;}
                }
            sender.sendMessage("§e/setspawn <lobby/gapple/potion/fulldia/combo>  在你当前的位置设置某模式出生点");
            return true;
        }
        return true;
    }

    public void backLobbyByPlayer(Player p){
        if (!(p.getHealth()<20)){
            backLobby(p);
        }else{
            p.sendMessage("§4请在满血的情况下再回主城");
        }
    }
    
    public static void joinGapple(Player p){
        getGappleKit(p);
        p.teleport(gappleLocation);
        p.setGameMode(GameMode.SURVIVAL);
        quit(p.getName());
        ga.add(p.getName());
        p.getPlayer().setMaximumNoDamageTicks(18);
    }
    public static void joinPotion(Player p){
        getPotionKit(p);
        p.teleport(potionLocation);
        p.setGameMode(GameMode.SURVIVAL);
        quit(p.getName());
        po.add(p.getName());
        p.getPlayer().setMaximumNoDamageTicks(18);
    }
    public static void joinFullDia(Player p){
        getFullDiaKit(p);
        p.teleport(fulldiaLocation);
        p.setGameMode(GameMode.SURVIVAL);
        quit(p.getName());
        fd.add(p.getName());
        p.getPlayer().setMaximumNoDamageTicks(18);
    }
    public static void joinCombo(Player p){
        getComboKit(p);
        p.teleport(comboLocation);
        p.setGameMode(GameMode.SURVIVAL);
        quit(p.getName());
        cb.add(p.getName());
        p.getPlayer().setMaximumNoDamageTicks(1);
    }
    
    public static void getGappleKit(Player p){
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
    public static void getPotionKit(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,999999,1));
        p.getInventory().setItem(0,sword2);
        p.getInventory().setItem(1,ender_pearl16);
        p.getInventory().setItem(8,beef);
        p.getInventory().setItem(39,potionhelmet);
        p.getInventory().setItem(38,potionchest_plate);
        p.getInventory().setItem(37,potionleggings);
        p.getInventory().setItem(36,potionboots);
        try{
            for (int x=2;x<8;x++){
                p.getInventory().setItem(x,potion);
            }
            for (int x=9;x<36;x++){
                p.getInventory().setItem(x,potion);
            }
        }catch(Exception ex){}
    }
    public static void getFullDiaKit(Player p){
        p.removePotionEffect(PotionEffectType.SPEED);
        p.getInventory().setItem(0,sword1);
        p.getInventory().setItem(1,fishing_rod);
        p.getInventory().setItem(2,bow);
        p.getInventory().setItem(3,potion2);
        p.getInventory().setItem(4,potion2);
        p.getInventory().setItem(5,potion2);
        p.getInventory().setItem(6,potion2);
        p.getInventory().setItem(7,beef);
        p.getInventory().setItem(8,ender_pearl);
        p.getInventory().setItem(9,arrow);
        p.getInventory().setItem(10,arrow);
        p.getInventory().setItem(39,helmet);
        p.getInventory().setItem(38,chest_plate);
        p.getInventory().setItem(37,leggings);
        p.getInventory().setItem(36,boots);
        for (int x=11;x<36;x++){
            try{
                p.getInventory().setItem(x,air);
            }catch(Exception e){}
        }
    }
    
    public static void getComboKit(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,999999,1));
        p.getInventory().setItem(0,combosword);
        p.getInventory().setItem(1,xgolden_apple);
        p.getInventory().setItem(2,combohelmet);
        p.getInventory().setItem(3,combochest_plate);
        p.getInventory().setItem(4,comboleggings);
        p.getInventory().setItem(5,comboboots);
        for (int x=6;x<36;x++){
            try{
                p.getInventory().setItem(x,air);
            }catch(Exception e){}
        }
        p.getInventory().setItem(39,combohelmet);
        p.getInventory().setItem(38,combochest_plate);
        p.getInventory().setItem(37,comboleggings);
        p.getInventory().setItem(36,comboboots);
    }
    
    public void backLobby(Player p){
        p.setMaximumNoDamageTicks(18);
        p.teleport(lobbyLocation);
        quit(p.getName());
        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,999999,1));
        getLobbyItem(p);
    }
    
    public void getLobbyItem(Player p){
        for (int i= 0;i<40;i++){
            try{
                p.getInventory().setItem(i, air);
            }catch(Exception e){}
        }
        new BukkitRunnable(){
            int t = 0;
            public void run() {
                if (t<2)
                    t++;
                if(t>=2){
                    ItemStack modeChoose = new ItemStack(267,1);
                    ItemMeta im = modeChoose.getItemMeta();
                    im.setDisplayName("§6§l模式选择§8(右键打开)");
                    modeChoose.setItemMeta(im);
                    p.getInventory().setItem(3, modeChoose);
                    ItemStack menu = new ItemStack(340,1);
                    ItemMeta im2 = menu.getItemMeta();
                    im2.setDisplayName("§6§lKarPVP主菜单§8(右键打开)");
                    List<String> lore2 = new ArrayList<String>();
                    lore2.add("§7游戏主菜单");
                    im2.setLore(lore2);
                    menu.setItemMeta(im2);
                    p.getInventory().setItem(5, menu);
                    p.getInventory().setItem(1, cpsTest);
                    p.getInventory().setItem(7, function);
                    p.removePotionEffect(PotionEffectType.SPEED);
                    cancel();
                }
            }
        }.runTaskTimer((Plugin)this , 0L ,20L );
    }
    
    public static boolean inGapple(Player p){
        String pn = p.getName();
        for (int index=0;index<ga.size();index++){
            if (ga.get(index)==pn){
                return true;
            }
        }
        return false;
    }public static boolean inPotion(Player p){
        String pn = p.getName();
        for (int index=0;index<po.size();index++){
            if (po.get(index)==pn){
                return true;
            }
        }
        return false;
    }public static boolean inFullDia(Player p){
        String pn = p.getName();
        for (int index=0;index<fd.size();index++){
            if (fd.get(index)==pn){
                return true;
            }
        }
        return false;
    }public static boolean inCombo(Player p){
        String pn = p.getName();
        for (int index=0;index<cb.size();index++){
            if (cb.get(index)==pn){
                return true;
            }
        }
        return false;
    }
    public static boolean inGame(Player p){
        if(inGapple(p)||inPotion(p)||inFullDia(p)||inCombo(p))
            return true;
        else
            return false;
    }
    
    public static void quit (String p){
//        int max = 0;
//        max = ga.size()>po.size()?ga.size():po.size();
//        max = max>fd.size()?max:fd.size();
//        max = max>cb.size()?max:cb.size();
//        try {
//            for (int index=0;index<max;index++){
//                if (ga.size()<index&&ga.get(index)==p){
//                    ga.remove(index);
//                    return;
//                }if (po.size()<index&&po.get(index)==p){
//                    po.remove(index);
//                    return;
//                }if (fd.size()<index&&fd.get(index)==p){
//                    fd.remove(index);
//                    return;
//                }if (cb.size()<index&&cb.get(index)==p){
//                    cb.remove(index);
//                    return;
//                }
//            }
//        }catch(Exception e){}
        getPlayer(p).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        getPlayer(p).removePotionEffect(PotionEffectType.SATURATION);
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
            }for (int index=0;index<cb.size();index++){
                if (cb.get(index)==p){
                    cb.remove(index);
                    return;
                }
            }
        }catch (Exception e){}
    }
}
