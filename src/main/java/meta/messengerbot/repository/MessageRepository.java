package meta.messengerbot.repository;

import meta.messengerbot.domain.MessageDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<MessageDomain, String> {
}