package com.aluracursos.DesafioLiteratura.repository;

import com.aluracursos.DesafioLiteratura.model.Author;
import com.aluracursos.DesafioLiteratura.model.Book;
import com.aluracursos.DesafioLiteratura.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainsIgnoreCase(String title);
    @Query("SELECT a.name, a.birthYear, a.deathYear FROM Book b JOIN b.authorList a GROUP BY name, birthYear, deathYear")
    List<Object> findAllAuthors();
    @Query("SELECT a.name, a.birthYear, a.deathYear FROM Book b JOIN b.authorList a WHERE a.birthYear <= :year AND a.deathYear >= :year GROUP BY name, birthYear, deathYear")
    List<Object> findAuthorsByYear(int year);

    List<Book> findByLanguage(Languages languages);
}
