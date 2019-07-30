package cn.mokier.soullaggremover.Utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static String filterVar(String str, String... values) {
    if(values != null) {
      if(values.length % 2 == 0) {
        for(int i = 0;i < values.length;) {
          str = str.replace(values[i], values[i + 1]);
          i += 2;
          if(i >= values.length) {
            break;
          }
        }
      }
    }
    return str;
  }

  public static List<Text> filterVar(List<String> strs, String... values) {
    List<Text> list = new ArrayList<>();
    for(String str : strs) {
      list.add(TextSerializers.FORMATTING_CODE.deserialize(filterVar(str, values)));
    }
    return list;
  }
}
