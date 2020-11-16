/*
 * NowPlayingJav.java
 * (c) 2020--11--16, Sponring Maximilian
 */
package Spotify.NowPlaying;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;


public class NowPlayingJava {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        File nowPlayingtxt = new File("NowPlaying.txt");
        while (true) {
            URL url = new URL("https://api.spotify.com/v1/me/player/currently-playing");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Map<String, String> parameters = new HashMap<>();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer BQBDNUUl0oFRpHiJ8CkA_oZ8uzrjSekeXeNV34vDPjfrEwLHaussLoDEigR_wZoZbhikToOV9EegBXIZVGWP1DK_T5UL0ITbY3_4ZuDkoMrgq6xpm-e7QXu_dfCFUNROP8pbdqciiqPPY7CMFcZOLTG80ogVHVyVJ3KL_ebgURaL-d_nBMISNX5QmSa7VyTPStR5-ld2p4vo29irkrspd0SR14gUqNMljZQb_U2QQA_lI9rsMLJH10PiCk7ImOuLl3u11bQQ2POqzJEyXdw");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String contentString = content.toString();
            JSONObject json = new JSONObject(contentString);
            JSONObject item = json.getJSONObject("item");
            String title = item.getString("name");
            JSONArray artistsJson = item.getJSONArray("artists");
            StringBuilder artists = new StringBuilder().append(artistsJson.getJSONObject(0).getString("name"));
            for (int i = 1; i < artistsJson.length(); i++) {
                JSONObject artistJson = artistsJson.getJSONObject(i);
                String artist = artistJson.getString("name");
                artists.append(", ").append(artist);
            }
            String out = title + " - " + artists;
            Scanner reader = new Scanner(nowPlayingtxt);
            String curr = reader.nextLine();
            if (!curr.equals(out)) {
                FileWriter writer = new FileWriter(nowPlayingtxt.getName(), false);
                writer.write(out);
                writer.close();
                System.out.println("Song updated!");
            }
            con.disconnect();
            sleep(5000);
        }
    }
}