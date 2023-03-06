package ru.practicum.ewmservice.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
  private List<Long> events;
  @NotNull
  private Boolean pinned;
  @NotBlank
  private String title;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewCompilationDto newCompilationDto = (NewCompilationDto) o;
    return Objects.equals(this.events, newCompilationDto.events) &&
        Objects.equals(this.pinned, newCompilationDto.pinned) &&
        Objects.equals(this.title, newCompilationDto.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events, pinned, title);
  }

  @Override
  public String toString() {
    return "class NewCompilationDto {\n" +
            "    events: " + toIndentedString(events) + "\n" +
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
