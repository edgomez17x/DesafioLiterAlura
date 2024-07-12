package com.aluracursos.DesafioLiteratura;

import com.aluracursos.DesafioLiteratura.consumoApi.GutendexAPI;
import com.aluracursos.DesafioLiteratura.model.Author;
import com.aluracursos.DesafioLiteratura.model.Book;
import com.aluracursos.DesafioLiteratura.model.BookRec;
import com.aluracursos.DesafioLiteratura.model.Languages;
import com.aluracursos.DesafioLiteratura.repository.BookRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private BookRepository bookRepository;
    private Scanner scanner = new Scanner(System.in);
    private GutendexAPI gutendexAPI;
    private final String WELCOME_MESSAGE = "Bienvenido al sistema de busqueda de libros:";
    private final String SEPARATOR = "*************************************";
    private final String MENU = """
            Por favor elige una de las siguientes opciones:
                1.- Consulta de libro.
                2.- Mostrar todos los libros consultados
                3.- Listar autores consultados
                4.- Listar autores vivos en determinado año
                5.- Mostrar Libros en un determinado idioma
                
                0.- Salir
            """;

    public Principal(BookRepository bookRepository){
        this.bookRepository = bookRepository;
        gutendexAPI = new GutendexAPI(bookRepository);
    }

    public void start(){
        System.out.println(WELCOME_MESSAGE);
        String option = "0";
        do{
            System.out.println(MENU);
            option = scanner.nextLine();;
            menuAction(option);
        }while(!option.equals("0"));
    }

    private void menuAction(String option){
        switch (option){
            case "1":{
                searchBookByName();
                break;
            }
            case "2":{
                findAll();
            }
            case "3":{
                findAllAuthors();
                break;
            }
            case "4":{
                findAuthorsByYear();
                break;
            }
            case "5":{
                findAllBooksByLanguage();
                break;
            }
            default:{
                System.out.println("Opción invalida");
                break;
            }
        }
    }

    private void findAllBooksByLanguage() {
        System.out.println("""
                Seleccione el idioma que desea buscar:
                es = Español
                en = Ingles
                fr = Frances
                pt = Portugues
                """);
        String languageSelected = scanner.nextLine();
        Languages languages = Languages.fromString(languageSelected.toLowerCase());
        if(languages!=null){
            List<Book> bookList = gutendexAPI.findByLanguage(languages);
            showBooks(bookList);
        }else{
            System.out.println("Esta opción no es valida");
        }
    }

    private void findAuthorsByYear() {
        System.out.println("Diga el año en el que quiere buscar los autores que vivieron:");
        String yearStr = scanner.nextLine();
        try{
            int year = Integer.parseInt(yearStr);
            if(year > Calendar.getInstance().get(Calendar.YEAR)){
                System.out.println("Este año aún no ha pasado");
            }else if(year < 0){
                System.out.println("No se permiten años negativos");
            }else{
                System.out.println("Listando autores que vivieron en ese año fueron");
                List<Author> authorList = gutendexAPI.findAuthorsByYear(year);
                authorList.forEach(author -> {
                    System.out.println(SEPARATOR);
                    System.out.println("*   Nombre del autor: " + author.getName());
                    System.out.println("*   Fecha de nacimiento: " + author.getBirthYear());
                    System.out.println("*   Fecha de defuncion: " + author.getDeathYear());
                    System.out.println(SEPARATOR);
                });
            }
        }catch (NumberFormatException e){
            System.out.println("El valor no es numérico");
        }
    }

    private void findAllAuthors() {
        System.out.println("Listando todos los autores consultados");
        List<Author> authorList = gutendexAPI.findAllAuthors();
        authorList.forEach(author -> {
            System.out.println(SEPARATOR);
            System.out.println("*   Nombre del autor: " + author.getName());
            System.out.println("*   Fecha de nacimiento: " + author.getBirthYear());
            System.out.println("*   Fecha de defuncion: " + author.getDeathYear());
            System.out.println(SEPARATOR);
        });

    }

    private void findAll(){
        System.out.println("Listando todos los libros consultados:");
        List<Book> bookList = gutendexAPI.findAll();
        showBooks(bookList);
    }

    private void showBooks(List<Book> bookList){
        if(!bookList.isEmpty()){
            bookList.forEach(book -> {
                System.out.println(SEPARATOR);
                System.out.println("*   El titulo del libro es: " + book.getTitle());
                book.getAuthorList().forEach(a-> System.out.println("*   autor: " + a.getName()));
                System.out.println("*   El idioma es: " + book.getLanguage());
                System.out.println("*   El total de descargas es: " + book.getDownloads());
                System.out.println(SEPARATOR);
            });
        }else{
            System.out.println("No se encontró ningún libro en la base de datos");
        }
    }

    private void searchBookByName(){
        try {
            System.out.println("Por favor escriba el nombre del libro que desea buscar:");
            String bookName = scanner.nextLine();
            List<Book> bookList = gutendexAPI.searchBookByName(bookName);
            showBooks(bookList);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
