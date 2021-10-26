package ptit.ltw.Service.IService;

import ptit.ltw.Entity.Post;
import ptit.ltw.Entity.Subscriber;

import javax.validation.constraints.NotNull;

public interface SubscriberService {
    void save(@NotNull Subscriber subscriber);
    void sendNewPostToSub(Post post);
}
