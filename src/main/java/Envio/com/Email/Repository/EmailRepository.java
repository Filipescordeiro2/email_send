package Envio.com.Email.Repository;

import Envio.com.Email.Entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
}
