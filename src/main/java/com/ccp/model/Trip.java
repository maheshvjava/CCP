package com.ccp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ccp.json.CustomJsonDateDeserializerWithoutTimeZone;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * The persistent class for the trip database table.
 * 
 */
@Entity
@NamedQuery(name="Trip.findAll", query="SELECT t FROM Trip t")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class Trip implements Serializable {
	@Override
	public String toString() {
		return "Trip [id=" + id + ", datetime=" + datetime + ", destination="
				+ destination + ", destinationlat=" + destinationlat
				+ ", destinationlng=" + destinationlng + ", objectid="
				+ objectid + ", polyline=" + polyline + ", source=" + source
				+ ", sourcelat=" + sourcelat + ", sourcelng=" + sourcelng
				+ ", status=" + status + ", poolrqsts=" + poolrqsts 
				+ ", user="+ user + "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = CustomJsonDateDeserializerWithoutTimeZone.class)
	private Date datetime;

	
	private String destination;

	private double destinationlat;

	private double destinationlng;

	private String objectid;

	private String polyline;

	private String source;

	private double sourcelat;

	private double sourcelng;

	private int status;
	
	public Trip() {
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getDestinationlat() {
		return this.destinationlat;
	}

	public void setDestinationlat(double destinationlat) {
		this.destinationlat = destinationlat;
	}

	public double getDestinationlng() {
		return this.destinationlng;
	}

	public void setDestinationlng(double destinationlng) {
		this.destinationlng = destinationlng;
	}

	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public String getPolyline() {
		return this.polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getSourcelat() {
		return this.sourcelat;
	}

	public void setSourcelat(double sourcelat) {
		this.sourcelat = sourcelat;
	}

	public double getSourcelng() {
		return this.sourcelng;
	}

	public void setSourcelng(double sourcelng) {
		this.sourcelng = sourcelng;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="tripid", insertable = false, updatable = false)
	private Set<PoolRqst> poolrqsts = new HashSet<PoolRqst>();


	public Set<PoolRqst> getPoolrqsts() {
		return poolrqsts;
	}

	public void setPoolrqsts(Set<PoolRqst> poolrqsts) {
		this.poolrqsts = poolrqsts;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="objectid", referencedColumnName="objectid", insertable=false, updatable=false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}