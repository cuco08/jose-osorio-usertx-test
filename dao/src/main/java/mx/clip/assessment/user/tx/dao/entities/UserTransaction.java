package mx.clip.assessment.user.tx.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserTransaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String userId;

    private String transactionId;

    private double amount;

    private String description;

    private LocalDateTime date;
}
