package ar.uba.fi.fiubappREST.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "discussion_message_file")
public class DiscussionMessageFile {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String contentType;
	
	@Lob
    private byte[] file;

	@OneToOne(fetch=FetchType.EAGER)
	private DiscussionMessage message;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public DiscussionMessage getMessage() {
		return message;
	}

	public void setMessage(DiscussionMessage message) {
		this.message = message;
	}

}
