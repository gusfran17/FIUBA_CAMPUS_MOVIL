package ar.uba.fi.fiubappREST.persistance;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.MonthlyApprovedStudentsInformation;

@Repository
public interface MonthlyStudentInformationRepository extends CrudRepository<MonthlyApprovedStudentsInformation, Integer> {
	
	public MonthlyApprovedStudentsInformation findByMonthYear(Date monthYear);
	
	public List<MonthlyApprovedStudentsInformation> findAllOrderByMonthYeDesc(Pageable pageable);

}
