import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        try {
            System.out.println("=".repeat(100));
            System.out.println("👶🤰 MOM & BABY CARE TRACKER - COMPREHENSIVE ANALYSIS 👶🤰");
            System.out.println("=".repeat(100));
            System.out.println();

            // Load profiles
            BabyProfile babyProfile = loadBabyProfile();
            MamaProfile mamaProfile = loadMamaProfile();

            // Display profiles
            displayBabyProfile(babyProfile);
            displayMamaProfile(mamaProfile);

            // Load and analyze baby data
            System.out.println("\n" + "=".repeat(100));
            System.out.println("📊 BABY DATA ANALYSIS");
            System.out.println("=".repeat(100));
            analyzeBabyData(babyProfile);

            // Load and analyze mama data
            System.out.println("\n" + "=".repeat(100));
            System.out.println("📊 MAMA DATA ANALYSIS");
            System.out.println("=".repeat(100));
            analyzeMamaData(mamaProfile);

            System.out.println("\n" + "=".repeat(100));
            System.out.println("✅ ANALYSIS COMPLETE! ALL SYSTEMS CHECKED.");
            System.out.println("=".repeat(100));

        } catch (IOException e) {
            System.err.println("❌ Error reading files: " + e.getMessage());
            System.err.println("\nPlease make sure the following files exist:");
            System.err.println("  - BABY_PROFILE.csv");
            System.err.println("  - MUM_PROFILE.CSV");
            System.err.println("  - baby_data.csv");
            System.err.println("  - mama_data_complete.csv");
            e.printStackTrace();
        }
    }


    static class BabyProfile {
        String name;
        LocalDate birthDate;
        double birthWeight;
        double birthHeight;
        double birthHeadCircumference;
        String bloodType;
        String gender;
        String notes;
    }

    private static BabyProfile loadBabyProfile() throws IOException {
        // Try different possible filenames
        Path profilePath = null;
        if (Files.exists(Paths.get("BABY_PROFILE.csv"))) {
            profilePath = Paths.get("BABY_PROFILE.csv");
        } else if (Files.exists(Paths.get("baby_profile.csv"))) {
            profilePath = Paths.get("baby_profile.csv");
        } else {
            throw new IOException("Baby profile file not found!");
        }

        String csvData = Files.readString(profilePath);
        String[] lines = csvData.split("\n");

        BabyProfile profile = new BabyProfile();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",", 2);
            if (parts.length < 2) continue;

            String field = parts[0].trim();
            String value = parts[1].trim();

            switch (field) {
                case "baby_name":
                    profile.name = value;
                    break;
                case "birth_date":
                    profile.birthDate = LocalDate.parse(value, DATE_FORMATTER);
                    break;
                case "birth_weight_kg":
                    profile.birthWeight = Double.parseDouble(value);
                    break;
                case "birth_height_cm":
                    profile.birthHeight = Double.parseDouble(value);
                    break;
                case "birth_head_circumference_cm":
                    profile.birthHeadCircumference = Double.parseDouble(value);
                    break;
                case "blood_type":
                    profile.bloodType = value;
                    break;
                case "gender":
                    profile.gender = value;
                    break;
                case "notes":
                    profile.notes = value;
                    break;
            }
        }

        return profile;
    }

    private static void displayBabyProfile(BabyProfile profile) {
        System.out.println("👶 BABY PROFILE");
        System.out.println("-".repeat(100));
        System.out.println("Name: " + profile.name);
        System.out.println("Birth Date: " + profile.birthDate);

        // Calculate age
        long days = ChronoUnit.DAYS.between(profile.birthDate, LocalDate.now());
        long years = days / 365;
        long months = (days % 365) / 30;
        long remainingDays = (days % 365) % 30;

        if (years > 0) {
            System.out.printf("Age: %d years, %d months, %d days (%d days total)\n",
                    years, months, remainingDays, days);
        } else {
            System.out.printf("Age: %d months, %d days (%d days total)\n",
                    months, remainingDays, days);
        }

        System.out.println("Birth Weight: " + profile.birthWeight + " kg");
        System.out.println("Birth Height: " + profile.birthHeight + " cm");
        System.out.println("Birth Head Circumference: " + profile.birthHeadCircumference + " cm");
        System.out.println("Blood Type: " + profile.bloodType);
        System.out.println("Gender: " + profile.gender);
        System.out.println("Notes: " + profile.notes);
        System.out.println();
    }


    static class MamaProfile {
        String name;
        LocalDate lastMenstrualPeriod;
        LocalDate expectedDueDate;
        double prePregnancyWeight;
        String bloodType;
        double height;
        String doctorName;
        String doctorPhone;
        String hospital;
        String allergies;
        String chronicConditions;
        LocalDate deliveryDate;
        String deliveryType;
        String babyName;
        String notes;
    }

    private static MamaProfile loadMamaProfile() throws IOException {
        // Try different possible filenames
        Path profilePath = null;
        if (Files.exists(Paths.get("MUM_PROFILE.CSV"))) {
            profilePath = Paths.get("MUM_PROFILE.CSV");
        } else if (Files.exists(Paths.get("mama_profile.csv"))) {
            profilePath = Paths.get("mama_profile.csv");
        } else {
            throw new IOException("Mama profile file not found!");
        }

        String csvData = Files.readString(profilePath);
        String[] lines = csvData.split("\n");

        MamaProfile profile = new MamaProfile();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",", 2);
            if (parts.length < 2) continue;

            String field = parts[0].trim();
            String value = parts[1].trim();

            if (value.isEmpty()) continue;

            switch (field) {
                case "mama_name":
                    profile.name = value;
                    break;
                case "last_menstrual_period":
                    profile.lastMenstrualPeriod = LocalDate.parse(value, DATE_FORMATTER);
                    break;
                case "expected_due_date":
                    profile.expectedDueDate = LocalDate.parse(value, DATE_FORMATTER);
                    break;
                case "pre_pregnancy_weight_kg":
                    profile.prePregnancyWeight = Double.parseDouble(value);
                    break;
                case "blood_type":
                    profile.bloodType = value;
                    break;
                case "height_cm":
                    profile.height = Double.parseDouble(value);
                    break;
                case "doctor_name":
                    profile.doctorName = value;
                    break;
                case "doctor_phone":
                    profile.doctorPhone = value;
                    break;
                case "hospital":
                    profile.hospital = value;
                    break;
                case "allergies":
                    profile.allergies = value;
                    break;
                case "chronic_conditions":
                    profile.chronicConditions = value;
                    break;
                case "delivery_date":
                    profile.deliveryDate = LocalDate.parse(value, DATE_FORMATTER);
                    break;
                case "delivery_type":
                    profile.deliveryType = value;
                    break;
                case "baby_name":
                    profile.babyName = value;
                    break;
                case "notes":
                    profile.notes = value;
                    break;
            }
        }

        return profile;
    }

    private static void displayMamaProfile(MamaProfile profile) {
        System.out.println("🤰 MAMA PROFILE");
        System.out.println("-".repeat(100));
        System.out.println("Name: " + profile.name);
        System.out.println("Last Menstrual Period: " + profile.lastMenstrualPeriod);
        System.out.println("Expected Due Date: " + profile.expectedDueDate);

        if (profile.deliveryDate != null) {
            System.out.println("Delivery Date: " + profile.deliveryDate);
            System.out.println("Delivery Type: " + profile.deliveryType);
            long daysPostpartum = ChronoUnit.DAYS.between(profile.deliveryDate, LocalDate.now());
            long weeksPostpartum = daysPostpartum / 7;
            System.out.printf("Postpartum: %d weeks (%d days)\n", weeksPostpartum, daysPostpartum);
        } else {
            // Calculate pregnancy week
            long daysSinceLMP = ChronoUnit.DAYS.between(profile.lastMenstrualPeriod, LocalDate.now());
            long weeks = daysSinceLMP / 7;
            long days = daysSinceLMP % 7;
            int trimester = weeks <= 13 ? 1 : weeks <= 27 ? 2 : 3;
            System.out.printf("Current Pregnancy: Week %d + %d days (Trimester %d)\n",
                    weeks, days, trimester);

            long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), profile.expectedDueDate);
            System.out.println("Days Until Due Date: " + daysUntilDue);
        }

        System.out.println("Pre-Pregnancy Weight: " + profile.prePregnancyWeight + " kg");
        System.out.println("Height: " + profile.height + " cm");
        System.out.println("Blood Type: " + profile.bloodType);
        System.out.println("Doctor: " + profile.doctorName + " (" + profile.doctorPhone + ")");
        System.out.println("Hospital: " + profile.hospital);
        System.out.println("Allergies: " + profile.allergies);
        System.out.println("Baby: " + profile.babyName);
        System.out.println();
    }


    private static void analyzeBabyData(BabyProfile profile) throws IOException {
        Path dataPath = null;
        if (Files.exists(Paths.get("baby_data.csv"))) {
            dataPath = Paths.get("baby_data.csv");
        } else if (Files.exists(Paths.get("BABY_DATA.csv"))) {
            dataPath = Paths.get("BABY_DATA.csv");
        } else {
            System.out.println("⚠️ No baby data file found. Skipping baby analysis.");
            return;
        }

        String csvData = Files.readString(dataPath);
        String[] lines = csvData.split("\n");

        if (lines.length < 2) {
            System.out.println("No baby data found in file.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate weekAgo = today.minusDays(7);

        // Counters
        int totalFeedings = 0, todayFeedings = 0, yesterdayFeedings = 0, weekFeedings = 0;
        int totalSleepMinutes = 0, todaySleepMinutes = 0;
        int totalDiapers = 0, todayDiapers = 0;
        double totalTemperature = 0;
        int temperatureCount = 0;
        double maxTemp = 0, minTemp = 100;
        int milestoneCount = 0, memoryCount = 0;
        int vaccineCount = 0, doctorVisitCount = 0;
        int pumpingCount = 0;
        double totalPumpingML = 0;

        Map<String, Integer> feedingTypes = new HashMap<>();
        Map<String, Integer> diaperTypes = new HashMap<>();
        Map<String, Integer> moodCounts = new HashMap<>();
        Map<String, Integer> sleepQuality = new HashMap<>();
        List<String> milestones = new ArrayList<>();
        List<String> memories = new ArrayList<>();
        List<String> vaccines = new ArrayList<>();
        List<Double> growthWeights = new ArrayList<>();
        List<Double> growthHeights = new ArrayList<>();

        LocalTime lastFeedingTime = null;
        LocalDate lastFeedingDate = null;

        // Parse CSV
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] cols = parseCSVLine(line);
            if (cols.length < 3) continue;

            try {
                String dateStr = cols[0].trim();
                String timeStr = cols.length > 1 ? cols[1].trim() : "";
                String activity = cols[2].trim();

                if (dateStr.isEmpty()) continue;

                LocalDate eventDate = LocalDate.parse(dateStr, DATE_FORMATTER);
                boolean isToday = eventDate.equals(today);
                boolean isYesterday = eventDate.equals(yesterday);
                boolean isThisWeek = !eventDate.isBefore(weekAgo);

                switch (activity) {
                    case "feeding":
                        totalFeedings++;
                        if (isToday) todayFeedings++;
                        if (isYesterday) yesterdayFeedings++;
                        if (isThisWeek) weekFeedings++;

                        if (cols.length > 3 && !cols[3].isEmpty()) {
                            String type = cols[3].trim();
                            feedingTypes.put(type, feedingTypes.getOrDefault(type, 0) + 1);
                        }

                        if (!timeStr.isEmpty()) {
                            lastFeedingDate = eventDate;
                            lastFeedingTime = LocalTime.parse(timeStr, TIME_FORMATTER);
                        }
                        break;

                    case "sleep":
                        if (cols.length > 4 && cols[4].equals("end")) {
                            int duration = cols.length > 5 && !cols[5].isEmpty()
                                    ? Integer.parseInt(cols[5].trim()) : 0;
                            totalSleepMinutes += duration;
                            if (isToday) todaySleepMinutes += duration;

                            if (cols.length > 7 && !cols[7].isEmpty()) {
                                String quality = cols[7].trim();
                                sleepQuality.put(quality, sleepQuality.getOrDefault(quality, 0) + 1);
                            }
                        }
                        break;

                    case "diaper":
                        totalDiapers++;
                        if (isToday) todayDiapers++;

                        if (cols.length > 3 && !cols[3].isEmpty()) {
                            String type = cols[3].trim();
                            diaperTypes.put(type, diaperTypes.getOrDefault(type, 0) + 1);
                        }
                        break;

                    case "temperature":
                        if (cols.length > 5 && !cols[5].isEmpty()) {
                            double temp = Double.parseDouble(cols[5].trim());
                            totalTemperature += temp;
                            temperatureCount++;
                            maxTemp = Math.max(maxTemp, temp);
                            minTemp = Math.min(minTemp, temp);
                        }
                        break;

                    case "mood":
                        if (cols.length > 3 && !cols[3].isEmpty()) {
                            String mood = cols[3].trim();
                            moodCounts.put(mood, moodCounts.getOrDefault(mood, 0) + 1);
                        }
                        break;

                    case "milestone":
                        milestoneCount++;
                        if (cols.length > 19 && !cols[19].isEmpty()) {
                            milestones.add(dateStr + ": " + cols[19].trim());
                        }
                        break;

                    case "memory":
                        memoryCount++;
                        if (cols.length > 20 && !cols[20].isEmpty()) {
                            memories.add(dateStr + ": " + cols[20].trim());
                        }
                        break;

                    case "pumping":
                        pumpingCount++;
                        if (cols.length > 5 && !cols[5].isEmpty()) {
                            String amountStr = cols[5].trim().replace("ml", "");
                            if (!amountStr.isEmpty()) {
                                totalPumpingML += Double.parseDouble(amountStr);
                            }
                        }
                        break;

                    case "vaccine":
                        vaccineCount++;
                        String vaccineName = cols.length > 3 ? cols[3].trim() : "Unknown";
                        vaccines.add(dateStr + ": " + vaccineName);
                        break;

                    case "doctor_visit":
                        doctorVisitCount++;
                        break;

                    case "growth":
                        if (cols.length > 3 && cols[3].equals("weight") && cols.length > 5) {
                            String weightStr = cols[5].trim().replace("kg", "");
                            if (!weightStr.isEmpty()) {
                                growthWeights.add(Double.parseDouble(weightStr));
                            }
                        }
                        if (cols.length > 3 && cols[3].equals("height") && cols.length > 5) {
                            String heightStr = cols[5].trim().replace("cm", "");
                            if (!heightStr.isEmpty()) {
                                growthHeights.add(Double.parseDouble(heightStr));
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                // Skip malformed lines
                continue;
            }
        }

        // Calculate days since birth
        long daysSinceBirth = ChronoUnit.DAYS.between(profile.birthDate, today);

        // Display Results
        System.out.println("\n🍼 FEEDING STATISTICS:");
        System.out.println("  Today: " + todayFeedings + " feedings");
        System.out.println("  Yesterday: " + yesterdayFeedings + " feedings");
        System.out.println("  This Week: " + weekFeedings + " feedings");
        System.out.println("  Total: " + totalFeedings + " feedings");
        System.out.printf("  Average per day: %.1f feedings\n",
                totalFeedings / (double)Math.max(1, daysSinceBirth));

        if (!feedingTypes.isEmpty()) {
            System.out.println("  Feeding Types:");
            feedingTypes.forEach((type, count) ->
                    System.out.println("    " + type + ": " + count + " times"));
        }

        if (lastFeedingDate != null && lastFeedingTime != null) {
            long hoursSinceFeeding = ChronoUnit.HOURS.between(
                    lastFeedingDate.atTime(lastFeedingTime),
                    LocalDate.now().atTime(LocalTime.now()));
            System.out.println("  Last feeding: " + hoursSinceFeeding + " hours ago");

            if (hoursSinceFeeding > 4) {
                System.out.println("  ⚠️ WARNING: It's been over 4 hours since last feeding!");
            }
        }

        System.out.println("\n😴 SLEEP STATISTICS:");
        System.out.printf("  Today: %.1f hours\n", todaySleepMinutes / 60.0);
        System.out.printf("  Average per day: %.1f hours\n",
                totalSleepMinutes / 60.0 / Math.max(1, daysSinceBirth));

        if (!sleepQuality.isEmpty()) {
            System.out.println("  Sleep Quality:");
            sleepQuality.forEach((quality, count) ->
                    System.out.println("    " + quality + ": " + count + " times"));
        }

        // Sleep recommendation based on age
        double avgSleepHours = totalSleepMinutes / 60.0 / Math.max(1, daysSinceBirth);
        long ageMonths = daysSinceBirth / 30;
        double recommendedSleep = ageMonths < 3 ? 16 : ageMonths < 12 ? 14 : 13;
        if (avgSleepHours < recommendedSleep) {
            System.out.printf("  ⚠️ Baby is sleeping less than recommended %.0f hours for age\n",
                    recommendedSleep);
        }

        System.out.println("\n💩 DIAPER STATISTICS:");
        System.out.println("  Today: " + todayDiapers + " diapers");
        System.out.println("  Total: " + totalDiapers + " diapers");
        System.out.printf("  Average per day: %.1f diapers\n",
                totalDiapers / (double)Math.max(1, daysSinceBirth));

        if (!diaperTypes.isEmpty()) {
            System.out.println("  Diaper Types:");
            diaperTypes.forEach((type, count) ->
                    System.out.println("    " + type + ": " + count + " times"));
        }

        if (temperatureCount > 0) {
            double avgTemp = totalTemperature / temperatureCount;
            System.out.println("\n🌡️ TEMPERATURE:");
            System.out.printf("  Average: %.1f°C\n", avgTemp);
            System.out.printf("  Range: %.1f°C - %.1f°C\n", minTemp, maxTemp);

            if (maxTemp >= 38.0) {
                System.out.println("  🔥 ALERT: Fever detected! (>38°C) - Contact doctor!");
            } else if (avgTemp >= 37.5) {
                System.out.println("  ⚠️ WARNING: Elevated temperature - Monitor closely");
            }
        }

        if (!moodCounts.isEmpty()) {
            System.out.println("\n😊 MOOD DISTRIBUTION:");
            moodCounts.forEach((mood, count) -> {
                String emoji = mood.equals("happy") ? "😊" :
                        mood.equals("calm") ? "😌" :
                                mood.equals("fussy") ? "😠" : "😭";
                System.out.println("  " + emoji + " " + mood + ": " + count + " times");
            });

            // Pattern detection
            int total = moodCounts.values().stream().mapToInt(Integer::intValue).sum();
            int fussy = moodCounts.getOrDefault("fussy", 0);
            int crying = moodCounts.getOrDefault("crying", 0);

            if ((fussy + crying) > total * 0.4) {
                System.out.println("  ⚠️ PATTERN: Baby has been fussy/crying frequently");
                System.out.println("     Possible causes: teething, colic, illness");
            }
        }

        if (!growthWeights.isEmpty()) {
            System.out.println("\n📏 GROWTH TRACKING:");
            double currentWeight = growthWeights.get(growthWeights.size() - 1);
            double weightGain = currentWeight - profile.birthWeight;
            System.out.printf("  Current Weight: %.2f kg\n", currentWeight);
            System.out.printf("  Weight Gain: %.2f kg (%.1f%%)\n",
                    weightGain, (weightGain / profile.birthWeight) * 100);

            if (!growthHeights.isEmpty()) {
                double currentHeight = growthHeights.get(growthHeights.size() - 1);
                double heightGain = currentHeight - profile.birthHeight;
                System.out.printf("  Current Height: %.1f cm\n", currentHeight);
                System.out.printf("  Height Gain: %.1f cm (%.1f%%)\n",
                        heightGain, (heightGain / profile.birthHeight) * 100);
            }
        }

        if (pumpingCount > 0) {
            System.out.println("\n🍼 PUMPING STATISTICS:");
            System.out.println("  Total Sessions: " + pumpingCount);
            System.out.printf("  Average Amount: %.0f ml per session\n",
                    totalPumpingML / pumpingCount);
            System.out.printf("  Total Produced: %.0f ml\n", totalPumpingML);
        }

        if (vaccineCount > 0) {
            System.out.println("\n💉 VACCINATION RECORD (" + vaccineCount + " vaccines):");
            vaccines.forEach(v -> System.out.println("  ✅ " + v));
        }

        if (doctorVisitCount > 0) {
            System.out.println("\n👨‍⚕️ DOCTOR VISITS: " + doctorVisitCount + " visits total");
        }

        if (milestoneCount > 0) {
            System.out.println("\n🎯 MILESTONES (" + milestoneCount + " total):");
            milestones.forEach(m -> System.out.println("  ✨ " + m));
        }

        if (memoryCount > 0) {
            System.out.println("\n💝 SPECIAL MEMORIES (" + memoryCount + " total):");
            int displayCount = Math.min(10, memories.size());
            for (int i = 0; i < displayCount; i++) {
                System.out.println("  🎉 " + memories.get(i));
            }
            if (memories.size() > 10) {
                System.out.println("  ... and " + (memories.size() - 10) + " more memories!");
            }
        }
    }

   private static void analyzeMamaData(MamaProfile profile) throws IOException {
        Path dataPath = null;
        if (Files.exists(Paths.get("mama_data_complete.csv"))) {
            dataPath = Paths.get("mama_data_complete.csv");
        } else if (Files.exists(Paths.get("MUM_DATA.csv"))) {
            dataPath = Paths.get("MUM_DATA.csv");
        } else {
            System.out.println("⚠️ No mama data file found. Skipping mama analysis.");
            return;
        }

        String csvData = Files.readString(dataPath);
        String[] lines = csvData.split("\n");

        if (lines.length < 2) {
            System.out.println("No mama data found in file.");
            return;
        }

        LocalDate today = LocalDate.now();

        // Counters
        int weightCount = 0;
        double totalWeightGain = 0, maxWeight = 0, minWeight = 1000;
        int doctorVisits = 0, testCount = 0;
        double totalMoodScore = 0;
        int moodCount = 0;
        int totalSleepMinutes = 0, sleepDays = 0;
        int breastfeedingCount = 0, pumpingCount = 0;
        double totalPumpingAmount = 0;
        int exerciseCount = 0, totalExerciseMinutes = 0;
        int fetalMovementsCount = 0;
        int mamaMilestones = 0;
        int contractionCount = 0;
        double totalWaterLiters = 0;
        int waterDays = 0;
        int postpartumCheckups = 0;
        int kegelSessions = 0;

        Map<String, Integer> symptomCounts = new HashMap<>();
        Map<String, Integer> exerciseTypes = new HashMap<>();
        Map<String, Integer> moodTypes = new HashMap<>();
        List<String> milestones = new ArrayList<>();
        List<String> doctorAppointments = new ArrayList<>();
        List<String> tests = new ArrayList<>();

        // Parse CSV
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] cols = parseCSVLine(line);
            if (cols.length < 3) continue;

            try {
                String activity = cols[2].trim();

                switch (activity) {
                    case "weight":
                    case "pregnancy_week":
                        if (cols.length > 8 && !cols[8].isEmpty()) {
                            double gain = Double.parseDouble(cols[8].trim());
                            totalWeightGain += gain;
                            weightCount++;
                        }
                        if (cols.length > 7 && !cols[7].isEmpty()) {
                            double weight = Double.parseDouble(cols[7].trim());
                            maxWeight = Math.max(maxWeight, weight);
                            if (weight > 0) minWeight = Math.min(minWeight, weight);
                        }
                        break;

                    case "doctor_appointment":
                        doctorVisits++;
                        if (cols.length > 16 && !cols[16].isEmpty()) {
                            doctorAppointments.add(cols[0] + ": " + cols[16].trim());
                        }
                        break;

                    case "test":
                        testCount++;
                        if (cols.length > 22 && !cols[22].isEmpty()) {
                            tests.add(cols[0] + ": " + cols[22].trim());
                        }
                        break;

                    case "mood":
                        if (cols.length > 26 && !cols[26].isEmpty()) {
                            double score = Double.parseDouble(cols[26].trim());
                            totalMoodScore += score;
                            moodCount++;
                        }
                        if (cols.length > 27 && !cols[27].isEmpty()) {
                            String moodType = cols[27].trim();
                            moodTypes.put(moodType, moodTypes.getOrDefault(moodType, 0) + 1);
                        }
                        break;

                    case "mama_sleep":
                        if (cols.length > 70 && !cols[70].isEmpty()) {
                            int minutes = Integer.parseInt(cols[70].trim());
                            totalSleepMinutes += minutes;
                            sleepDays++;
                        }
                        break;

                    case "breastfeeding":
                        breastfeedingCount++;
                        break;

                    case "pumping":
                        pumpingCount++;
                        if (cols.length > 63 && !cols[63].isEmpty()) {
                            String amountStr = cols[63].trim().replace("ml", "");
                            if (!amountStr.isEmpty()) {
                                totalPumpingAmount += Double.parseDouble(amountStr);
                            }
                        }
                        break;

                    case "exercise":
                        exerciseCount++;
                        if (cols.length > 95 && !cols[95].isEmpty()) {
                            int minutes = Integer.parseInt(cols[95].trim());
                            totalExerciseMinutes += minutes;
                        }
                        if (cols.length > 94 && !cols[94].isEmpty()) {
                            String exType = cols[94].trim();
                            exerciseTypes.put(exType, exerciseTypes.getOrDefault(exType, 0) + 1);
                        }
                        break;

                    case "fetal_movements":
                        fetalMovementsCount++;
                        break;

                    case "contractions":
                        contractionCount++;
                        break;

                    case "nutrition":
                        if (cols.length > 92 && !cols[92].isEmpty()) {
                            double liters = Double.parseDouble(cols[92].trim());
                            totalWaterLiters += liters;
                            waterDays++;
                        }
                        break;

                    case "symptom":
                        if (cols.length > 31 && !cols[31].isEmpty()) {
                            String symptom = cols[31].trim();
                            symptomCounts.put(symptom, symptomCounts.getOrDefault(symptom, 0) + 1);
                        }
                        break;

                    case "postpartum_checkup":
                        postpartumCheckups++;
                        break;

                    case "pelvic_floor":
                        kegelSessions++;
                        break;

                    case "mama_milestone":
                        mamaMilestones++;
                        if (cols.length > 142 && !cols[142].isEmpty()) {
                            milestones.add(cols[0] + ": " + cols[142].trim());
                        }
                        break;
                }
            } catch (Exception e) {
                continue;
            }
        }

        // Display Results
        if (weightCount > 0) {
            System.out.println("\n⚖️ WEIGHT TRACKING:");
            System.out.printf("  Average weight gain: %.1f kg\n", totalWeightGain / weightCount);
            System.out.printf("  Weight range: %.1f kg - %.1f kg\n", minWeight, maxWeight);
            System.out.printf("  Total gain: %.1f kg\n", maxWeight - profile.prePregnancyWeight);

            // BMI-based recommendation
            double bmi = profile.prePregnancyWeight / Math.pow(profile.height / 100, 2);
            double recommendedGain = bmi < 18.5 ? 18 : bmi < 25 ? 15 : bmi < 30 ? 11 : 9;
            double currentGain = maxWeight - profile.prePregnancyWeight;

            if (currentGain > recommendedGain + 3) {
                System.out.printf("  ⚠️ Weight gain above recommended (%.0f kg) for BMI\n", recommendedGain);
            } else if (currentGain < recommendedGain - 3) {
                System.out.printf("  ⚠️ Weight gain below recommended (%.0f kg) for BMI\n", recommendedGain);
            }
        }

        System.out.println("\n👨‍⚕️ MEDICAL CARE:");
        System.out.println("  Doctor visits: " + doctorVisits);
        System.out.println("  Tests completed: " + testCount);
        System.out.println("  Postpartum checkups: " + postpartumCheckups);

        if (!doctorAppointments.isEmpty()) {
            System.out.println("  Recent appointments:");
            int show = Math.min(3, doctorAppointments.size());
            for (int i = 0; i < show; i++) {
                System.out.println("    📅 " + doctorAppointments.get(i));
            }
        }

        if (moodCount > 0) {
            double avgMood = totalMoodScore / moodCount;
            System.out.println("\n💭 MENTAL HEALTH:");
            System.out.printf("  Average mood score: %.1f/10\n", avgMood);

            String status;
            String emoji;
            if (avgMood >= 7) {
                status = "Good - doing well!";
                emoji = "😊";
            } else if (avgMood >= 5) {
                status = "Fair - manageable";
                emoji = "😐";
            } else if (avgMood >= 3) {
                status = "Challenging - needs support";
                emoji = "😢";
            } else {
                status = "ALERT - Please seek professional help";
                emoji = "🚨";
            }
            System.out.println("  Overall: " + emoji + " " + status);

            if (avgMood < 5) {
                System.out.println("  ⚠️ WARNING: Low mood detected. Consider:");
                System.out.println("     - Talk to healthcare provider");
                System.out.println("     - Reach out to support network");
                System.out.println("     - Postpartum depression screening");
            }

            if (!moodTypes.isEmpty()) {
                System.out.println("  Mood Distribution:");
                moodTypes.forEach((mood, count) ->
                        System.out.println("    " + mood + ": " + count + " times"));
            }
        }

        if (sleepDays > 0) {
            double avgSleepHours = (totalSleepMinutes / 60.0) / sleepDays;
            System.out.println("\n😴 SLEEP TRACKING:");
            System.out.printf("  Average per night: %.1f hours\n", avgSleepHours);
            System.out.printf("  Total nights tracked: %d\n", sleepDays);

            if (avgSleepHours < 6) {
                double sleepDebt = (7 - avgSleepHours) * sleepDays;
                System.out.printf("  🔴 CRITICAL: Severe sleep deprivation (%.0f hours debt)\n", sleepDebt);
                System.out.println("     This can affect physical and mental health!");
            } else if (avgSleepHours < 7) {
                System.out.println("  ⚠️ Below recommended 7-8 hours - try to rest more");
            } else {
                System.out.println("  ✅ Meeting sleep recommendations!");
            }
        }

        if (breastfeedingCount > 0 || pumpingCount > 0) {
            System.out.println("\n🍼 BREASTFEEDING & PUMPING:");
            System.out.println("  Breastfeeding sessions: " + breastfeedingCount);
            System.out.println("  Pumping sessions: " + pumpingCount);

            if (pumpingCount > 0) {
                System.out.printf("  Average pumping: %.0f ml per session\n",
                        totalPumpingAmount / pumpingCount);
                System.out.printf("  Total milk produced: %.0f ml\n", totalPumpingAmount);

                double avgDaily = totalPumpingAmount / Math.max(1, pumpingCount / 4.0);
                if (avgDaily < 500) {
                    System.out.println("  ⚠️ Consider: milk production may be low");
                }
            }
        }

        if (exerciseCount > 0) {
            System.out.println("\n🚶‍♀️ PHYSICAL ACTIVITY:");
            System.out.println("  Total sessions: " + exerciseCount);
            System.out.println("  Total minutes: " + totalExerciseMinutes);
            System.out.printf("  Average per session: %d minutes\n",
                    totalExerciseMinutes / exerciseCount);
            System.out.printf("  Weekly average: %.0f minutes\n",
                    (totalExerciseMinutes / (double)exerciseCount) * 7);

            if (!exerciseTypes.isEmpty()) {
                System.out.println("  Exercise Types:");
                exerciseTypes.forEach((type, count) ->
                        System.out.println("    " + type + ": " + count + " times"));
            }

            double weeklyMinutes = (totalExerciseMinutes / (double)exerciseCount) * 7;
            if (weeklyMinutes >= 150) {
                System.out.println("  ✅ Meeting WHO recommendation of 150 min/week!");
            } else {
                System.out.printf("  💪 Goal: %.0f more minutes to reach 150/week\n",
                        150 - weeklyMinutes);
            }
        }

        if (waterDays > 0) {
            double avgWater = totalWaterLiters / waterDays;
            System.out.println("\n💧 HYDRATION:");
            System.out.printf("  Average water intake: %.1f liters/day\n", avgWater);

            if (avgWater < 2.0) {
                System.out.println("  ⚠️ Below recommended 2-3 liters during pregnancy/breastfeeding");
            } else if (avgWater >= 2.5) {
                System.out.println("  ✅ Excellent hydration!");
            }
        }

        if (fetalMovementsCount > 0) {
            System.out.println("\n👶 FETAL MOVEMENTS:");
            System.out.println("  Tracking sessions: " + fetalMovementsCount);
            System.out.println("  ✅ Monitoring baby's activity regularly");
        }

        if (contractionCount > 0) {
            System.out.println("\n🤰 CONTRACTIONS:");
            System.out.println("  Total recorded: " + contractionCount);
            if (contractionCount > 10) {
                System.out.println("  📊 Good tracking - helpful for labor preparation");
            }
        }

        if (kegelSessions > 0) {
            System.out.println("\n🧘‍♀️ PELVIC FLOOR EXERCISES:");
            System.out.println("  Kegel sessions: " + kegelSessions);
            System.out.println("  ✅ Great work on pelvic floor recovery!");
        }

        if (!symptomCounts.isEmpty()) {
            System.out.println("\n⚠️ SYMPTOMS TRACKER:");
            symptomCounts.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .forEach(entry ->
                            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " times"));

            // Pattern detection
            if (symptomCounts.getOrDefault("nausea", 0) > 10) {
                System.out.println("  💡 TIP: Frequent nausea - try ginger tea, small meals");
            }
            if (symptomCounts.getOrDefault("back_pain", 0) > 15) {
                System.out.println("  💡 TIP: Frequent back pain - consider prenatal yoga, massage");
            }
        }

        if (mamaMilestones > 0) {
            System.out.println("\n🎉 MAMA MILESTONES (" + mamaMilestones + " achievements):");
            milestones.forEach(m -> System.out.println("  ✨ " + m));
            System.out.println("\n  💪 You're doing an amazing job, mama!");
        }
    }


    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }
}