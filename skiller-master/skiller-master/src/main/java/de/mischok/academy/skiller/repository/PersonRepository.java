package de.mischok.academy.skiller.repository;

import de.mischok.academy.skiller.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.comment <> null and p.comment IS NOT EMPTY")
    Optional<List<Person>> findAllPersonByComment();

    @Query("SELECT p FROM Person p WHERE p.country in :countries")
    Optional<List<Person>> findAllPersonByCountriesIn(@Param("countries") List<String> countries);

    @Query("SELECT DISTINCT(p.country) FROM Person p where p <> null")
    Optional<List<String>> findAllCountries();

    @Query(value = "SELECT p FROM Person p where p.birthday BETWEEN TO_CHAR('1980-01-01','YYYY-MM-DD') AND TO_CHAR('1989-12-31','YYYY-MM-DD')")
    Optional<List<Person>> findAllPersonsBornInTheEighties();

    @Query(value = "SELECT p.country, count(p.id) FROM Person p where p.country <> null group by p.country")
    Optional<List<Object[]>> findAllPersonsCountByCountry();
}
