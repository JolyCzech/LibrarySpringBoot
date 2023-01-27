package ru.elikhanov.library.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.elikhanov.library.models.Person;
import ru.elikhanov.library.services.PeopleService;

/**
 * @author Elikhanov
 */

@AllArgsConstructor
@Component
public class PersonValidator implements Validator {

    PeopleService peopleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleService.findByEmail(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already taken");
    }
}
