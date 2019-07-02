package com.example.airbnb.repositories;

import java.util.List;
import com.example.airbnb.entities.Review;
import com.example.airbnb.mappers.ReviewRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * ReviewRepository
 */
@Transactional
@Repository
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
      }

    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM \"review\"";
        RowMapper<Review> rowMapper = new ReviewRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }
    
    public List<Review> getReviewsByPropertyId(int id){
        String sql = "SELECT * FROM review WHERE property_id = ?";
        RowMapper<Review> rowMapper = new ReviewRowMapper();
        return this.jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
    }

    public void createReview(Review review, int id) {
        String sql = "INSERT INTO review VALUES(?, ?, ?) ";
        int rating = review.getRating();
        String remark = review.getRemark();
        this.jdbcTemplate.update(sql, rating,remark,id);
       
        String select = "SELECT top 1 id from review order by id desc ";
        int reviewId = this.jdbcTemplate.queryForObject(select, Integer.class);
  
        review.setId(reviewId);  
    }
    
}