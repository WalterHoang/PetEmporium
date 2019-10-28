package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.PetUsers;

import java.util.List;

@Repository
public interface IPetUsersRepo extends JpaRepository<PetUsers, Integer> {

    PetUsers findByEmailEmailId(Integer emailId);

    List<PetUsers> findByLastName(String lastName);

    PetUsers findFirstByPhoneNumber(String phoneNumber);

}
