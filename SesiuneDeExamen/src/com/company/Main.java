/* author: otiliaH*/

package com.company;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        LocalDate startLD = LocalDate.of(2021, 11, 8);
        LocalTime startLT = LocalTime.of(11, 0, 0);
        LocalDateTime startSerial = LocalDateTime.now();
        LocalDateTime endSerial = startSerial.plusHours(3);

        LocalDate startDateRandom = LocalDate.of(2021, 11, 1);
        LocalDate endDateRandom = LocalDate.of(2021, 11, 30);

/*        Duration d = Duration.ofHours(2).plusMinutes(30);

        Activation a1 = Activation.getInstance(start, end);
        System.out.println("a1: " + a1);

        Activation a2 = Activation.getInstance(start, d);
        System.out.println("a2: " + a2);

        Activation a3 = Activation.getInstance(start, 5, 5, 30, 0);
        System.out.println("a3: " + a3);

        Activation a4 = Activation.getInstance(startLD, startLT, startLD.plusDays(1), startLT.plusHours(1));
        System.out.println("a4: " + a4);*/

        Activation[] actvs = ActivationUtilis.generateActivationSeries(startSerial, endSerial, Duration.ofHours(1));
        System.out.println(Arrays.toString(actvs));

/*        Activation[] actvsRandom = ActivationUtilis.generateRandomActivations(6, Duration.ofHours(1), Duration.ofDays(3), startDateRandom, endDateRandom);

        Activation a;
        for (int i = 0; i < actvsRandom.length; i++) {
            for (int j = i + 1; j < actvsRandom.length; j++) {
                if (actvsRandom[j].start.isBefore(actvsRandom[i].start)) {
                    a = actvsRandom[i];
                    actvsRandom[i] = actvsRandom[j];
                    actvsRandom[j] = a;
                }
            }
        }
        System.out.println(Arrays.toString(actvsRandom));
 */
    }
}
