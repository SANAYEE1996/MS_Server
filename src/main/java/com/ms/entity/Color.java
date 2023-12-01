package com.ms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "color")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

}