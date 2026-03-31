package election.votepoll.payload.response;

import election.votepoll.models.ElectionStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ElectionDTO {
    private Long id;
    private String name;
    private String details;
    private LocalDate startDate;
    private LocalDate endDate;
    private ElectionStatus status;
}
