package ar.uba.fi.fiubappREST.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.MonthlyApprovedStudentsInformation;
import ar.uba.fi.fiubappREST.persistance.MonthlyStudentInformationRepository;

@Service
public class ApprovedStudentsReportUpdater {
	
	private MonthlyStudentInformationRepository monthlyStudentInformationRepository;
	
	@Autowired
	public ApprovedStudentsReportUpdater(MonthlyStudentInformationRepository monthlyStudentInformationRepository){
		this.monthlyStudentInformationRepository = monthlyStudentInformationRepository;
	}
	
	public void regenerateInformation(){
		Pageable topOne = new PageRequest(0, 1);
		MonthlyApprovedStudentsInformation lastInformation = this.monthlyStudentInformationRepository.findByOrderByMonthYearDesc(topOne).get(0);
		
		Date thisMonth = this.getThisMonth();
		if(!DateUtils.isSameDay(lastInformation.getMonthYear(), thisMonth)){
			this.buildMissingInformation(lastInformation, thisMonth);
		}	
	}

	private void buildMissingInformation(MonthlyApprovedStudentsInformation lastInformation, Date thisMonth) {
		Date missingMonth = DateUtils.addMonths(lastInformation.getMonthYear(), 1);
		while(!missingMonth.after(thisMonth)){
			MonthlyApprovedStudentsInformation missingInformation = new MonthlyApprovedStudentsInformation();
			missingInformation.setMonthYear(missingMonth);
			missingInformation.setAmountOfStudents(lastInformation.getAmountOfStudents());
			this.monthlyStudentInformationRepository.save(missingInformation);
			missingMonth = DateUtils.addMonths(missingMonth, 1);
		}
	}

	private Date getThisMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
}
