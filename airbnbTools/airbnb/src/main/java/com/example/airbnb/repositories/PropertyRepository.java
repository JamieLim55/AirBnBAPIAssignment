package com.example.airbnb.repositories;

import java.util.List;

import com.example.airbnb.entities.Property;
import com.example.airbnb.mappers.PropertyRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * PropertyRepository
 */
@Transactional
@Repository
public class PropertyRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PropertyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
      }

    public List<Property> getAllPropertys() {
        String sql = "SELECT * FROM property";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    } 

    public List<Property> getPropertyByAddress(String address){
        String sql = "SELECT * FROM property where address like ?";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        String x = "%" + address + "%";
        return this.jdbcTemplate.query(sql, new Object[]{x} ,rowMapper); 
    }
    
    public List<Property> getPropertyByNumRooms(int numRooms){
        String sql = "SELECT * FROM property where numRooms = ?";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, new Object[]{numRooms} ,rowMapper); 
    }

    public List<Property> getPropertyByPrice(int price){
        String sql = "SELECT * FROM property where price = ?";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, new Object[]{price} ,rowMapper); 
    }

    public List<Property> searchProperties(String address, int numRooms, int price){
        String sql = "SELECT * FROM property WHERE address LIKE ? AND numRooms = ? AND price = ? ";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql,new Object[] {"%" + address + "%", numRooms, price} , rowMapper);
    }

    public List<Property> getBookedProperties(){
        String sql = "SELECT DISTINCT property.* FROM property JOIN review ON review.property_id = property.id";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);

    }

    public List<Property> getReviewedProperties(){
        String sql = "SELECT DISTINCT property.* FROM property JOIN booking ON booking.property_id = property.id";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);

    }

    // public void addProperty(String address, int numRooms, int price, Boolean allowSmoking, int maxGuestNum){
    //     String sql = "INSERT INTO \"property\" VALUES (?, ?, ?, ?, ?)";
    //     this.jdbcTemplate.update(sql, address, numRooms, price, allowSmoking, maxGuestNum);
    // }

    public void createProperty(Property property){
        String sql = "INSERT INTO property VALUES (?,?,?,?,?)";
        this.jdbcTemplate.update(sql, property.getAddress(), property.getNumRooms(), property.getPrice(), property.getAllowSmoking(), property.getMaxGuestNum());

    }

    public void updateProperty(Boolean oldValue, Boolean newValue){
        String sql = "UPDATE \"property\" SET allowSmoking = ? WHERE allowSmoking = ?";
        this.jdbcTemplate.update(sql, oldValue, newValue );
    }

    public void deleteProperty(int id){
        String sql = "DELETE FROM \"property\" WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    public void createProperties(Property property) {
        String sql = "INSERT INTO \"property\" VALUES(?, ?, ?, ?, ?) ";
        String address = property.getAddress();
        int numRooms = property.getNumRooms();
        int price = property.getPrice();
        boolean allowSmoking = property.getAllowSmoking();
        int maxGuestNum = property.getMaxGuestNum();
        this.jdbcTemplate.update(sql, address,numRooms,price,allowSmoking,maxGuestNum);
       
        String select = "SELECT id FROM \"property\" WHERE address = ?";
        int propertyId = this.jdbcTemplate.queryForObject(select, Integer.class, address);
  
        property.setId(propertyId);  

    }
}