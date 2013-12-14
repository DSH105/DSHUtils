package io.github.dsh105.dshutils.pagination;

import java.util.ArrayList;


public class Paginator {

    private ArrayList<String> raw;
    private int perPage;

    public Paginator(String[] raw, int perPage) {
        this.perPage = perPage;
        this.setRaw(raw);
    }

    public Paginator(ArrayList<String> raw, int perPage) {
        this.perPage = perPage;
        this.setRaw(raw);
    }

    public void setRaw(String[] newRaw) {
        for (String s : newRaw) {
            this.raw.add(s);
        }
    }

    public void setRaw(ArrayList<String> newRaw) {
        this.raw = newRaw;
    }

    public ArrayList<String> getRaw() {
        return this.raw;
    }

    public int getIndex() {
        return (int) (Math.ceil(this.raw.size() / this.perPage));
    }

    public double getDoubleIndex() {
        return (Math.ceil(this.raw.size() / this.perPage));
    }

    public String[] getPage(int pageNumber) {
        int index = this.perPage * (Math.abs(pageNumber) - 1);
        ArrayList<String> list = new ArrayList<String>();
        if (pageNumber <= getDoubleIndex()) {
            for (int i = index; i < (index + this.perPage); i++) {
                if (raw.size() > i) {
                    list.add(raw.get(i));
                }
            }
        } else {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
}