package ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Book;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Person;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.repositories.PeopleRepository;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
     
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id,Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

@Transactional
    public Optional<Person> getPersonByFullName(String name){
        return  peopleRepository.findByFullName(name);
        }

    @Transactional
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = (peopleRepository.findById(id));
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            //Мы внизу итерируемся по книгам, роэтому они точно будут загружены, но на всякий случай
            // не мешает всега вызвать  Hibernate.initialize
            //на всякий случай если код в дальнейшем поменяется и итерация
            // по книгам удалится
            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() -
                    new Date().getTime());
            //864000000 милисекунд = 10 суток
            if (diffInMillies > 1)
                book.setExpired(true);//книга просрочена
        });
            return person.get().getBooks();
    }
    else {
        return Collections.emptyList();
        }
    }
}
