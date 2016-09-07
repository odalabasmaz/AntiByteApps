package com.antibyteapps.fenerinmacivar.network;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Orhun Dalabasmaz
 * @since 05.04.2015.
 */
public class FBConnect {
	private final static FBConnect connection = new FBConnect();
	private final String DATE_HEADER = "tarihbaslik";
	private final List<String> WORDS_TO_OMIT = Arrays.asList("<br.*>", "&nbsp;", "\n");
	private final List<String> WORDS_TO_REPLACE = Arrays.asList("&.*;", "<br.*>", "&nbsp;", "<.*div>", "\n");

	private FBConnect() {
	}

	public static FBConnect getInstance() {
		return connection;
	}

	public List<MatchEvent> getEvents(final Document doc) {
		return generateMatchEvents(doc);
	}

	private List<MatchEvent> generateMatchEvents(final Document doc) {
		List<MatchEvent> matchEvents = new LinkedList<>();
		MatchEvent matchEvent = null;
		String eventDate;
		int count = 1;

		final Elements eventsByMonth = doc.select("ul.maclist");
		for (Element monthEvent : eventsByMonth) {
			final Elements eventsByDay = monthEvent.children();
			for (Element dayEvent : eventsByDay) {
				final Elements eventsInDay = dayEvent.children();
				if (eventsInDay.isEmpty()) continue;
				final Iterator<Element> eventsInDayIterator = eventsInDay.iterator();
				final Element firstEvent = eventsInDayIterator.next();
				final String firstEventValue = firstEvent.toString();
//					assert firstEventValue.contains(DATE_HEADER);
				eventDate = getEventValueByNode(firstEvent);

				Element event;
				while (eventsInDayIterator.hasNext()) {
					event = eventsInDayIterator.next();
					for (Node node : event.childNodes()) {
						final String nodeValue = formatValue(node);
						if (shouldOmit(nodeValue)) continue;

						final String value = getEventValueByNode(node).trim();
						final String formattedValue = formatValue(value);
						if (count == 1 || count == 5 || (count == 4 && !nodeValue.contains("Yayın"))) {
							if (matchEvent != null) {
								matchEvents.add(matchEvent);
							}
							count = 1;
							matchEvent = new MatchEvent();
							matchEvent.setEventHeader(formattedValue);
							matchEvent.setEventDate(formatValue(eventDate));
						} else {
//								assert matchEvent != null;
							if (count == 2) {
								matchEvent.setEventName(formattedValue);
							} else if (count == 3) {
								if (formattedValue.matches("^.*\\d\\d.\\d\\d$")) {
									matchEvent.setEventPlaceAndTime(formattedValue);
									// todo
									final String[] split = formattedValue.split("(-|/)");
									if (split.length == 1) {
										matchEvent.setEventTime(split[0].trim());
									} else if (split.length == 2) {
										matchEvent.setEventPlace(split[0].trim());
										matchEvent.setEventTime(split[1].trim());
									}
								} else {
									matchEvent.setEventName(matchEvent.getEventName() + " " + formattedValue);
									--count;
								}
							} else if (count == 4) {
								matchEvent.setEventBroadcaster(formattedValue);
							}
						}
						++count;
					}
				}
				matchEvents.add(matchEvent);
				matchEvent = null;
				count = 1;
			}
		}
		return matchEvents;
	}

	private String getEventValueByNode(Node element) {
		if (element.childNodes().isEmpty()) return element.toString();
		else {
			for (Node childNode : element.childNodes()) {
				if (StringUtils.isNotBlank(childNode.toString()))
					return getEventValueByNode(childNode);
			}
			return "";
		}
	}

	private boolean shouldOmit(final String val) {
		return StringUtils.isBlank(val) || WORDS_TO_OMIT.contains(val) || val.matches("<br.*>");
	}

	private String formatValue(Node node) {
		String nodeValue = node.toString();
		for (String word : WORDS_TO_REPLACE) {
			nodeValue = nodeValue.replaceAll(word, "");
		}
		return nodeValue.trim();
	}

	private String formatValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (final String word : WORDS_TO_REPLACE) {
				value = value.replaceAll(word, "");
			}
		}
		return value;
	}
}

