package ug.door.retrofit;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsonArrayResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    JsonArrayResponseBodyConverter() {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JSONArray array;
        try {
            array = new JSONArray(value.string());
            return (T) array;
        } catch(JSONException e) {
            return null;
        }
    }
}
