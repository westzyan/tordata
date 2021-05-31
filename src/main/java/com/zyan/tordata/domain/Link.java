package com.zyan.tordata.domain;

public class Link {
    private int source;
    private int target;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Links{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
