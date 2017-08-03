package my.controller;

import my.model.Apply;
import my.model.Loans;
import my.model.Person;
import my.repo.LoansRepository;
import my.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Show {

    private static final Logger log = LoggerFactory.getLogger(Show.class);

    private final PersonRepository personRepository;

    private final LoansRepository loansRepository;

    @Autowired
    public Show(PersonRepository personRepository, LoansRepository loansRepository) {
        this.personRepository = personRepository;
        this.loansRepository = loansRepository;
    }

    @RequestMapping("/person")
    public ResponseEntity<?> person() {
        return new ResponseEntity<Object>(personRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/loans")
    public ResponseEntity<?> loans() {
        return new ResponseEntity<Object>(loansRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/approvedall")
    public ResponseEntity<?> approvedAll() {
        return new ResponseEntity<Object>(loansRepository.findByIsApproved(true), HttpStatus.OK);
    }

    @RequestMapping("/approveduser")
    public ResponseEntity<?> approvedUser(@RequestParam(value = "user_id") String userId) {
        return new ResponseEntity<Object>(loansRepository.findByIsApprovedAndPersonId(true, userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseEntity<?> apply(@RequestBody Apply apply) {
        Person person = personRepository.findOne(apply.getId());
        System.out.println(person);
        if (person == null) {
            person = new Person(apply.getId(), apply.getName(), apply.getSurname(), false);
            personRepository.save(person);
        }

        Loans loans = new Loans();
        loans.setAmount(apply.getAmount());
        loans.setPersonId(apply.getId());
        loans.setTerm(apply.getTerm());
        loans.setApproved(true);
        return new ResponseEntity<Object>(loansRepository.save(loans), HttpStatus.OK);
    }
}
