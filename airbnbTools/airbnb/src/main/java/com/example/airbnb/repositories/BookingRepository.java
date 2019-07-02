package com.example.airbnb.repositories;

import java.util.List;
import com.example.airbnb.entities.Booking;
import com.example.airbnb.mappers.BookingRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookingRepository
 */
@Transactional
@Repository
public class BookingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
      }
    
      public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BookingRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<Booking> getBookingsByPropertyId(int id){
      String sql = "SELECT * FROM booking WHERE property_id = ?";
      RowMapper<Booking> rowMapper = new BookingRowMapper();
      return this.jdbcTemplate.query(sql, new Object[]{id},rowMapper);
  }

    public void updateTotalPrice(int id, Booking booking) {
    String sql = "UPDATE booking SET totalprice = ? where id=?";
    int totalPrice = booking.getTotalPrice();
    this.jdbcTemplate.update(sql, totalPrice, id);
   
    // String select = "SELECT top 1 id FROM booking order by id desc";
    // int bookingId = this.jdbcTemplate.queryForObject(select, Integer.class);

    // booking.setId(bookingId);  
}
}