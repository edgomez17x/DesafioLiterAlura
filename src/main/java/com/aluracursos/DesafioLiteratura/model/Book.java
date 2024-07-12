package com.aluracursos.DesafioLiteratura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authorList;
    @Enumerated(EnumType.STRING)
    private Languages language;
    private Long downloads;

    public Book(){

    }

    public Book(BookRec bookRec){
        this.title = bookRec.title();
        this.authorList = bookRec.authors().stream().map(Author::new).toList();
        this.downloads = bookRec.download_count();
        this.language = Languages.fromString(bookRec.languages().get(0));
        this.authorList.forEach(a->a.setBook(this));
    }
    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }
}
