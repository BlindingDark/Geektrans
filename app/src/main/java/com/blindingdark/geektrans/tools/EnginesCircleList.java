package com.blindingdark.geektrans.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by BlindingDark on 2016/12/7 0007.
 */

public class EnginesCircleList<E> {
    int nowIndex = 0;

    List<E> arrayList = new ArrayList<>();

    public int getNowIndex() {
        return nowIndex;
    }

    public EnginesCircleList<E> setNowIndex(int nowIndex) {
        this.nowIndex = nowIndex;
        return this;
    }

    public EnginesCircleList<E> setNowIndex(E e) {
        this.nowIndex = arrayList.indexOf(e);
        return this;
    }

    public EnginesCircleList(ArrayList<E> arrayList) {
        this.arrayList = arrayList;
    }

    public EnginesCircleList(HashSet<E> hashSet) {
        for (E e :
                hashSet) {
            arrayList.add(e);
        }
    }

    public E next(E e) {
        int nextIndex = (arrayList.indexOf(e) + 1);

        return this.next(nextIndex);
    }

    public E next(int index) {
        int nextIndex = index + 1;
        if (nextIndex == arrayList.size()) {
            nextIndex = 0;
        }
        this.setNowIndex(nextIndex);

        return arrayList.get(nextIndex);
    }


    public E next() {
        return next(this.nowIndex);
    }
}
