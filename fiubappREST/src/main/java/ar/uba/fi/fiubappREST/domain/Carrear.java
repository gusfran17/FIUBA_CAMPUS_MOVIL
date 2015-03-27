package ar.uba.fi.fiubappREST.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "Carrear")
public class Carrear {
	
	@Id
	private Integer code;
	
	@Column(unique=true)
	private String name;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="Orientation" , joinColumns=@JoinColumn(name="code"))
    private List<Orientation> orientations;;
		
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public List<Orientation> getOrientations() {
		return orientations;
	}

	public void setOrientations(List<Orientation> orientations) {
		this.orientations = orientations;
	}
	
}
