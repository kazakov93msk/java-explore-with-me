package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.event.property.EventState;
import ru.practicum.ewmservice.location.dto.LocationDto;
import ru.practicum.ewmservice.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {
  private Long id;
  private String title;
  private String description;
  private String annotation;
  private CategoryDto category;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdOn;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime eventDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime publishedOn;
  private UserShortDto initiator;
  private LocationDto location;
  private Boolean paid;
  private Boolean requestModeration;
  private EventState state;
  private Integer participantLimit;
  private int views;
  private int confirmedRequests;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventFullDto eventFullDto = (EventFullDto) o;
    return Objects.equals(this.annotation, eventFullDto.annotation) &&
        Objects.equals(this.category, eventFullDto.category) &&
        Objects.equals(this.confirmedRequests, eventFullDto.confirmedRequests) &&
        Objects.equals(this.createdOn, eventFullDto.createdOn) &&
        Objects.equals(this.description, eventFullDto.description) &&
        Objects.equals(this.eventDate, eventFullDto.eventDate) &&
        Objects.equals(this.id, eventFullDto.id) &&
        Objects.equals(this.initiator, eventFullDto.initiator) &&
        Objects.equals(this.location, eventFullDto.location) &&
        Objects.equals(this.paid, eventFullDto.paid) &&
        Objects.equals(this.participantLimit, eventFullDto.participantLimit) &&
        Objects.equals(this.publishedOn, eventFullDto.publishedOn) &&
        Objects.equals(this.requestModeration, eventFullDto.requestModeration) &&
        Objects.equals(this.state, eventFullDto.state) &&
        Objects.equals(this.title, eventFullDto.title) &&
        Objects.equals(this.views, eventFullDto.views);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation, category, confirmedRequests, createdOn, description, eventDate, id, initiator, location, paid, participantLimit, publishedOn, requestModeration, state, title, views);
  }

  @Override
  public String toString() {
    return "class EventFullDto {\n" +
            "    annotation: " + toIndentedString(annotation) + "\n" +
            "    category: " + toIndentedString(category) + "\n" +
            "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
            "    createdOn: " + toIndentedString(createdOn) + "\n" +
            "    description: " + toIndentedString(description) + "\n" +
            "    eventDate: " + toIndentedString(eventDate) + "\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    initiator: " + toIndentedString(initiator) + "\n" +
            "    location: " + toIndentedString(location) + "\n" +
            "    paid: " + toIndentedString(paid) + "\n" +
            "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
            "    publishedOn: " + toIndentedString(publishedOn) + "\n" +
            "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
            "    state: " + toIndentedString(state) + "\n" +
            "    title: " + toIndentedString(title) + "\n" +
            "    views: " + toIndentedString(views) + "\n" +
            "}";
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
