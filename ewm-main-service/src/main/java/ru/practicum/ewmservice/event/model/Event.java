package ru.practicum.ewmservice.event.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.location.model.Location;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Length(max = 7000)
    @NotBlank
    private String description;
    @Length(max = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn;
    private LocalDateTime createdOn;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private Boolean paid;
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState state;
    private Integer participantLimit;
    private int views;
    @Transient
    private int confirmedRequests;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description.substring(0, 50) + "..." + '\'' +
                ", annotation='" + annotation + '\'' +
                ", category=" + category +
                ", eventDate=" + eventDate +
                ", publishedOn=" + publishedOn +
                ", createdOn=" + createdOn +
                ", location=" + location +
                ", initiator=" + initiator +
                ", paid=" + paid +
                ", requestModeration=" + requestModeration +
                ", state=" + state +
                ", participantLimit=" + participantLimit +
                ", views=" + views +
                ", confirmedRequests=" + confirmedRequests +
                '}';
    }
}
