package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ElectionDto {
    private Long id;

    @NotNull
    private String name;

    private String details;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
