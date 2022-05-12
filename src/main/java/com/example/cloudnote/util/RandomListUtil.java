package com.example.cloudnote.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * class RandomListUtil
 *
 * @author zhangyi 2022/4/15 10:49
 */
public class RandomListUtil<T> {

    private final List<T> allList;

    public RandomListUtil(List<T> allList) {
        this.allList = allList;
    }

    /**
     * 获取列表里面指定数量的随机子串
     *
     * @param n
     * @return
     */
    public List<T> getRandomList(int n) {
        Set<Integer> exist = new TreeSet<>();
        if (this.allList.size() <= n) {
            return this.allList;
        }

        List<T> resList = new ArrayList<>();

        while (exist.size() < n) {
            int random = (int) (Math.random() * this.allList.size());
            if (!exist.contains(random)) {
                exist.add(random);
                resList.add(this.allList.get(random));
            }
        }
        return resList;
    }

    public T getRandomOne() {
        if (this.allList.size() == 0) {
            return null;
        }
        int random = (int) (Math.random() * this.allList.size());
        return this.allList.get(random);
    }
}

