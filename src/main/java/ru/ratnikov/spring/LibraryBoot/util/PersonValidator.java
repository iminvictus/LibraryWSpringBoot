package ru.ratnikov.spring.LibraryBoot.util;

import ru.ratnikov.spring.LibraryBoot.models.Person;
import ru.ratnikov.spring.LibraryBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (!peopleService.findByName(person.getName()).isEmpty())
            errors.rejectValue("name", "", "Person with entered name is already registered");
    }
}
