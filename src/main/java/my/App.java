package my;

import my.model.Person;
import my.repo.LoansRepository;
import my.repo.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Bean
    public CommandLineRunner person(PersonRepository repository) {
        return (args )-> {
            repository.save(new Person("01", "Name1", "Surname1", false));
            repository.save(new Person("02", "Name2", "Surname2", false));
        };
    }

    @Bean
    public CommandLineRunner loans(LoansRepository repository) {
        return (args)-> {
            repository.save(new my.model.Loans(new BigDecimal(12), 124312L, "01", true));
            repository.save(new my.model.Loans(new BigDecimal(122), 1214L, "01", false));
            repository.save(new my.model.Loans(new BigDecimal(11), 452L, "02", true));
            repository.save(new my.model.Loans(new BigDecimal(22), 444L, "02", true));
        };
    }

}

