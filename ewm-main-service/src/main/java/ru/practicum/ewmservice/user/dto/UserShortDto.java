package ru.practicum.ewmservice.user.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortDto {
  private Long id;
  private String name;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserShortDto userShortDto = (UserShortDto) o;
    return Objects.equals(this.id, userShortDto.id) &&
        Objects.equals(this.name, userShortDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "class UserShortDto {\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    name: " + toIndentedString(name) + "\n" +
            "}";
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
