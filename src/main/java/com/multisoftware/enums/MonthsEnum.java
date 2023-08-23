package com.multisoftware.enums;

public enum MonthsEnum {
    JAN,
    FEB,
    MAR,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    public static MonthsEnum get(int code) {
        switch (code) {
            case 1:
                return JAN;
            case 2:
                return FEB;
            case 3:
                return MAR;
            case 4:
                return APRIL;
            case 5:
                return MAY;
            case 6:
                return JUNE;
            case 7:
                return JULY;
            case 8:
                return AUGUST;
            case 9:
                return SEPTEMBER;
            case 10:
                return OCTOBER;
            case 11:
                return NOVEMBER;
            case 12:
                return DECEMBER;
            default: return JAN;
        }
    }

    public static int get(MonthsEnum month) {
        switch (month) {
            case JAN:
                return 1;
            case FEB:
                return 2;
            case MAR:
                return 3;
            case APRIL:
                return 4;
            case MAY:
                return 5;
            case JUNE:
                return 6;
            case JULY:
                return 7;
            case AUGUST:
                return 8;
            case SEPTEMBER:
                return 9;
            case OCTOBER:
                return 10;
            case NOVEMBER:
                return 11;
            case DECEMBER:
                return 12;
            default: return 12;
        }
    }

    public static String getText(MonthsEnum month) {
        switch (month) {
            case JAN:
                return "Styczeń";
            case FEB:
                return "Luty";
            case MAR:
                return "Marzec";
            case APRIL:
                return "Kwiecień";
            case MAY:
                return "Maj";
            case JUNE:
                return "Czerwiec";
            case JULY:
                return "Lipiec";
            case AUGUST:
                return "Sierpień";
            case SEPTEMBER:
                return "Wrzesień";
            case OCTOBER:
                return "Pańdziernik";
            case NOVEMBER:
                return "Listopad";
            case DECEMBER:
                return "Grudzień";
            default: return "Styczeń";
        }
    }

    @Override
    public String toString() {
        return this.getText(this);
    }
}
