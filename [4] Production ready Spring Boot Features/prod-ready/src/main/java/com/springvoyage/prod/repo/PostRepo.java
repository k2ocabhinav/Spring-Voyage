package com.springvoyage.prod.repo;

import com.springvoyage.prod.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, Long> {
}
