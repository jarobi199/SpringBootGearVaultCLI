package io.gearvault.model;

import java.time.LocalDate;

public record BookingNote(LocalDate eventDate, String setupDetails, int headcount, String notes){}
