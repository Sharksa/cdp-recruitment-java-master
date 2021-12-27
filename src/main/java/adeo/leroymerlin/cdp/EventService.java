package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    @Transactional
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        // Get stream of events
        List<Event> events = eventRepository.findAllBy().stream()
                // Filter event
                .filter(event -> event.getBands()
                        // Get stream of bands for this event
                        .stream()
                        .anyMatch(bandElement -> bandElement.getMembers()
                                // Get stream of members for a band
                                .stream()
                                // Check if a member name for a band match the query
                                .anyMatch(memberElement -> memberElement.getName().contains(query))
                        )
                ).collect(Collectors.toList());

        // Add counter to display result with the good count
        for(Event currentEvent : events){
            currentEvent.setTitle(currentEvent.getTitle() + " [" + currentEvent.getBands().size() + "]");
            for(Band currentBand : currentEvent.getBands()) {
                currentBand.setName(currentBand.getName() + " [" + currentBand.getMembers().size() + "]");
            }
        }

        return events;
    }

    @Transactional
    public void update(Long id, Event event) {
        // Try to get element by id
        Optional<Event> existingEvent = eventRepository.findById(id);

        if(existingEvent.isPresent()) {
            if (existingEvent.get().getId().equals(event.getId())) {
                eventRepository.save(event);
            } else {
                System.err.println("Ids are not the same.");
            }
        }else{
            System.err.println("Event id " + id + " not found");
        }
    }

}
