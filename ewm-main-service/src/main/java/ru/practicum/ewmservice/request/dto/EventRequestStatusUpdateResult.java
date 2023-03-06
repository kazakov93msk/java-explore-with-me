package ru.practicum.ewmservice.request.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateResult {
  private List<ParticipationRequestDto> confirmedRequests;
  private List<ParticipationRequestDto> rejectedRequests;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventRequestStatusUpdateResult eventRequestStatusUpdateResult = (EventRequestStatusUpdateResult) o;
    return Objects.equals(this.confirmedRequests, eventRequestStatusUpdateResult.confirmedRequests) &&
        Objects.equals(this.rejectedRequests, eventRequestStatusUpdateResult.rejectedRequests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(confirmedRequests, rejectedRequests);
  }

  @Override
  public String toString() {
    return "class EventRequestStatusUpdateResult {\n" +
            "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
            "    rejectedRequests: " + toIndentedString(rejectedRequests) + "\n" +
            "}";
  }

  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
