package ar.uba.fi.fiubappREST.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WALL_CONFIGURATION")
public class WallConfiguration extends Configuration{

}
