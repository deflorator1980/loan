package my.controller;

import my.model.*;
import my.repo.LoansRepository;
import my.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Show {

    private static final Logger log = LoggerFactory.getLogger(Show.class);

    private final PersonRepository personRepository;

    private final LoansRepository loansRepository;

    private final Map<String, Long> countries0 = new HashMap<>();
    private final Map<String, Long> countries = Collections.synchronizedMap(countries0); // todo check by code inspection

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
        Person person = personRepository.findOne(userId);
        if(person.isBan()) {
            return new ResponseEntity<Object>(person, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(loansRepository.findByIsApprovedAndPersonId(true, userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseEntity<?> apply(@RequestBody Apply apply, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String ip = request.getRemoteAddr();
        ResponseEntity<IpApi> resp = restTemplate.getForEntity("http://ip-api.com/json/" + ip, IpApi.class);
        IpApi ipApi = resp.getBody();
        log.info(ipApi.toString());
        String country = ipApi.getCountry();
        if (country == null) {      // todo try without internet
            country = "lv";
        }

        Long time = countries.get(country);
        if ((time != null) && ((System.nanoTime() - time) > 500_000_000)) {
            return new ResponseEntity<Object>(new CountryBan(country, "Too many requests from your country"), HttpStatus.CONFLICT);
        }
        countries.put(country, System.nanoTime());  //todo test it

        Person person = personRepository.findOne(apply.getId());
        System.out.println(person);
        if (person == null) {
            person = new Person(apply.getId(), apply.getName(), apply.getSurname(), false);
            personRepository.save(person);
        }
        if(person.isBan()) {
            return new ResponseEntity<Object>(person, HttpStatus.BAD_REQUEST);
        }

        Loans loans = new Loans();
        loans.setAmount(apply.getAmount());
        loans.setPersonId(apply.getId());
        loans.setTerm(apply.getTerm());
        loans.setApproved(true);
        loans.setCountry(country);
        return new ResponseEntity<Object>(loansRepository.save(loans), HttpStatus.OK);
    }
}
