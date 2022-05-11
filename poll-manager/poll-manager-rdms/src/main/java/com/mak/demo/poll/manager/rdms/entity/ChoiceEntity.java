package com.mak.demo.poll.manager.rdms.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity(name = "Choice")
@Table(name = "CHOICE")
@SQLDelete(sql = "UPDATE Choice SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_AT is null")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceEntity extends BaseEntity{

    @Id
    @SequenceGenerator(name = "GEN_CHOICE", sequenceName = "SEQ_CHOICE", allocationSize = 1 )
    @GeneratedValue(generator = "GEN_CHOICE", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "TEXT")
    private String text;


}
