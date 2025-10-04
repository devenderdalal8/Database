@Entity
@Table(name = "temperature_measurements") 
public class TemperatureMeasurement{
    @EmbeddedId
    private TemperatureMeasurementKey id;
    private double temperature;
}

@Embeddable
public class TemperatureMeasurementKey implements Serializable {
    private Integer cityId;
    private LocalDate measurementDate;

    // getters and setters
}

// This approach works because the composite key includes the partition key 
// (measurement_date), which satisfies PostgreSQL requirements and JPA 
// requirements for unique identification.
