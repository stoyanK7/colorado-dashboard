package com.example.canondashboardapi.Repository.interfaces;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepo extends JpaRepository<PrintSquareMeterPerMediaCategory,Long> {
    PrintSquareMeterPerMediaCategory findPrintSquareMeterPerMediaTypeById(long id);
}
