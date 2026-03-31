package election.votepoll.payload.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ElectionRequest {
    private String name;
    private String details;
    private LocalDate startDate;
    private LocalDate endDate;
}
