package ru.practicum.ewmservice.request.dto;

import ru.practicum.ewmservice.request.property.RequestStatus;

import lombok.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
  private List<Long> requestIds;
  private RequestStatus status;

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest = (EventRequestStatusUpdateRequest) o;
    return Objects.equals(this.requestIds, eventRequestStatusUpdateRequest.requestIds) &&
        Objects.equals(this.status, eventRequestStatusUpdateRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestIds, status);
  }

  @Override
  public String toString() {
    return "class EventRequestStatusUpdateRequest {\n" +
            "    requestIds: " + toIndentedString(requestIds) + "\n" +
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
