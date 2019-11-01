package com.tekken;

import com.tekken.support.Logs;

import java.io.*;
import java.lang.reflect.Field;

public class Option {

    public static Integer VERTX_POOL = 1;
    public static Integer VERTX_PORT = 80;

    public static boolean TEKKEN_DEBUG = true;
    public static String TEKKEN_MODULE = "modules/";
    public static boolean MODE_DEVELOPPER = true;

    public static Integer TEMPLATE_UPDATER_DELAY = 10*1000;
    public static String TEMPLATE_WEBROOT = "webroot";

    public static boolean FIREWALL_ENABLE = true;
    public static String FIREWALL_HOST = "127.0.0.1";
    public static Integer FIREWALL_PORT = 100;

    public static Integer MYSQL_POOL = 2;

    private static File config = new File("config/config.xml");

    public static void loadConfig(){
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(config))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split(":");
                if(lines.length != 2) continue;
                Field f = Option.class.getField(lines[0]);
                f.setAccessible(true);
                if(lines[1].startsWith(" ")) lines[1] = lines[1].substring(1, (lines[1].length()));
                try {
                    if(f.getType() == Boolean.class)
                        f.set(Boolean.class, Boolean.parseBoolean(lines[1]));
                    else if(f.getType() == Integer.class) {
                        f.set(Integer.class, Integer.parseInt(lines[1]));
                    } else if(f.getType() == String.class)
                        f.set(String.class, lines[1]);

                }catch (Exception e){
                    Logs.warn("Cannot load config because field "+f.getName()+" contains a bad type . Please set a type "+f.getType().getName());
                }
            }
        } catch (IOException e) {
            Logs.error("File can't read", e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void createConfig(){

        try (PrintWriter writer = new PrintWriter(new FileWriter(config, false))) {

            for(Field f : Option.class.getFields()){
                writer.print(f.getName()+": "+f.get(f.getType()).toString()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            // ... handle IO exception
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
