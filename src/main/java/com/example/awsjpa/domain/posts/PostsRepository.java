package com.example.awsjpa.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // Mybatis나 ibatis에서 Dao라고 불리는 DB Layer 접근자.
    // Jpa에서는 Repository라고 부르며 인터페이스로 생성

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();


}