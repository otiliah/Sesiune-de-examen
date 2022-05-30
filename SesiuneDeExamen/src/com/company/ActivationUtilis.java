/* author: otiliaH*/

package com.company;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActivationUtilis {
    //transformarea duratei primita ca parametru in zile, ore, minute si secunde
    public static String durationToDHMS(Duration d) {
        int days = (int) d.toDays();
        int hours = (int) d.minusDays(days).toHours();
        int minutes = (int) d.minusDays(days).minusHours(hours).toMinutes();
        int seconds = (int) d.minusDays(days).minusHours(hours).minusMinutes(minutes).getSeconds();
        return "" + days + " zile, " + hours + " ore, " + minutes + " minute, " + seconds + " secunde";
    }

    //crearea unui array cu activari consecutive care acopera intreaga perioada start-end, cu sesiuni de durata d
    public static Activation[] generateActivationSeries(LocalDateTime start, LocalDateTime end, Duration d) {
        Duration total = Duration.between(start, end);
        double totalMinute = total.toMinutes();
        double dMinute = d.toMinutes();
        int length = (int) (Math.ceil(totalMinute / dMinute));
        Activation[] activations = new Activation[length];

        for (int i = 0; i < length - 1; i++) {
            activations[i] = Activation.getInstance(start, start.plus(d).minusSeconds(1));
            start = start.plus(d);
        }

        activations[length - 1] = Activation.getInstance(start, end);
        return activations;
    }

    //generarea unui array cu un numar de activari generate aleator
    public static Activation[] generateRandomActivations(int nrActivations, Duration minDuration, Duration maxDuration, LocalDate minDate, LocalDate maxDate) {
        Activation[] actvsRandom = new Activation[nrActivations];

        for (int i = 0; i < nrActivations; i++) {
            Duration d = Duration.ofMinutes(durataAleatoare(minDuration, maxDuration));
            actvsRandom[i] = Activation.getInstance(startActvRandom(minDate, maxDate), d);
            if (actvsRandom[i] == null) {
                i--;
                continue;
            }
            for (int m = 0; m < i; m++) {
                if (actvsRandom[i].overlaps(actvsRandom[m])) {
                    actvsRandom[i] = null;
                    i--;
                }
            }
        }
        return actvsRandom;
    }

    public static long durataAleatoare(Duration d1, Duration d2) {
        double d1Minute = d1.toMinutes();
        double d2Minute = d2.toMinutes();
        return (long) (Math.random() * (d2Minute - d1Minute) + d1Minute);
    }

    public static LocalDateTime startActvRandom(LocalDate minDate, LocalDate maxDate) {
        LocalDateTime startActv = minDate.atTime(0, 0);
        LocalDateTime endActv = maxDate.atTime(23, 59);
        LocalDateTime startRandom = startActv.plus(Duration.ofMinutes(durataAleatoare(Duration.ofSeconds(1), Duration.between(startActv, endActv))));
        return startRandom.isBefore(endActv) ? startRandom : null;
    }
}
