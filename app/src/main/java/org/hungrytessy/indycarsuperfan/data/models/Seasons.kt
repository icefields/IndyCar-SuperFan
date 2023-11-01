package org.hungrytessy.indycarsuperfan.data.models

import android.util.Log
import androidx.recyclerview.widget.SortedList
import com.google.gson.annotations.SerializedName
import java.util.TreeSet

data class Seasons(
    @SerializedName("stages")
    val stages: TreeSet<Season>
)

/*
class Seasons {
    final SortedList<Season> stages;

    const Seasons({
        required this.stages,
    });

    factory Seasons.fromJson(Map<String, dynamic> json) {
        List<Season> stages = json['stages'].map<Season>((json2) => Season.fromJson(json2)).toList();
        SortedList<Season> sortedList = SortedList();
        sortedList.addAll(stages);
        return Seasons(
            stages: sortedList
        );
    }
}*/