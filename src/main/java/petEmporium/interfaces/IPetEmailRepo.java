package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.PetEmail;

@Repository
public interface IPetEmailRepo extends JpaRepository<PetEmail, Integer> {

    PetEmail findByUserEmail(String userEmail);

}
