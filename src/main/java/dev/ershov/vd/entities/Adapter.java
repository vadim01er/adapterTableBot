package dev.ershov.vd.entities;

import lombok.Data;

@Data
public class Adapter {
    private String name;
    private String university;
    private String group;
    private String vk;
    private String inst;
    private String phone;
    private String letter;
    private String date;
    private String photo;
    private String comment;

    public Adapter() {
        this.name = "no have adapter";
        this.university = "no have adapter";
        this.group = "no have adapter";
        this.vk = "no have adapter";
        this.inst = "no have adapter";
        this.phone = "no have adapter";
        this.letter = "no have adapter";
        this.date = "no have adapter";
        this.photo = "no have adapter";
        this.comment = "no have adapter";
    }

    public Adapter(String name, String university, String group, String vk, String inst, String phone, String letter, String date, String photo) {
        this.name = name;
        this.university = university;
        this.group = group;
        this.vk = vk;
        this.inst = inst;
        this.phone = phone;
        this.letter = letter;
        this.date = date;
        this.photo = photo;
    }

}
