package top.karpvp.normalpvp.pvpmode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class ActionBar
{
  private static String version = "";
  private static Object packet;
  private static Method getHandle;
  private static Method sendPacket;
  private static Field playerConnection;
  private static Class<?> nmsChatSerializer;
  private static Class<?> nmsIChatBaseComponent;
  private static Class<?> packetType;

  public static void send(Player paramPlayer, String paramString) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
  {
    Object localObject3;
    try
    {
      Object localObject1 = nmsChatSerializer.getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', JSONObject.escape(paramString)) + "\"}" });
      if (!version.contains("1_7")){
        packet = packetType.getConstructor(new Class[] { nmsIChatBaseComponent, Byte.TYPE }).newInstance(new Object[] { localObject1, Byte.valueOf((byte)2) });
      }else {
        packet = packetType.getConstructor(new Class[] { nmsIChatBaseComponent, Integer.TYPE }).newInstance(new Object[] { localObject1, Integer.valueOf(2) });
      }
      localObject3 = getHandle.invoke(paramPlayer, new Object[0]);
      Object localObject4 = playerConnection.get(localObject3);
      sendPacket.invoke(localObject4, new Object[] { packet });
    } catch (java.lang.NoSuchMethodException localNoSuchMethodException) {
      Bukkit.getLogger().log(Level.SEVERE, "Error {0} " + version, localNoSuchMethodException);
    }
    try
    {
      Object localObject2 = getHandle.invoke(paramPlayer, new Object[0]);
      localObject3 = playerConnection.get(localObject2);
      sendPacket.invoke(localObject3, new Object[] { packet });
    } catch (java.lang.IllegalAccessException localSecurityException) {
      Bukkit.getLogger().log(Level.SEVERE, "Error {0}", localSecurityException);
    }
  }

  private static String getCraftPlayerClasspath() {
    return "org.bukkit.craftbukkit." + version + ".entity.CraftPlayer";
  }

  private static String getPlayerConnectionClasspath() {
    return "net.minecraft.server." + version + ".PlayerConnection";
  }

  private static String getNMSPlayerClasspath() {
    return "net.minecraft.server." + version + ".EntityPlayer";
  }

  private static String getPacketClasspath() {
    return "net.minecraft.server." + version + ".Packet";
  }

  private static String getIChatBaseComponentClasspath() {
    return "net.minecraft.server." + version + ".IChatBaseComponent";
  }

  private static String getChatSerializerClasspath() {
    if ((version.equals("v1_8_R1")) || (version.contains("1_7"))) {
      return "net.minecraft.server." + version + ".ChatSerializer";
    }
    return "net.minecraft.server." + version + ".IChatBaseComponent$ChatSerializer";
  }

  private static String getPacketPlayOutChat()
  {
    return "net.minecraft.server." + version + ".PacketPlayOutChat";
  }

  static
  {
    try
    {
      version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
      packetType = Class.forName(getPacketPlayOutChat());
      Class localClass1 = Class.forName(getCraftPlayerClasspath());
      Class localClass2 = Class.forName(getNMSPlayerClasspath());
      Class localClass3 = Class.forName(getPlayerConnectionClasspath());
      nmsChatSerializer = Class.forName(getChatSerializerClasspath());
      nmsIChatBaseComponent = Class.forName(getIChatBaseComponentClasspath());
      getHandle = localClass1.getMethod("getHandle", new Class[0]);
      playerConnection = localClass2.getField("playerConnection");
      sendPacket = localClass3.getMethod("sendPacket", new Class[] { Class.forName(getPacketClasspath()) });
    } catch (Exception e) {
//      Bukkit.getLogger().log(Level.SEVERE, "Error {0}", localClassNotFoundException);
    }
  }
}
