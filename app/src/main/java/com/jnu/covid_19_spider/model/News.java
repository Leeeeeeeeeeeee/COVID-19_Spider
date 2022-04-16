package com.jnu.covid_19_spider.model;

public class News implements Comparable<News> {
    private String from;
    private long date;
    private String img;
    private String url;
    private String title;

    public News() {

    }

    public News(String from, long date, String img, String url, String title) {
        this.from = from;
        this.date = date;
        this.img = img;
        this.url = url;
        this.title = title;
    }

    @Override
    public String toString() {
        return "news{" +
                "from='" + from + '\'' +
                ", date=" + date +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(News news) {
        return ((this.getDate() > news.getDate()) ? (-1)
                :
                ((this.getDate() == news.getDate())
                        ? 0 : 1));
    }
}
