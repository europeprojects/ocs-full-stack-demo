package com.mak.demo.poll.manager.rdms.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name="Vote")
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "POLL_ID",
                "USERNAME",
                "DELETED_AT"
        })
})
@SQLDelete(sql = "UPDATE Vote SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_AT is null")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "GEN_VOTE", sequenceName = "SEQ_VOTE", allocationSize = 1 )
    @GeneratedValue(generator = "GEN_VOTE", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POLL_ID", nullable = false)
    private PollEntity poll;

    @ManyToOne
    @JoinColumn
    private ChoiceEntity choice;

    @Column(name = "USERNAME")
    private String username;


}
