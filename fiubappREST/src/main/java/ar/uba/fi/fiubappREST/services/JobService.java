package ar.uba.fi.fiubappREST.services;

import java.util.List;

import ar.uba.fi.fiubappREST.domain.Job;

public interface JobService {

	public Job create(String userName, Job job);

	public List<Job> findAll(String userName);

	public Job update(String userName, Integer id, Job job);

	public void delete(String userName, Integer id);

}