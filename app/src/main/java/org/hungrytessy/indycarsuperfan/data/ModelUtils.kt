package org.hungrytessy.indycarsuperfan.data

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//fun stringToLocalDateTime(scheduled: String): LocalDateTime {
//    // 2022-02-25T19:05:00+00:00
//    // YYYY-MM-DDThh:mm:ssTZD (eg
//    // 1997-07-16T19:20:30+01:00)
//    //val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssO")//("YYYY-MM-DD'T'hh:mm:ss'T'ZD")
//    return LocalDateTime.parse(scheduled, DateTimeFormatter.ISO_ZONED_DATE_TIME)
//}


/*

class ModelsUtils {

  static SortedList<Stage> getStages(Map<String, dynamic> json) {
    List<Stage> eventStage = [];
    if (json['stages'] != null) {
      eventStage = json['stages'].map<Stage>((json2) => Stage.fromJson(json2)).toList();
    }
    SortedList<Stage> sortedList = SortedList();
    sortedList.addAll(eventStage);
    return sortedList;
  }

  static Venue? getVenue(Map<String, dynamic> json) {
    Venue? venue;
    if (json['venue'] != null) {
      venue = Venue.fromJson(json['venue']);
    }
    return venue;
  }

  static DateTime? parseScheduled(Map<String, dynamic> json) {
    DateTime? parsedScheduled;
    if (json['scheduled'] != null) {
      parsedScheduled = DateTime.parse(json['scheduled']);
    }
    return parsedScheduled;
  }

  static DateTime? parseScheduledEnd(Map<String, dynamic> json) {
    DateTime? parsedScheduledEnd;
    if (json['scheduledEnd'] != null) {
      parsedScheduledEnd = DateTime.parse(json['scheduledEnd']);
    }
    return parsedScheduledEnd;
  }

  static int compareTo(DateTime? ownScheduled, DateTime? otherScheduled) {
    if (ownScheduled == null) return 1;
    if (otherScheduled == null) return -1;

    if (ownScheduled.isBefore(otherScheduled) == true) {
      return 1;
    } else if (ownScheduled.isAfter(otherScheduled) == true) {
      return -1;
    } else {
      return 0;
    }
  }
}

 */