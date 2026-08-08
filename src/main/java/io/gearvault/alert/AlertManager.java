package io.gearvault.alert;

import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AlertManager {

    private final List<AlertStrategy> strategies;

    public AlertManager(List<AlertStrategy> strategies) {
        this.strategies = strategies;
    }
    public List<AlertResult> evaluate(List<Equipment> equipment) {
        return equipment.stream()
            .flatMap(e -> strategies.stream()
                .filter(s -> s.supports(e))
                .map(s -> s.evaluate(e)))
                .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}