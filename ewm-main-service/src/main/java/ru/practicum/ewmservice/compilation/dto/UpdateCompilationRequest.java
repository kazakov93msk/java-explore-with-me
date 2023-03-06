package ru.practicum.ewmservice.compilation.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompilationRequest {
  private List<Long> events;
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
    UpdateCompilationRequest updateCompilationRequest = (UpdateCompilationRequest) o;
    return Objects.equals(this.events, updateCompilationRequest.events) &&
        Objects.equals(this.pinned, updateCompilationRequest.pinned) &&
        Objects.equals(this.title, updateCompilationRequest.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events, pinned, title);
  }

  @Override
  public String toString() {
    return "class UpdateCompilationRequest {\n" +
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
