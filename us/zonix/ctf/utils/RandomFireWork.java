package us.zonix.ctf.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Random;

public class RandomFireWork {

    //Make the arraylists for the colors and types
    static ArrayList<Color> colors = new ArrayList<Color>();
    static ArrayList<FireworkEffect.Type> types = new ArrayList<FireworkEffect.Type>();

    //MAKE SURE YOU PUT THIS IN YOUR ONENABLE!!!
    public static void addColors(){
//ADD ALL THE COLORS
        colors.add(Color.WHITE);
        colors.add(Color.PURPLE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.AQUA);
        colors.add(Color.BLUE);
        colors.add(Color.FUCHSIA);
        colors.add(Color.GRAY);
        colors.add(Color.LIME);
        colors.add(Color.MAROON);
        colors.add(Color.YELLOW);
        colors.add(Color.SILVER);
        colors.add(Color.TEAL);
        colors.add(Color.ORANGE);
        colors.add(Color.OLIVE);
        colors.add(Color.NAVY);
        colors.add(Color.BLACK);
//I think I added them all not sure though
    }

    //MAKE SURE YOU PUT THIS IN YOUR ONENABLE!!!
    public void addTypes(){
//ADD ALL THE TYPES
        types.add(FireworkEffect.Type.BURST);
        types.add(FireworkEffect.Type.BALL);
        types.add(FireworkEffect.Type.BALL_LARGE);
        types.add(FireworkEffect.Type.CREEPER);
        types.add(FireworkEffect.Type.STAR);
//Added all the types
    }

//Getting a random firework

    public FireworkEffect.Type getRandomType(){
        int size = types.size();
        Random ran = new Random();
        FireworkEffect.Type theType = types.get(ran.nextInt(size));

        return theType;
    }

//Getting a random COLOR!!!

    public static Color getRandomColor(){
        int size = colors.size();
        Random ran = new Random();
        Color color = colors.get(ran.nextInt(size));
        return color;
    }
}
