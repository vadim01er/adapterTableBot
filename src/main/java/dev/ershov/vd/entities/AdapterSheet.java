package dev.ershov.vd.entities;

import lombok.Data;

@Data
public class AdapterSheet {
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

    public AdapterSheet() {
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

    public AdapterSheet(String name, String university, String group, String vk, String inst, String phone, String letter, String date, String photo) {
        this.name = name;
        this.university = university;
        this.group = group;
        if (vk.startsWith("https://")) {
            this.vk = vk;
        } else {
            this.vk = "https://" + vk;
        }
        if (inst.startsWith("https://")) {
            this.inst = inst;
        } else {
            this.inst = "https://" + inst;
        }
        this.phone = phone;
        this.letter = letter;
        this.date = date;
        this.photo = photo;
    }
}
