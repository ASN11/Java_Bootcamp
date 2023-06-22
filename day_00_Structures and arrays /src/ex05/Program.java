import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Program {
    private static final String[] student = new String[10];
    private static int countStudent = 0;
    private static final String[] date = new String[7];
    private static int countDate = 0;
    private static final String[] attendance = new String[310];
    private static final String[][] attendanceAnalyze = new String[310][4];
    private static int countAttendance = 0;
    private static final String[] timeOfLesson = new String[31];
    private static final String[] dayOfMonth = new String[31];
    private static int countDayOfMonth = 0;
    private static final Calendar cal = new GregorianCalendar(2020, Calendar.SEPTEMBER, 1);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        countStudent = DataRecording(in, student);
        countDate = DataRecording(in, date);
        countAttendance = DataRecording(in, attendance);
        in.close();

        TableInit();
        AttendanceAnalyze();
        PersonAdd();
    }

    /**
     * парсинг статистики посещаемости
     */
    private static void AttendanceAnalyze() {
        for (int i = 0; i < countAttendance; i++) {
            attendanceAnalyze[i] = attendance[i].split("\\s");
        }
    }


    private static int DataRecording(Scanner in, String[] data) {
        String input;
        int count = 0;
        while (!(input = in.nextLine()).equals(".")) {
            data[count] = input;
            count++;
        }
        return count;
    }

    /**
     * печать самих колонок таблицы
     */
    private static void PersonAdd() {
        for (int i = 0; i < countStudent; i++) {
            System.out.printf("%-10s", student[i]);
            for (int j = 0; j < countDayOfMonth; j++) {
                boolean flag = true;
                for (int k = 0; k < countAttendance; k++) {
                    if (student[i].equals(attendanceAnalyze[k][0]) && timeOfLesson[j].equals(attendanceAnalyze[k][1]) &&
                            dayOfMonth[j].equals(attendanceAnalyze[k][2]) && attendanceAnalyze[k][3].equals("HERE")) {
                        System.out.printf("%10s|", "1");
                        flag = false;
                        break;
                    }
                    if (student[i].equals(attendanceAnalyze[k][0]) && timeOfLesson[j].equals(attendanceAnalyze[k][1]) &&
                            dayOfMonth[j].equals(attendanceAnalyze[k][2]) && attendanceAnalyze[k][3].equals("NOT_HERE")) {
                        System.out.printf("%10s|", "-1");
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    System.out.printf("%10s|", "");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Производится печать шапки таблицы, а также заполняются поля dayOfMonth, timeOfLesson для дальнейшей обработки
     */
    private static void TableInit() {
        DayIndex();
        System.out.printf("%10s", "");
        while (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
            for (int i = 0; i < countDate; i++) {
                if ((int)cal.get(Calendar.DAY_OF_WEEK) == Character.getNumericValue(date[i].charAt(0))) {  // если совпадает день недели
                    String day = date[i].substring(3);
                    System.out.print(date[i].charAt(1) + ":00 " + day);
                    System.out.printf("%3d|", cal.get(Calendar.DAY_OF_MONTH));

                    dayOfMonth[countDayOfMonth] = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                    timeOfLesson[countDayOfMonth] = String.valueOf(date[i].charAt(1));
                    countDayOfMonth++;
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.print("\n");
    }

    /**
     * обработка поля date для дальнейшего сопоставления с календарём
     */
    private static void DayIndex() {
        for (int i = 0; i < countDate; i++) {
            String numString = date[i].substring(2);
            switch (numString) {
                case "SU":
                    date[i] = "1" + date[i];
                    break;
                case "MO":
                    date[i] = "2" + date[i];
                    break;
                case "TU":
                    date[i] = "3" + date[i];
                    break;
                case "WE":
                    date[i] = "4" + date[i];
                    break;
                case "TH":
                    date[i] = "5" + date[i];
                    break;
                case "FR":
                    date[i] = "6" + date[i];
                    break;
                case "SA":
                    date[i] = "7" + date[i];
                    break;
            }
        }
    }
}

/*
       September 2020
    Su Mo Tu We Th Fr Sa
           1  2  3  4  5
     6  7  8  9 10 11 12
    13 14 15 16 17 18 19
    20 21 22 23 24 25 26
    27 28 29 30
*/

/*
Mike
Andrey
.
2 MO
4 WE
.
Mike 2 28 NOT_HERE
Andrey 4 9 HERE
Mike 4 9 HERE
.
 */

