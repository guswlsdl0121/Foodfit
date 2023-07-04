package Foodfit.BackEnd.Domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "board_foods")
@Builder
@Getter
public class BoardFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_food_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    private Food food;
}
