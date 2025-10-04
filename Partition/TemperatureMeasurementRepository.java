package Partition;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureMeasurementRepository extends JpaRepository<TemperatureMeasurement, TemperatureKey> {
    List<TemperatureMeasurement> findByIdMeasurementDate(LocalDate date);
}