package ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.krizhanivsky.springcourse.ProjectLibraryDataJPA.models.Person;

import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

     Optional<Person> findByFullName(String name);



}
