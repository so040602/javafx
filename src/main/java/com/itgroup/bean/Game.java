package com.itgroup.bean;

public class Game {
    private int gnum;
    private String gname;
    private String ggenre;
    private String image01;
    private String image02;
    private String company;
    private int price;
    private String reldate;
    private String content;
    private int count;

    public Game() {
    }


    public Game(int gnum, String gname, String ggenre, String image01, String image02, String company, int price, String reldate, String content, int count) {
        this.gnum = gnum;
        this.gname = gname;
        this.ggenre = ggenre;
        this.image01 = image01;
        this.image02 = image02;
        this.company = company;
        this.price = price;
        this.reldate = reldate;
        this.content = content;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gnum=" + gnum +
                ", gname='" + gname + '\'' +
                ", ggenre='" + ggenre + '\'' +
                ", image01='" + image01 + '\'' +
                ", image02='" + image02 + '\'' +
                ", company='" + company + '\'' +
                ", price=" + price +
                ", reldate='" + reldate + '\'' +
                ", content='" + content + '\'' +
                ", count=" + count +
                '}';
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGgenre() {
        return ggenre;
    }

    public void setGgenre(String ggenre) {
        this.ggenre = ggenre;
    }

    public String getImage01() {
        return image01;
    }

    public void setImage01(String image01) {
        this.image01 = image01;
    }

    public String getImage02() {
        return image02;
    }

    public void setImage02(String image02) {
        this.image02 = image02;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReldate() {
        return reldate;
    }

    public void setReldate(String reldate) {
        this.reldate = reldate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
