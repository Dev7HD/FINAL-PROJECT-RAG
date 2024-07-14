package ma.dev7hd.finalexamrag.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;
import ma.dev7hd.finalexamrag.entities.Person;
import ma.dev7hd.finalexamrag.repositories.PersonRepository;

@BrowserCallable
@AnonymousAllowed
public class PersonService extends CrudRepositoryService<Person, Long, PersonRepository>  {
}
