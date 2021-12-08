package api.coloradodashboard.squaremeterperprintmode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SquareMeterPerPrintModeRepository extends JpaRepository<SquareMeterPerPrintMode, Long> {
    List<SquareMeterPerPrintMode> getAllByOrderByDateAsc();
}
