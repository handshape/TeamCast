package com.handshape.teamcast.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author jturner
 */
public class WebhookMessageDispatcher {

    private static final String THEME_COLOR = "0076D7";

    private static final String DEFAULT_PFP_URL = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAGKklEQVRYw62XfWyVVx3HP+d5uc9z33r7TqGF3pYyaMkwLSAlkzQ6ap3dWxZcnEsWo2m0rQtqMMaYLDESXbLoYnRm6EjkD8n4y2UuGkWyZIOwwAayYaVdn74w6KB4297by317Xo5/lN720lvCZZzkJs9zfr9zvt/znO/v5cKykYkntk8ff3PI6u88YPV3lnGfhtXfaVr9nd/59Njh9zJziX3LbUqBp6L+Mryzq1WpbH4JGLP6O5+7D+C9wH+FUfZqWVfvLqHrB4sSyMzOtQlV61Z8JpEnBgGqgCNWf+cRq3+3cY/gvwDeAqKhRwbRyipAKJsz8cRjK7+A5suf1tzyOcTa9sXX50AeG/3uLqVE8FeAnwAQWou/Y8+SUdXyWCL/BZKpD4WiPggQm5klM3Qe+ZefLd/zRVTtKJ67UwlVqPr6pmK4NnA2O/TBQ8ChPEj3fvTtXdRUVyKEACnjRtAszxOYeedvWnDHwzbAjTf+hL2jGxB4h38Micu33OTCJwtXoDc0gRCrnl7aOdzYDdyZa6D6UAb+CD4D7exxqvY+hRoM4aXmt/qrq4cUAH/rji15tqFy5L9eB1VFdPQWgAvNh14fvSM4gNB9aHX16NEtiG2Pg+FHvv82Xi6LGgwtCr5ySQOargDcvPg+ti8A0a3gujA1mgcHUGvrQbl7KSiBIHpIA8+FqjrcNY3Mnzu1YPQcLU8gO3x2GECvWYfIphAtDyIvnESOHC/YUA1HSo4EEfsYdeIConkrpJL4ausXhThdIMJsMnUSRX0oNjNLNhHHe3UQ7OTSaYIR9MaWe0sGqg/7SwOoZoA1tdUgvetG0F9XEIbSsX8PYBgGcuw/BeAAwgzcezZycyjXLExzIZ1Iz3t5RR4wyyNH8dw3A34T8ek4AI4niWe9BQI+s2TcuVQWx11Yr8xOEQwEQHpn3Jmp3xRNxdLOPqMg/6plkzgevHg+yTfeTXAxlitJfAAffRJj32snOfjWeRzPQ/cyaKp4z5n732PBxk2ZogTMioqUEQo8bk98MDSRsDk96wJwzMog1NIIHD0zBsDJyTnGpxO4sclLmfGPuoL166dXLUbWjx4V1sDuA/LmdDTjLIXfhXkPJRAqicC5qfn8c9p2kNnkhvm3j3xv6neDoigBa2B3kJuxE0j5EhAoN5e49bbWlnwFva01+eeKoAkQAH7lSf555beDeUWrAOMHug2Zy/4D6Fo0lPkUalSJq/n4dlcbIVMviUBjVZgrsQTPdKxne7R2uakZ+MIPv/r5P//672c9cev0B5Hyp8U20uoaUSurC+Y8KRmPpbmW1tBUlbUhQUNYQRElcfx5w/OvvCDGnt+zVjr2GFA0znwbtyKMJVPO8Th0+ipW2kdlRWV+fmNI8PRmDf3ubyoNNCnSdfpWA0cIhOEHoeR/l6ZTnJtOYxiFPYqVlIzNyQLfZYm22PADfRrQswJ3017Etj3oDc3UbNhQeLejo3D6+0gpV+zYsG8/NU3RwiSYmse+/gmZkTPYVz68fcmXNSDf+lC5CeUrfbAueiv7rRReS0sLPY/0cOqdU4SCS6G5s3Mn0dvAAdRAGLWpDbOpjdzUOPPvvo6XvLFoblf372g4CCDWdaB87QBULilW13X8/pW3097ejlAF1oiFpmn09Pbw5FNPoqrqnWtSuAJzYwe5q2PIdBzAJ6yB3TaRqKY8+wL4gwULDJ+PqqqKVTe8emUKgPqGdSXJ30vfZPaNl/FSM1kF+LfS07cCfDHcVm27pCQ+lyA+Fy+qhzs2Kv4goT1fB7igKLuePcH64nXecZyi83bOZnxsglA4SCgcYnxsglzOLomE0dCC0frFE2JidOIBKZTh1Rxra6vRlt3tpaFhbNthTV0NtWsW9DJ9fZrr126g6xpb2jbfNQnpOpsFwMTo5GEpxLeKOUXKwgSDn6EZWVUI7mtGKNCn3KKyH+THxfxS6cz9B5feiHSdH+SrYXRTc1LAXpAjK+7btslms/cTfFi6TrcZKUsWlOPoxsbLQsoOgfxDQS8OzMXn8TzvsyJLPPcQnrfdLAtfXvHXbPmYtCZbPPgmiIdBbgMR0FSVSCS8WAPkXZ42CeIiyBPSzh4xy8tHb3f5P2lNOntxBHkAAAAAAElFTkSuQmCC";

