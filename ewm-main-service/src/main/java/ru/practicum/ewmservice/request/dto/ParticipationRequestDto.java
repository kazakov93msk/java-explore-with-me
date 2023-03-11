package ru.practicum.ewmservice.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmservice.request.property.RequestStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime created;
  private Long event;
  private Long id;
  private Long requester;
  private RequestStatus status;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParticipationRequestDto participationRequestDto = (ParticipationRequestDto) o;
    return Objects.equals(this.created, participationRequestDto.created) &&
        Objects.equals(this.event, participationRequestDto.event) &&
        Objects.equals(this.id, participationRequestDto.id) &&
        Objects.equals(this.requester, participationRequestDto.requester) &&
        Objects.equals(this.status, participationRequestDto.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(created, event, id, requester, status);
  }


  @Override
  public String toString() {
    return "class ParticipationRequestDto {\n" +
            "    created: " + toIndentedString(created) + "\n" +
            "    event: " + toIndentedString(event) + "\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    requester: " + toIndentedString(requester) + "\n" +
            "    status: " + toIndentedString(status) + "\n" +
            "}";
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
