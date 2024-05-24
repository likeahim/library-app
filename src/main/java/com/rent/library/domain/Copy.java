package com.rent.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "copies")
public class Copy {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "title_id")
    private Long titleId;

    @NotNull
    @Setter
    @Column(name = "status")
    private Status status;

}
