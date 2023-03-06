package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.event.property.StateAction;
import ru.practicum.ewmservice.location.model.Location;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventUserRequest {
  private String annotation;
  private Long category;
  private String description;
  private String eventDate;
  private Location location;
  private Boolean paid;
  private Integer participantLimit;
  private Boolean requestModeration;
  private StateAction stateAction;
  private String title;

  public UpdateEventUserRequest annotation(String annotation) {
    this.annotation = annotation;
    return this;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateEventUserRequest updateEventUserRequest = (UpdateEventUserRequest) o;
    return Objects.equals(this.annotation, updateEventUserRequest.annotation) &&
        Objects.equals(this.category, updateEventUserRequest.category) &&
        Objects.equals(this.description, updateEventUserRequest.description) &&
        Objects.equals(this.eventDate, updateEventUserRequest.eventDate) &&
        Objects.equals(this.location, updateEventUserRequest.location) &&
        Objects.equals(this.paid, updateEventUserRequest.paid) &&
        Objects.equals(this.participantLimit, updateEventUserRequest.participantLimit) &&
        Objects.equals(this.requestModeration, updateEventUserRequest.requestModeration) &&
        Objects.equals(this.stateAction, updateEventUserRequest.stateAction) &&
        Objects.equals(this.title, updateEventUserRequest.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit, requestModeration, stateAction, title);
  }

  @Override
  public String toString() {
    return "class UpdateEventUserRequest {\n" +
            "    annotation: " + toIndentedString(annotation) + "\n" +
            "    category: " + toIndentedString(category) + "\n" +
            "    description: " + toIndentedString(description) + "\n" +
            "    eventDate: " + toIndentedString(eventDate) + "\n" +
            "    location: " + toIndentedString(location) + "\n" +
            "    paid: " + toIndentedString(paid) + "\n" +
            "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
            "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
            "    stateAction: " + toIndentedString(stateAction) + "\n" +
            "    title: " + toIndentedString(title) + "\n" +
            "}";
  }
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
