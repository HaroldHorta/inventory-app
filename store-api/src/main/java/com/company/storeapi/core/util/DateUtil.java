package com.company.storeapi.core.util;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.service.DataCorruptedServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * The type Date util.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    /**
     * The constant YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP.
     */
    private static final String YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP = "dd-M-yyyy hh:mm:ss";

    /**
     * To xml gregorian calendar xml gregorian calendar.
     *
     * @param dateSource the date source
     * @return the xml gregorian calendar
     * @throws DataCorruptedServiceException the data corrupted service exception
     */
    public static final XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime dateSource) throws DataCorruptedServiceException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateSource.format(DateTimeFormatter.ISO_DATE_TIME));
        } catch (DatatypeConfigurationException e) {
            throw new DataCorruptedServiceException(LogRefServices.ERROR_CONVERSION_DATE, "No se ha podido realizar la conversión de la fecha", e);
        }
    }

    /**
     * To date base format or default format date.
     *
     * @param date the date
     * @return the date
     */
    public static final String toDateStringBaseFormatOrDefaultFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            String actualDateString = dateFormat.format(new Date());
            return dateFormat.format(actualDateString);
        }
    }


    /**
     * To date base format or default format date.
     *
     * @param date the date
     * @return the date
     */
    public static final Date toDateBaseFormatOrDefaultFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP);
        try {

            String dateString = dateFormat.format(date);
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP).parse(dateString);

        } catch (Exception e) {
            String actualDateString = dateFormat.format(new Date());
            try {
                return dateFormat.parse(actualDateString);
            } catch (ParseException ex) {
                log.error("Final Date Conversion exception.");
                return null;

            }
        }
    }

    /**
     * To date base format or default format date.
     *
     * @param date the date
     * @return the date
     */
    public static final Date toDateBaseFormatOrDefaultFormat(String date) {
        try {
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP).parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * To date base format date.
     *
     * @param dateToConvert the date to convert
     * @return the date
     */
    public static final Date toDateBaseFormat(String dateToConvert) {
        try{
            DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                    .appendPattern(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP)
                    .toFormatter();
            LocalDateTime localDateTime = Timestamp.valueOf(dateToConvert).toLocalDateTime();
            String dateFormatted = localDateTime.atOffset(ZoneOffset.UTC).format(dateTimeFormatter);
            return parseToDate(dateFormatted);
        }catch (Exception e){
            log.error("No se pudo realizar la transformación de la fecha: {}", dateToConvert);
            return null;
        }
    }


    /**
     * Date parse to string string.
     *
     * @param parse the parse
     * @return the string
     */
    public static String dateParseToString(Date parse) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP);
            return sdf.format(parse);
        }catch (Exception e){
            log.error("No se puede aplicar el formato de fecha estandar: {}",parse);
            return null;
        }

    }
    private static Date parseToDate(String dateFormatted) throws ParseException {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_STANDARD_FORMAT_NP).parse(dateFormatted);
    }


    /**
     * From local dateto date date.
     *
     * @param localDate the local date
     * @return the date
     */
    public static Date fromLocalDatetoDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

    }

    public @NotNull
    static String getDateActual() {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String date = sdf.format(now);
        return date;

    }

    public static String getCode() {
        int number = (int) (Math.random() * 10000000     + 1);
        return String.valueOf(number);
    }

}
