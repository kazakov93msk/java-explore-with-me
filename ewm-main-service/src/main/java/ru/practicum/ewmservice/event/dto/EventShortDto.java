package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
  private Long id;
  @NotBlank
  private String title;
  @NotBlank
  private String annotation;
  @NotNull
  private CategoryDto category;
  @NotNull
  private String eventDate;
  @NotNull
  private UserShortDto initiator;
  @NotNull
  private Boolean paid;
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
    EventShortDto eventShortDto = (EventShortDto) o;
    return Objects.equals(this.annotation, eventShortDto.annotation) &&
        Objects.equals(this.category, eventShortDto.category) &&
        Objects.equals(this.confirmedRequests, eventShortDto.confirmedRequests) &&
        Objects.equals(this.eventDate, eventShortDto.eventDate) &&
        Objects.equals(this.id, eventShortDto.id) &&
        Objects.equals(this.initiator, eventShortDto.initiator) &&
        Objects.equals(this.paid, eventShortDto.paid) &&
        Objects.equals(this.title, eventShortDto.title) &&
        Objects.equals(this.views, eventShortDto.views);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation, category, confirmedRequests, eventDate, id, initiator, paid, title, views);
  }

  @Override
  public String toString() {
    return "class EventShortDto {\n" +
            "    annotation: " + toIndentedString(annotation) + "\n" +
            "    category: " + toIndentedString(category) + "\n" +
            "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
            "    eventDate: " + toIndentedString(eventDate) + "\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    initiator: " + toIndentedString(initiator) + "\n" +
            "    paid: " + toIndentedString(paid) + "\n" +
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
