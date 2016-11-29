package prometheus.zero.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtil {
    public static String parseErrorArray(String json) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator keys = jsonObject.keys();

            while (keys.hasNext()) {
                String currentKey = (String) keys.next();
                JSONArray jsonArray = jsonObject.getJSONArray(currentKey);
                ArrayList<String> responseArray = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String response = jsonArray.getString(i);
                    responseArray.add(response);
                }
                String sep = "\n";
                for (int i = 0; i < responseArray.size(); i++) {
                    sb.append(responseArray.get(i)).append(sep);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Something went wrong, please try again later";
        }
        return sb.toString();
    }

    public static String parseErrorObject(String json) {
        StringBuilder sb = new StringBuilder();
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                Iterator keys = jsonObject.keys();
                List<String> responseArray = new ArrayList<>();

                while (keys.hasNext()) {
                    String currentKey = (String) keys.next();

                    String response = jsonObject.getString(currentKey);
                    responseArray.add(response);

                    String sep = "\n";
                    for (int i = 0; i < responseArray.size(); i++) {
                        if (i < responseArray.size() - 1) {
                            sb.append(responseArray.get(i)).append(sep);
                        } else {
                            sb.append(responseArray.get(i));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "Something went wrong, please try again later";
            }
        } else {
            return "Something went wrong, please try again later";
        }

        return sb.toString();
    }
}
