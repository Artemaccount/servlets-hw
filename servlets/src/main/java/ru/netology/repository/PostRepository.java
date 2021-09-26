package ru.netology.repository;

import org.springframework.stereotype.Component;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PostRepository {

  private Map<Long, Post> repository;
  private final AtomicLong generateId;

  public PostRepository() {
    repository = new ConcurrentHashMap<>();
    generateId = new AtomicLong(0);
  }

  public List<Post> all() {
        List<Post> postList = new CopyOnWriteArrayList<>();
        for(Map.Entry<Long, Post> entry:repository.entrySet()){
          postList.add(entry.getValue());
    }
    return postList;
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(repository.get(id));
  }

  public Post save(Post post) {
    long postId = post.getId();
    if (postId == 0) {
      postId = generateId.incrementAndGet();
      repository.put(postId, post);
    } else {
      if (repository.get(post.getId()) != null) {
        repository.put(post.getId(), post);
      } else {
        return null;
      }
    }
    return post;
  }

  public void removeById(long id) {
    repository.remove(id);
  }
}
