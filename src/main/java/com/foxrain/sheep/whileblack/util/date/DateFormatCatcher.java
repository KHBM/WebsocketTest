package com.foxrain.sheep.whileblack.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 사용자의 입력을 받은 날짜 문자열을 지정된 포맷에 해당하는 양식으로 파싱하는 기능을 제공한다.
 *
 * Created with intellij IDEA. by 2020 04 2020/04/19 12:56 오후 19 User we at 12 56 To change this
 * template use File | Settings | File Templates.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DateFormatCatcher {

  private static Map<String, DateFormat> formatMap;

  static {
    formatMap = new HashMap<>();
    formatMap.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));
  }

  private static DateFormat getFormat(String format) {
    return
        !formatMap.containsKey(format) ? ((Supplier<DateFormat>) () -> {
          final SimpleDateFormat cache = new SimpleDateFormat(format);
          formatMap.put(format, cache);
          return cache;
        }).get() : formatMap.get(format);
  }

  public static boolean isFormatParsible(String dateString, String formatString) {
    try {
      DateFormat format = getFormat(formatString);
      LocalDateTime.ofInstant(format.parse(dateString).toInstant(),
                              ZoneId.systemDefault());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 주어진 문자열 데이터를 포맷 양식으로 변환 시도한다.
   * @param dateString
   * @param formatString
   * @return 포맷 변환 실패 시, 현재 날짜에 해당하는 데이터를 가져온다.
   */
  public static LocalDateTime getLocalDateTime(String dateString, String formatString) {
    DateFormat format = getFormat(formatString);
    try {
      return LocalDateTime.ofInstant(format.parse(dateString).toInstant(),
                                              ZoneId.systemDefault());
    } catch (ParseException e) {
      log.error("Parameter date can't be parsed.. check valid format {} : {}", formatString, e.getMessage());
      return LocalDateTime.now();
    }
  }

  public static LocalDate getLocalDate(String dateString, String formatString) {
    return getLocalDateTime(dateString, formatString).toLocalDate();
  }

  public static Date getDate(String dateString, String formatString) {
    return Date.from(getLocalDateTime(dateString, formatString).atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Return free-format date string.
   * @param date
   * @param formatString
   * @return
   */
  public static String getDateStringInFormat(Date date, String formatString) {
    DateFormat format = getFormat(formatString);
    return format.format(date);
  }

  public static String getDataStringInyyyyMMdd(Date date) {
    return getDateStringInFormat(date, "yyyy-MM-dd");
  }

  public static String getDataStringInyyyyMMdd(LocalDateTime date) {
    final Date targetDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    return getDateStringInFormat(targetDate, "yyyy-MM-dd");
  }

}
