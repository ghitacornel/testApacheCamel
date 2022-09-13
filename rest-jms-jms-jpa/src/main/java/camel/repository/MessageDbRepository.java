package camel.repository;

import camel.repository.entity.MessageDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDbRepository extends JpaRepository<MessageDb, Integer> {
}
