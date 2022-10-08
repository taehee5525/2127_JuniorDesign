import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiCallMaker {

    /**
     * method to make get Request.
     */
    public void callGetAPI(String Url, Map<String, String> headerMap) {
		try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
            for (String key : headerMap.keySet()) {
                connection.setRequestProperty(key, headerMap.get(key));
            }
			connection.setDoOutput(true);

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder res = new StringBuilder();
			String line = null;

			while((line = br.readLine()) != null) {
				res.append(line);
			}
			System.out.println(res); //test
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}