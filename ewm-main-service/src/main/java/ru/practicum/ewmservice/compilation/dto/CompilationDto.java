package ru.practicum.ewmservice.compilation.dto;

import lombok.*;
import ru.practicum.ewmservice.event.dto.EventShortDto;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
  private Set<EventShortDto> events;
  private Long id;
  private Boolean pinned;
  private String title;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompilationDto compilationDto = (CompilationDto) o;
    return Objects.equals(this.events, compilationDto.events) &&
        Objects.equals(this.id, compilationDto.id) &&
        Objects.equals(this.pinned, compilationDto.pinned) &&
        Objects.equals(this.title, compilationDto.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events, id, pinned, title);
  }

  @Override
  public String toString() {
    return "class CompilationDto {\n" +
            "    events: " + toIndentedString(events) + "\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    pinned: " + toIndentedString(pinned) + "\n" +
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
