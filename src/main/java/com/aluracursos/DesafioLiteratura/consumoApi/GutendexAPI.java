package com.aluracursos.DesafioLiteratura.consumoApi;

import com.aluracursos.DesafioLiteratura.model.*;
import com.aluracursos.DesafioLiteratura.repository.BookRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GutendexAPI {

    private BookRepository bookRepository;

    private static final String URL = "https://gutendex.com/books/";

    public GutendexAPI(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> searchBookByName(String name) throws IOException, InterruptedException {
        List<Book> bookList = bookRepository.findByTitleContainsIgnoreCase(name);
        if(bookList.isEmpty()){
            bookList = searchBookWeb(name);
            bookList.forEach(b->bookRepository.save(b));
        }
        return bookList;
    }

    private List<Book> searchBookWeb(String name) throws IOException, InterruptedException {
        List<BookRec> bookRecList = new ArrayList<>();
        String json = getApiResponse(URL + "?search=" + encodeURL(name));
        SearchRec searchRec = new DataConverter().getData(json, SearchRec.class);
        if (!searchRec.count().equals(0L)){
            bookRecList.addAll(searchRec.results());
            while(searchRec.next()!= null){
                searchRec = getNextSearch(searchRec.next());
                bookRecList.addAll(searchRec.results());
            }
        }
        return bookRecList.stream().map(Book::new).toList();
    }

    private SearchRec getNextSearch(String nextUrl) throws IOException, InterruptedException {
        String json = getApiResponse(nextUrl);
        return new DataConverter().getData(json, SearchRec.class);
    }

    private String getApiResponse(String urlComplete) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(urlComplete)).build();
        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    private String encodeURL(String param){
        return URLEncoder.encode(param, StandardCharsets.UTF_8);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Author> findAllAuthors() {
        List<Object> objectList = bookRepository.findAllAuthors();
        return new ArrayList<>(objectList.stream().map(o -> {
            Author author = new Author();
            Object[] objects = (Object[]) o;
            author.setName((String) objects[0]);
            author.setBirthYear((int) objects[1]);
            author.setDeathYear((int) objects[2]);
            return author;
        }).toList());
    }

    public List<Author> findAuthorsByYear(int year) {
        List<Object> objectList = bookRepository.findAuthorsByYear(year);
        return new ArrayList<>(objectList.stream().map(o -> {
            Author author = new Author();
            Object[] objects = (Object[]) o;
            author.setName((String) objects[0]);
            author.setBirthYear((int) objects[1]);
            author.setDeathYear((int) objects[2]);
            return author;
        }).toList());
    }

    public List<Book> findByLanguage(Languages languages) {
        return bookRepository.findByLanguage(languages);
    }
}
