package com.application.se2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.application.se2.Application;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.application.se2.misc.IDGenerator;


/**
 * Customer is an Entity-class that represents a customer.
 * 
 * @author sgra64
 * 
 */

@Entity
@Table(name = "Customer")
public class Customer implements com.application.se2.model.Entity {
	private static final long serialVersionUID = 1L;
	
	private static final IDGenerator CustomerIdGenerator
		= new IDGenerator( "K", IDGenerator.IDTYPE.NUM, 6 );

	/*
	 * Entity Properties.
	 */
	@Id
	@Column(name ="id")
	private final String id;

	@Column(name ="name")
	private String name;

	@Column(name ="address")
	private String address;

	@Column(name="contacts")
	@Convert(converter = com.application.se2.model.customserializer.StringListConverter.class)		// map List<String> to single, ';'-separated String
	private final List<String>contacts;

	//@Transient
	private final List<Note>notes;

	//@Transient
	private final Date created;

	public enum Status { ACT, SUSP, TERM };
	//
	@Column(name="status")
	private Status status;


	/**
	 * Default constructor needed by JSON deserialization and Hibernate (private
	 * is sufficient). Public default constructor needed by Hibernate/JPA access.
	 * Otherwise Hibernate Error: HHH000142: Bytecode enhancement failed).
	 */
	public Customer() {
		this( null );
	}

	/**
	 * Public constructor.
	 * @param name Customer name.
	 */
	public Customer( final String name ) {
		this( null, name, null );
	}


	/**
	 * Private constructor.
	 * @param id if null is passed as id, an ID will be generated.
	 * @param name Customer name.
	 */
	public Customer( final String id, final String name, final Date created ) {
		this.id = id == null? CustomerIdGenerator.nextId() : id;
		setName( name );
		this.address = "";
		this.contacts = new ArrayList<String>();
		this.notes = new ArrayList<Note>();
		//this.created = new Date();
		this.created = created==null? new Date() : created;
		this.status = Status.ACT;
	}


	/**
	 * Return Customer id.
	 * 
	 * @return Customer id.
	 */
	@Override
	public String getId() {
		return id;
	}


	/**
	 * Return Customer name.
	 * 
	 * @return Customer name.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Set Customer name.
	 * 
	 * @param name new Customer name.
	 * @return self reference.
	 */
	public Customer setName( final String name ) {
		this.name = name;
		// this.name = prettyName( name );		
		return this;			
	}


	/**
	 * Return Customer address.
	 * 
	 * @return Customer address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set Customer address.
	 * 
	 * @param new Customer address.
	 * @return self reference.
	 */
	public Customer setAddress( final String address ) {
		this.address = address;		
		return this;
	}


	/**
	 * Get Customer contacts.
	 * 
	 * @return Customer contacts.
	 */
	public List<String>getContacts() {
		return contacts;
	}

	/**
	 * Add Customer contact.
	 * 
	 * @param contact new Customer contact.
	 * @return self reference.
	 */
	public Customer addContact( final String contact ) {
		if( contact != null && contact.length() > 0 && ! contacts.contains( contact ) ) {
			contacts.add( contact.trim() );
		}
		return this;
	}


	/**
	 * Get Customer notes, which are short, time-stamped records.
	 * 
	 * @return Customer notes.
	 */
	public List<Note>getNotes() {
		return notes;
	}

	/**
	 * Add Customer note.
	 * 
	 * @param noteStr short, time-stamped record.
	 * @return self reference.
	 */
	public Customer addNote( final String noteStr ) {
		if( noteStr != null && noteStr.length() > 0 ) {
			Note note = new Note( noteStr.trim() );
			notes.add( note );
		}
		return this;
	}


	/**
	 * Get creation date of this Customer instance.
	 * 
	 * @return creation date of this Customer instance.
	 */
	public Date getCreationDate() {
		return created;
	}


	/**
	 * Get Customer status.
	 * 
	 * @return Customer status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Set Customer status.
	 * 
	 * @param status new Customer status.
	 * @return self reference.
	 */
	public Customer setStatus( Status status ) {
		this.status = status;
		return this;
	}


	/*
	 * Private methods.
	 */

	@SuppressWarnings("unused")
	private String prettyName( String name ) {
		String retName = name == null? "" : name;

		if( ! retName.contains( "," ) ) {
			// reverse name order to show last name first
			StringBuffer sb = new StringBuffer();
			String[] sp = retName.split( "[ \t]" );
			int parts = sp.length;
			if( parts > 0 ) {
				sb.append( sp[ --parts ] );
				for( int i = 0; i < parts; i++ ) {
					sb.append( i==0? ", " : " " ).append( sp[i] );
				}
			}
			retName = sb.toString();
		}

		return retName;
	}

}
