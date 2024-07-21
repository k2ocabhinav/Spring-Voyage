package com.springvoyage.prod.services;

import com.springvoyage.prod.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePostById(Long postId, PostDTO inputPost);
}
