package org.example;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HashMap<String, Integer> maps = new HashMap<>();
        maps.put("hello", 3);
        maps.put("hello",maps.get("hello")+1);
        System.out.println(maps.get("hello"));
    }
}
