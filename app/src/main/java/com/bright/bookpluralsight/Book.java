package com.bright.bookpluralsight;

import java.util.Arrays;

public class Book {
    public String id;
    public String title;
    public String[] authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public String imageLink;
    public String thumbnail;

    public Book(String id, String title, String[] authors, String publisher, String publishedDate, String description, String imageLink, String thumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.imageLink = imageLink;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
