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
public class UpdateEventAdminRequest {
  private String title;
  private String annotation;
  private String description;
  private Location location;
  private Long category;
  private String eventDate;
  private Boolean paid;
  private int participantLimit;
  private Boolean requestModeration;
  private StateAction stateAction;


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateEventAdminRequest updateEventAdminRequest = (UpdateEventAdminRequest) o;
    return Objects.equals(this.annotation, updateEventAdminRequest.annotation) &&
        Objects.equals(this.category, updateEventAdminRequest.category) &&
        Objects.equals(this.description, updateEventAdminRequest.description) &&
        Objects.equals(this.eventDate, updateEventAdminRequest.eventDate) &&
        Objects.equals(this.location, updateEventAdminRequest.location) &&
        Objects.equals(this.paid, updateEventAdminRequest.paid) &&
        Objects.equals(this.participantLimit, updateEventAdminRequest.participantLimit) &&
        Objects.equals(this.requestModeration, updateEventAdminRequest.requestModeration) &&
        Objects.equals(this.stateAction, updateEventAdminRequest.stateAction) &&
        Objects.equals(this.title, updateEventAdminRequest.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit, requestModeration, stateAction, title);
  }

  @Override
  public String toString() {
    return "class UpdateEventAdminRequest {\n" +
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
