package com.antibyteapps.fenerinmacivar.network;

import java.io.Serializable;

/**
 * @author Orhun Dalabasmaz
 */
public class MatchEvent implements Serializable {
	private String eventDate = "";
	private String eventHeader = "";
	private String eventName = "";
	private String eventPlaceAndTime = "";
	private String eventPlace = "";
	private String eventTime = "";
	private String eventBroadcaster = "";

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventHeader() {
		return eventHeader;
	}

	public void setEventHeader(String eventHeader) {
		this.eventHeader = eventHeader;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventPlaceAndTime() {
		return eventPlaceAndTime;
	}

	public void setEventPlaceAndTime(String eventPlaceAndTime) {
		this.eventPlaceAndTime = eventPlaceAndTime;
	}

	public String getEventBroadcaster() {
		return eventBroadcaster;
	}

	public void setEventBroadcaster(String eventBroadcaster) {
		this.eventBroadcaster = eventBroadcaster;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventPlace() {
		return eventPlace;
	}

	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}

	/*@Override
	public String toString() {
		return eventDate + " # " +
				eventHeader + " # " +
				eventName + " # " +
				eventPlaceAndTime + " # " +
//				eventPlace + " # " +
//				eventTime + " # " +
				eventBroadcaster;
	}*/

	@Override
	public String toString() {
		return eventDate + " " +
				eventHeader + " " +
				eventName + " " +
				eventPlaceAndTime + " " +
				eventBroadcaster + "\n";
	}
}
