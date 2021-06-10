package com.zyan.tordata.domain;

/**
 * @author zhangyan
 * @date 2021/6/8 下午7:40
 */
public class Assessment {
    private int id;
    private String node;
    private String type;
    private String description;

    public Assessment() {
    }

    public Assessment(int id, String node, String type, String description) {
        this.id = id;
        this.node = node;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", node='" + node + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
