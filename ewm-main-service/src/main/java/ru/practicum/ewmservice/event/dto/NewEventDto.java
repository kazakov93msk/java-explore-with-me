package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
  @NotBlank
  private String title;
  @NotBlank
  private String annotation;
  @NotBlank
  private String description;
  @NotNull
  private Long category;
  @NotNull
  private Location location;
  @NotNull
  private String eventDate;
  private Boolean paid;
  private Integer participantLimit;
  private Boolean requestModeration;


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewEventDto newEventDto = (NewEventDto) o;
    return Objects.equals(this.annotation, newEventDto.annotation) &&
        Objects.equals(this.category, newEventDto.category) &&
        Objects.equals(this.description, newEventDto.description) &&
        Objects.equals(this.eventDate, newEventDto.eventDate) &&
        Objects.equals(this.location, newEventDto.location) &&
        Objects.equals(this.paid, newEventDto.paid) &&
        Objects.equals(this.participantLimit, newEventDto.participantLimit) &&
        Objects.equals(this.requestModeration, newEventDto.requestModeration) &&
        Objects.equals(this.title, newEventDto.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            annotation,
            category,
            description,
            eventDate,
            location,
            paid,
            participantLimit,
            requestModeration,
            title
    );
  }

  @Override
  public String toString() {
    return "class NewEventDto {\n" +
            "    annotation: " + toIndentedString(annotation) + "\n" +
            "    category: " + toIndentedString(category) + "\n" +
            "    description: " + toIndentedString(description) + "\n" +
            "    eventDate: " + toIndentedString(eventDate) + "\n" +
            "    location: " + toIndentedString(location) + "\n" +
            "    paid: " + toIndentedString(paid) + "\n" +
            "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
            "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
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
