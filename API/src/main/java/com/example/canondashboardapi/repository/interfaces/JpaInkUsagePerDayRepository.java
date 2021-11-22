package com.example.canondashboardapi.repository.interfaces;

import com.example.canondashboardapi.model.InkUsagePerDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInkUsagePerDayRepository extends JpaRepository<InkUsagePerDay, Long> {
}
