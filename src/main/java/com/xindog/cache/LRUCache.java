package com.xindog.cache;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/24/19
 * Time:    5:25 PM
 * Project: learn
 */
import java.util.HashMap;
import java.util.LinkedList;

class LRUCache {
    //保存key和值
    private HashMap<Integer, Integer> cacheMap = new HashMap<>();
    /**
     *在此对key进行最近最少使用淘汰，只保存key
     *新添加的key或新被访问的key放到链表尾部，超过容量时，移除链表第一个key
     */
    private LinkedList<Integer> recentlyList = new LinkedList<>();

    //链表的最大容量
    private int capacity;

    public LRUCache(int capacity) {
        //需要手动指定容量大小
        this.capacity = capacity;
    }

    //访问一个key
    public int get(int key) {
        //若需要访问的key不存在，则返回-1
        if (!cacheMap.containsKey(key)) {
            return -1;
        }
        //若key存在
        //1、先从链表中删除该key，因为不知道该key在链表中的顺序
        recentlyList.remove((Integer) key);
        //2、又将该key添加到链表的尾部
        recentlyList.add(key);

        //从map中返回该key的对应的value
        return cacheMap.get(key);
    }

    //添加一个key
    public void put(int key, int value) {
        //若该key已存在，则说明此次操作是修改
        if (cacheMap.containsKey(key)) {
            //移除掉链表中的key，后面再添加，保证最近被访问的key总是在链表的尾部
            recentlyList.remove((Integer) key);

            //若容量已满，则移除链表中的第一个key，并移除对应的map元素
        }else if(cacheMap.size() == capacity){
            cacheMap.remove(recentlyList.removeFirst());
        }
        //将新加入的key放到链表尾部
        recentlyList.add(key);
        //更新或添加key和value
        cacheMap.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // returns 1
        cache.put(3, 3); // 驱逐 key 2
        System.out.println(cache.get(2)); // returns -1 (not found)
        cache.put(4, 4); // 驱逐 key 1
        System.out.println(cache.get(1)); // returns -1 (not found)
        System.out.println(cache.get(3)); // returns 3
        System.out.println(cache.get(4)); // returns 4
    }
}
