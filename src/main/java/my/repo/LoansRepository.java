package my.repo;

import my.model.Loans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface LoansRepository extends CrudRepository<my.model.Loans, Long> {
    List<Loans> findByIsApproved(boolean isApproved);

    List<Loans> findByIsApprovedAndPersonId(boolean isApproved, String personId);
}
