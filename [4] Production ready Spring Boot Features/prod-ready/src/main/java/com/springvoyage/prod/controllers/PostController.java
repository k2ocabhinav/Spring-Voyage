package com.springvoyage.prod.controllers;

import com.springvoyage.prod.dto.PostDTO;
import com.springvoyage.prod.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PutMapping(path = "/{postId}")
        public PostDTO updatePostById(@PathVariable Long postId, @RequestBody PostDTO postDTO){
        return postService.updatePostById(postId, postDTO);

    }

}
