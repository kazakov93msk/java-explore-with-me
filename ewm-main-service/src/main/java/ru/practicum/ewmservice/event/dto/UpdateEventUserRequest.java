package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmservice.event.property.StateAction;
import ru.practicum.ewmservice.location.dto.LocationDto;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventUserRequest {
  @Size(max = 2000)
  private String annotation;
  @PositiveOrZero
  private Long category;
  @Size(max = 7000)
  private String description;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime eventDate;
  private LocationDto location;
  private Boolean paid;
  @PositiveOrZero
  private Integer participantLimit;
  private Boolean requestModeration;
  private StateAction stateAction;
  @Size(max = 255)
  private String title;

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
