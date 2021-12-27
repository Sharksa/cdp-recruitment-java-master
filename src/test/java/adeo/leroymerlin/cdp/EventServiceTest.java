package adeo.leroymerlin.cdp;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Test
    public void updateEvent() throws Exception {
        // Given
        EventRepository eventRepository = mock(EventRepository.class);
        Event event = new Event();
        event.setId(1L);
        event.setNbStars(3);
        event.setComment("Test comment");
        event.setImgUrl("Test IMG URL");
        event.setTitle("Test title");
        Band band = new Band();
        band.setName("Test band");
        Set<Band> bands = new HashSet<>();
        bands.add(band);
        event.setBands(bands);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // Change event to update number of stars on the element
        event.setNbStars(4);

        EventService eventService = new EventService(eventRepository);

        // When
        eventService.update(1L, event);

        // Then
        verify(eventRepository).save(event);
        assertThat(event.getNbStars()).isEqualTo(4);
    }

    @Test
    public void deleteEvent() throws Exception {
        // Given
        EventRepository eventRepository = mock(EventRepository.class);
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        EventService eventService = new EventService(eventRepository);

        // When
        eventService.update(1L, event);
        eventService.delete(1L);;

        // Then
        verify(eventRepository).deleteById(1L);
    }

    @Test
    public void getEvent() throws Exception {
        // Given
        EventRepository eventRepository = mock(EventRepository.class);
        Event event = new Event();
        event.setId(1L);
        List<Event> events = List.of(event);

        when(eventRepository.findAllBy()).thenReturn(events);

        EventService eventService = new EventService(eventRepository);

        // When
        List<Event> eventsResult = eventService.getEvents();

        // Then
        assertThat(eventsResult.size()).isEqualTo(1);
    }

    @Test
    public void getFilteredEvent() throws Exception {
        // Given
        EventRepository eventRepository = mock(EventRepository.class);
        Event event = new Event();
        Event event2 = new Event();
        Event event3 = new Event();

        event.setId(1L);
        Set<Band> bands = new HashSet<>();
        Set<Member> members = new HashSet<>();
        Member member = new Member();
        member.setName("John");
        members.add(member);
        Band band = new Band();
        band.setMembers(members);
        bands.add(band);
        event.setBands(bands);

        event2.setId(2L);
        Set<Band> bands2 = new HashSet<>();
        Set<Member> members2 = new HashSet<>();
        Member member2 = new Member();
        member2.setName("Doe");
        members2.add(member2);
        Band band2 = new Band();
        band2.setMembers(members2);
        bands2.add(band2);
        event2.setBands(bands2);

        event3.setId(3L);
        Set<Band> bands3 = new HashSet<>();
        Set<Member> members3 = new HashSet<>();
        Member member3 = new Member();
        member3.setName("Jr");
        members3.add(member3);
        Band band3 = new Band();
        band3.setMembers(members3);
        bands3.add(band3);
        event3.setBands(bands3);

        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        events.add(event3);

        when(eventRepository.findAllBy()).thenReturn(events);

        EventService eventService = new EventService(eventRepository);

        // When
        List<Event> eventsResult = eventService.getFilteredEvents("Doe");

        // Then
        assertThat(eventsResult.size()).isEqualTo(1);
        assertThat(eventsResult.get(0).getId()).isEqualTo(2L);
    }






}