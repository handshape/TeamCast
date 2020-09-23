package com.handshape.teamcast.ui;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jturner
 */
public class WebhookMessageDispatcherTest {

    public WebhookMessageDispatcherTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testIntegration() {
        WebhookMessageDispatcher inst = new WebhookMessageDispatcher();
        String title = "This is the title.";
        String subtitle = "This is the subtitle.";
        String imageUrl = "http://nowhere.com/icon.png";
        String message = "This is a message.";
        JsonElement discord = JsonParser.parseString(inst.createDiscordJsonBody(title, subtitle, imageUrl, message));
        assertTrue(discord.isJsonObject(), "Discord body must be an object.");
        JsonElement teams = JsonParser.parseString(inst.createTeamsJsonBody(title, subtitle, imageUrl, message));
        assertTrue(teams.isJsonObject(), "Teams body must be an object.");
        JsonElement slack = JsonParser.parseString(inst.createSlackJsonBody(title, subtitle, imageUrl, message));
        assertTrue(slack.isJsonObject(), "Slack body must be an object.");
    }
}
