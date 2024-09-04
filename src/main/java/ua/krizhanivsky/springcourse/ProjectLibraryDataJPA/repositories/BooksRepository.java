package ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Book;


import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleStartingWith(String title);


}
