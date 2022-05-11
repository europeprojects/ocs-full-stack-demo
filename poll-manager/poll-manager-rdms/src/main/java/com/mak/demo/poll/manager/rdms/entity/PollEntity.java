package com.mak.demo.poll.manager.rdms.entity;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Poll")
@Table(name = "POLL")
@SQLDelete(sql = "UPDATE Poll SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_AT is null")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "GEN_POLL", sequenceName = "SEQ_POLL", allocationSize = 1 )
    @GeneratedValue(generator = "GEN_POLL", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "QUESTION")
    private String question;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_APPROVED")
    private Boolean isApproved = true;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "POLL_ID")
    private List<ChoiceEntity> choices;

    private LocalDateTime expirationDateTime;
}
