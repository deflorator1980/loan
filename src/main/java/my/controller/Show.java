package my.controller;

import my.repo.ApplyRepository;
import my.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Show {

    private final PersonRepository personRepository;

    private final ApplyRepository applyRepository;

    @Autowired
    public Show(PersonRepository personRepository, ApplyRepository applyRepository) {
        this.personRepository = personRepository;
        this.applyRepository = applyRepository;
    }

    @RequestMapping("/person")
    public ResponseEntity<?> person() {
        return new ResponseEntity<Object>(personRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/apply")
    public ResponseEntity<?> apply() {
        return new ResponseEntity<Object>(applyRepository.findAll(), HttpStatus.OK);
    }
}
