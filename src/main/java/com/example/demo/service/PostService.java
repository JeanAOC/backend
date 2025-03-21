package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> findByUsuarioId(Long usuarioId) {
        return postRepository.findByUsuarioId(usuarioId);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

}
