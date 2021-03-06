package com.codingnomads.impacttracker.presentation.api;

import com.codingnomads.impacttracker.logic.JWT.AuthenticationService;
import com.codingnomads.impacttracker.logic.commitment.CommitmentService;
import com.codingnomads.impacttracker.logic.commitment.CommitmentUtils;
import com.codingnomads.impacttracker.model.Commitment;
import com.codingnomads.impacttracker.model.CommitmentWithReduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commitments")
public class CommitmentApiController {
    private CommitmentService commitmentService;

    private AuthenticationService authenticationService;

    @Autowired
    public CommitmentApiController(CommitmentService commitmentService, AuthenticationService authenticationService) {
        this.commitmentService = commitmentService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public List<CommitmentWithReduction> allCommitments(@RequestParam(name = "token") String tokenValue) {
        authenticationService.validateToken(tokenValue);
        int userIdFromToken = authenticationService.getUserIdFromToken(tokenValue);

        List<CommitmentWithReduction> commitments = commitmentService.getCommitmentsWithReductionsFromUserId(userIdFromToken);
        return commitments;
    }

    @PostMapping("/addcommitment")
    public Commitment addCommitment(@RequestParam(name = "token") String tokenValue, @RequestBody Commitment commitment) {
        authenticationService.validateToken(tokenValue);
        int userIdFromToken = authenticationService.getUserIdFromToken(tokenValue);
        commitment.setUserId(userIdFromToken);
        return commitmentService.save(commitment);
    }

    @PutMapping("/updatecommitment/{id}")
    public Commitment updateCommitmentById(@RequestParam(name = "token") String tokenValue, @PathVariable int id, @RequestBody Commitment commitment) {
        authenticationService.validateToken(tokenValue);
        return commitmentService.updateCommitmentById(id, commitment);
    }

    @DeleteMapping("/deletecommitment/{id}")
    public ResponseEntity<Commitment> deleteCommitment(@RequestParam(name = "token") String tokenValue, @PathVariable int id) {
        authenticationService.validateToken(tokenValue);
        commitmentService.deleteCommitmentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
