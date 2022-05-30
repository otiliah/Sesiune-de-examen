/* author: otiliaH*/

package com.company;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Activation {
    LocalDateTime start;
    LocalDateTime end;

    public Activation(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    //crearea unei sesiuni de examen cu data de start si finish
    public static Activation getInstance(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            //System.out.println("Datele introduse sunt nule!");
            return null;
        }
        if (end.isBefore(start)) {
            //System.out.println("Eroare! Data de finish e inaintea datei de start!");
            return null;
        }
        if (start.getDayOfWeek() == DayOfWeek.SUNDAY || end.getDayOfWeek() == DayOfWeek.SUNDAY) {
            //System.out.println("Activarea NU incepe si nu se termina duminica!");
            return null;
        }
        if (((int) Duration.between(start, end).toDays() > 6) || (start.getDayOfWeek().getValue() - end.getDayOfWeek().getValue() > 0)) {
            //System.out.println("Activarea se suprapune peste zile de duminica!");
            return null;
        }
        return new Activation(start, end);
    }

    //crearea unei sesiuni de examen cu data de start si durata sesiunii
    public static Activation getInstance(LocalDateTime start, Duration d) {
        if (d == null) {
            //System.out.println("Duration is null!");
            return null;
        }
        return getInstance(start, start.plus(d));
    }

    //crearea unei sesiuni de examen cu data de start si durata sesiunii
    public static Activation getInstance(LocalDateTime start, int days, int hours, int minutes, int seconds) {
        Duration d = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        return getInstance(start, d);
    }

    //crearea unei sesiuni de examen cu date si ore de start si finish
    public static Activation getInstance(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return getInstance(startDate.atTime(startTime), endDate.atTime(endTime));
    }

    //verificarea suprapunerii cu o sesiune primita ca parametru
    public boolean overlaps(Activation s) {
        // (Start1 <= End2) && (End1 >= Start2)
        return start.isBefore(s.end) && end.isAfter(s.start);
    }

    public boolean containsFullDay() {
        return MonthDay.from(start).compareTo(MonthDay.from(end)) < 0 && start.getHour() < end.getHour();
    }

    public String getDescription() {
        return "\nActivarea va incepe " + getMomentDescription(start) +
                " si se incheie " + getMomentDescription(end) +
                ", si are o durata de: " + ActivationUtilis.durationToDHMS(Duration.between(start, end)) +
                ".";
    }

    public String getMomentDescription(LocalDateTime ldt) {
        Locale ro = new Locale("ro", "RO");
        // FORMAT: miercuri 03.11.2021 ora 15:02:14
        return ldt.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy 'ora' HH:mm:ss", ro));
    }

    public String toString() {
        return getDescription();
    }

}
