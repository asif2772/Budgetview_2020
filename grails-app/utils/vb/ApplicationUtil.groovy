package vb

import java.text.SimpleDateFormat
import java.text.ParseException;
/**
 * Created by SrPolash on 3/23/2015.
 */
class ApplicationUtil {

    /**
     * this function take string date like "23-01-2015"("dd-MM-yyyy") as parameter
     * and return "150123"(yyMMdd)
     */
    def convertDateToDBFormat(def dateData){
        def temp = dateData.split("-")
        def tempDate= temp[0]
        def tempMonth= temp[1]
        def tempYear= temp[2]
        def mainStr=tempYear.substring(2, 4)+tempMonth+tempDate
        return mainStr
    }

    /**
     * this function take string date like "150123"(yyMMdd)  as parameter
     * and return "23-01-2015"("dd-MM-yyyy")
     */
    def convertDateToDisplayFormat(def dateData){
        int nLen = dateData.length();
        if(nLen < 6) return 'Invalid Date';

        String tempYear = dateData.substring(0,2);
        StringBuilder sbYear = new StringBuilder(tempYear);
        sbYear.insert(0, '20');
        tempYear = sbYear.toString();
        println("year : "+tempYear);

        def tempMonth = dateData.substring(2,4);
        def tempDay = dateData.substring(4,6);
        def tempDate = tempDay + "-" + tempMonth + "-" + tempYear;

        return tempDate
    }

    def convertDateFromMonthAndYear(def month, def year){
        String day = "1"
        String formattedDate = year + "-" + month + "-0" + day + " 00:00:00"

        return formattedDate;
    }

    static boolean isNumericString(String text){

        if (text.matches("[0-9]+") && text.length() > 2)
            return true

        return false
    }
}
