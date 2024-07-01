package meta.messenger_bot.repository;

import meta.messenger_bot.domain.MessageDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<MessageDomain, String> {
}