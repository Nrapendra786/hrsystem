package de.mischok.academy.skiller.service;

import de.mischok.academy.skiller.domain.Person;
import de.mischok.academy.skiller.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository =personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> getAllPersonsWithComment() {
        Optional<List<Person>> optionalPersonList = personRepository.findAllPersonByComment();
        if(!optionalPersonList.isPresent()){
            return Collections.emptyList();
        }
        return optionalPersonList.get().stream().filter(person -> !person.getComment().isBlank()).collect(Collectors.toList());
    }

    public List<Person> getAllPersonsFromCountries(String...countries) {
        Optional<List<Person>> optionalPersonList = personRepository.findAllPersonByCountriesIn(List.of(countries));
        if(!optionalPersonList.isPresent()) {
            return Collections.emptyList();
        }
        return optionalPersonList.get();
    }

    public List<String> getAllCountries() {
        Optional<List<String>> optionalPersonList = personRepository.findAllCountries();
        if (!optionalPersonList.isPresent()) {
            return Collections.emptyList();
        }
        return optionalPersonList.get().stream().filter(country -> country!=null).collect(Collectors.toList());
    }

    public List<Person> getAllPersonsBornInTheEighties() {
        Optional<List<Person>> optionalPersonList = personRepository.findAllPersonsBornInTheEighties();
        if (!optionalPersonList.isPresent()) {
            return Collections.emptyList();
        }
        return personRepository.findAllPersonsBornInTheEighties().get();
    }

    public Map<String, Long> getPersonCountByCountry() {

        Optional<List<Object[]>> optionalPersonList = personRepository.findAllPersonsCountByCountry();
        if (!optionalPersonList.isPresent()) {
            return Collections.emptyMap();
        }

        List<Object[]> values = personRepository.findAllPersonsCountByCountry().get();
        Map<String, Long> personMap = new HashMap<>();

        for ( int iForLoop = 0 ; iForLoop < values.size(); iForLoop++) {
             personMap.put((String) values.get(iForLoop)[0],(Long) values.get(iForLoop)[1]);
        }
        return personMap;
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

}
