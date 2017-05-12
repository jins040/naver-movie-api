package kr.re.kitri.model;

import java.time.LocalDate;

/**
 * Created by danawacomputer on 2017-05-12.
 */
public class Item {

    // 필드
    private int itemID;
    private String title;
    private String link;
    private String image;
    private String subTitle;
    private LocalDate pubDate;
    private String director;
    private String actor;
    private double userRating;
    private int searchID;

    // 생성자
    public Item() {
    }

    public Item(int itemID, String title, String link, String image, String subTitle, LocalDate pubDate,
                String director, String actor, double userRating, int searchID) {
        this.itemID = itemID;
        this.title = title;
        this.link = link;
        this.image = image;
        this.subTitle = subTitle;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
        this.searchID = searchID;
    }

    // Method
    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getSubTitle() {
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }
    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }

    public double getUserRating() {
        return userRating;
    }
    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public int getSearchID() {
        return searchID;
    }
    public void setSearchID(int searchID) {
        this.searchID = searchID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", pubDate=" + pubDate +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", userRating=" + userRating +
                ", searchID=" + searchID +
                '}';
    }
}
