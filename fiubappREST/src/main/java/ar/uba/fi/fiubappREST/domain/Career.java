package ar.uba.fi.fiubappREST.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import ar.uba.fi.fiubappREST.exceptions.OrientationAlreadyExistsException;


@Entity
@Table(name = "Career")
public class Career {
	
	@Id
	private Integer code;
	
	@Column(unique=true)
	private String name;
	
	@OneToMany(mappedBy="career", cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
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

	public void addOrientation(final Orientation orientation) {
		if(this.existsOrientation(orientation)){
			throw new OrientationAlreadyExistsException(orientation.getName(), this.name);
		}
		orientation.setCareer(this);
		this.orientations.add(orientation);
	}
	
	private boolean existsOrientation(final Orientation orientation){
		Orientation foundOrientation = (Orientation) CollectionUtils.find(this.orientations,new Predicate() {
            public boolean evaluate(Object object) {
                return ((Orientation) object).getName().equals(orientation.getName());
            }
		});
		return foundOrientation!=null;
	}
	
}
