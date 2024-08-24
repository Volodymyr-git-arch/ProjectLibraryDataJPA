package ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Book;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Person;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.repositories.BooksRepository;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }
    public List<Book> findAll(boolean sortByYear) {
        if(sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }
    public List<Book> findWithPagination(Integer page,Integer booksPerPage,boolean sortByYear){
        if(sortByYear)
            return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("year"))).
            getContent();
            else
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
            }
    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String query){
        return booksRepository.findByTitleStartingWith(query);
    }

   //returns null, if book has no owner
    public Person getBookOwner(int id){
        //здесь Hibernate.initializer не нужен, так как владелец( сторона one ) загружается не лениво
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);

    }
@Transactional
   public void release(int id){
        booksRepository. findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }
    //назначает книгу человеку, этот метод вызывается когда человек забирает книгу из библиотеки
    @Transactional
  public void assign(int id,Person selectedPerson){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());// текущее время
                }
        );
    }
}

