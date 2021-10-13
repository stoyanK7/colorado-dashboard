package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.models.PrintSquareMeterPerMediaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepo extends JpaRepository<PrintSquareMeterPerMediaCategory,Long> {
    PrintSquareMeterPerMediaCategory findPrintSquareMeterPerMediaTypeById(long id);
}
