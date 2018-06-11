package com.liuyitao.common;

import java.util.Collection;
import java.util.Iterator;

/***
 *@Author: liuyitao
 *@CreateDate:10:02 AM 2/4/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class MyFixedList<E> implements Collection<E> {
    private Object[] items;
    private int length;

    public MyFixedList(int capcity) {
        this.items = new Object[capcity];
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int a = 0; a < length; a++) {
            if (o == items[a] || (items[a] != null && items[a].equals(o)))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        throw new RuntimeException("myfixedlist do not support iterator");
    }

    @Override
    public Object[] toArray() {
        Object[] res = new Object[length];
        System.arraycopy(items, 0, res, 0, length);
        return res;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("wrong param,you should specicy a array");
        }
        if (a.length >= items.length) {
            System.arraycopy(items, 0, a, 0, items.length);
        } else {
            System.arraycopy(items, 0, a, 0, a.length);
        }
        return a;
    }

    public E getFirst()
    {
        return (E)items[0];
    }

    public E getLast()
    {
        return (E)items[items.length-1];
    }

    @Override
    public synchronized boolean add(E e) {
        if (length < items.length) {
            items[length] = e;
            length++;
        } else {
            fullAdd(e);
        }

        return true;
    }

    private void fullAdd(E e) {
        System.arraycopy(items, 1, items, 0, items.length - 1);
        items[length-1] = e;
    }

    @Override
    public synchronized boolean remove(Object o) {
        boolean isDeleted = false;
        for (int a = 0; a < length; a++) {
            if (items[a] == o || (items[a] != null && items[a].equals(o))) {
                removeIndex(a);
                length--;
                isDeleted = true;
            }
        }

        return isDeleted;
    }

    private void removeIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("wrong param for index,can not smaller than zero");
        } else {
            System.arraycopy(items, index + 1, items, index, items.length - index - 1);
            items[items.length - 1] = null;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(Object o:c)
        {
            this.add((E)o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isDeleted=false;
        for(Object o:c)
        {
            isDeleted=this.remove(o)||isDeleted;
        }
        return isDeleted;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("my fixed list do not support retailAll operation");
    }

    @Override
    public synchronized void clear() {
        for(int a=0;a<items.length;a++)
        {
            items[a]=null;
        }
        length=0;
    }
}
