package ru.practicum.ewmservice.user.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
  private String email;
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
    UserDto userDto = (UserDto) o;
    return Objects.equals(this.email, userDto.email) &&
        Objects.equals(this.id, userDto.id) &&
        Objects.equals(this.name, userDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, id, name);
  }

  @Override
  public String toString() {
    return "class UserDto {\n" +
            "    email: " + toIndentedString(email) + "\n" +
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
