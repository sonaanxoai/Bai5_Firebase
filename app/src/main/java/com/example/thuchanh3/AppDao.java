package com.example.thuchanh3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    long insertFruit(Fruit fruit);

    @Query("SELECT * FROM fruits")
    List<Fruit> getAllFruits();

    @Query("SELECT * FROM fruits WHERE name = :name LIMIT 1")
    Fruit getFruitByName(String name);

    @Update
    void updateFruit(Fruit fruit);

    @Insert
    long insertEmployee(Employee employee);

    @Query("SELECT * FROM employees")
    List<Employee> getAllEmployees();

    @Query("SELECT * FROM employees WHERE name = :name LIMIT 1")
    Employee getEmployeeByName(String name);

    @Insert
    void insertTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    List<Transaction> getAllTransactions();
    
    @Query("SELECT * FROM fruits WHERE expiryDate < :currentDate")
    List<Fruit> getExpiredFruits(String currentDate);
}
