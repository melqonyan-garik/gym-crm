package com.epam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(columnDefinition="text", length=10485760)
    private String token;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
