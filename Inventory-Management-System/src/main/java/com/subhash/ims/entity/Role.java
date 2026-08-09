package com.subhash.ims.entity;

import com.subhash.ims.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable=false,unique=true)
    private RoleType name;
}
