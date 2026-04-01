package election.votepoll.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateVoteException extends RuntimeException {
    public DuplicateVoteException(String message) {
        super(message);
    }
}
