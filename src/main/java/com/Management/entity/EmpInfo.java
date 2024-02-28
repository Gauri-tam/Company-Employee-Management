package com.Management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmpInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer infoId;
    private String empFirstName;
    private String empLastName;
    private String empPhone;

    @JsonIgnore
    @OneToOne(mappedBy = "empInfo")
    private Employee employee;
}
