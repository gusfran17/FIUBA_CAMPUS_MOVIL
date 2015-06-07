package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.List;

import ar.uba.fi.fiubappREST.domain.DiscussionReportInformation;

public interface ReportService {

	public List<DiscussionReportInformation> getMostActiveDiscussions(Date dateFrom, Date dateTo, Integer values);

}
