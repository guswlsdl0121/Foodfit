package Foodfit.BackEnd.Repository;

import Foodfit.BackEnd.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

