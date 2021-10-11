package com.example.canondashboardapi.Repository.interfaces;

import com.example.canondashboardapi.Model.models.PrintSquareMeterPerMediaType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepo extends JpaRepository<PrintSquareMeterPerMediaType,Long> {
    PrintSquareMeterPerMediaType findPrintSquareMeterPerMediaTypeById(long id);
}