    public void dispatchToAll(Set<String> urls, String title, String subtitle, String imageUrl, String message) throws IOException {
        for (String webhook : urls) {
            String toJson;
            if (webhook.matches("^https://hooks.slack.com/services/.*") || webhook.matches("^https://discordapp.com/api/webhooks/.*/slack$")) {
                toJson = createSlackJsonBody(title, subtitle, imageUrl, message);
            } else if (webhook.matches("^https://discordapp.com/api/webhooks/.*")) {
                toJson = createDiscordJsonBody(title, subtitle, imageUrl, message);
            } else {
                toJson = createTeamsJsonBody(title, subtitle, imageUrl, message);
            }
            System.out.println(toJson);
            try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpPost request = new HttpPost(webhook);
                StringEntity params = new StringEntity(toJson);
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                CloseableHttpResponse response = httpClient.execute(request);
                System.out.println(response.getStatusLine().toString());
            } 
        }
    }

    public String createTeamsJsonBody(String title, String subtitle, String imageUrl, String message) {
        Gson gson = new GsonBuilder().create();
        JsonObject root = new JsonObject();
        root.addProperty("@type", "MessageCard");
        root.addProperty("@context", "http://schema.org/extensions");
        root.addProperty("themeColor", THEME_COLOR);
        root.addProperty("summary", title);
        JsonArray sections = new JsonArray();
        root.add("sections", sections);
        JsonObject activity = new JsonObject();
        sections.add(activity);
        activity.addProperty("activityTitle", title);
        if (subtitle != null && !subtitle.trim().isEmpty()) {
            activity.addProperty("activitySubtitle", subtitle);
        }
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            activity.addProperty("activityImage", imageUrl);
        }
        activity.addProperty("activityText", message);
        String toJson = gson.toJson(root);
        return toJson;
    }

    public String createDiscordJsonBody(String title, String subtitle, String imageUrl, String message) {
        Gson gson = new GsonBuilder().create();
        JsonObject root = new JsonObject();
        root.addProperty("username", title);
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            root.addProperty("avatar_url", imageUrl);
        }
        root.addProperty("content", subtitle);
        JsonArray embeds = new JsonArray();
        root.add("embeds", embeds);
        JsonObject embed = new JsonObject();
        embeds.add(embed);
        embed.addProperty("description", message);
        String toJson = gson.toJson(root);
        return toJson;
    }

    public String createSlackJsonBody(String title, String subtitle, String imageUrl, String message) {
        Gson gson = new GsonBuilder().create();
        JsonObject root = new JsonObject();
        JsonArray blocks = new JsonArray();
        root.add("blocks", blocks);
        JsonObject header = new JsonObject();
        blocks.add(header);
        header.addProperty("type", "header");
        JsonObject headerText = new JsonObject();
        header.add("text", headerText);
        headerText.addProperty("type", "plain_text");
        headerText.addProperty("text", title);
        headerText.addProperty("emoji", true);

        JsonObject section = new JsonObject();
        blocks.add(section);
        section.addProperty("type", "section");
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            JsonObject accessory = new JsonObject();
            section.add("accessory", accessory);
            accessory.addProperty("type", "image");
            accessory.addProperty("image_url", imageUrl);
            accessory.addProperty("alt_text", "icon");
        }
        JsonObject text = new JsonObject();
        section.add("text", text);
        text.addProperty("type", "mrkdwn");
        text.addProperty("text", "*" + subtitle + "*\n" + message);
        String toJson = gson.toJson(root);
        return toJson;
    }
}
