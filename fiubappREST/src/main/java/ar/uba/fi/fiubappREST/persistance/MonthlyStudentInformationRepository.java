package ar.uba.fi.fiubappREST.persistance;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.fiubappREST.domain.MonthlyApprovedStudentsInformation;

@Repository
public interface MonthlyStudentInformationRepository extends JpaRepository<MonthlyApprovedStudentsInformation, Integer> {
	
	public List<MonthlyApprovedStudentsInformation> findByOrderByMonthYearDesc(Pageable pageable);
	
	public List<MonthlyApprovedStudentsInformation> findByMonthYearAfterOrderByMonthYearAsc(Date startMonthYear);

}
