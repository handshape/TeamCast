package com.handshape.teamcast.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 *
 * @author jturner
 */
public class TeamCastFrame extends javax.swing.JFrame {

    private static final String THEME_COLOR = "0076D7";

    private static final String DEFAULT_PFP_URL = "data:image/png;base64,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";

    private ResourceBundle i18n;

    private final JTextArea webhookArea = new JTextArea(4, 40);
    private DocumentListener docListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            fireChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fireChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            fireChange();
        }

        public void fireChange() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    rerenderPreview();
                }
            });
        }
    };

    /**
     * Creates new form TeamCastFrame
     */
    public TeamCastFrame() {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/shout.png")));
        } catch (IOException ex) {
            Logger.getLogger(TeamCastFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        i18n = ResourceBundle.getBundle("i18n", Locale.getDefault());
        initComponents();
        titleField.getDocument().addDocumentListener(docListener);
        subtitleField.getDocument().addDocumentListener(docListener);
        iconField.getDocument().addDocumentListener(docListener);
        bodyArea.getDocument().addDocumentListener(docListener);
        previewPane.setContentType("text/html");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        iconField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bodyArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        subtitleField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        previewPane = new javax.swing.JEditorPane();
        appToolBar = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TeamCast");

        jSplitPane1.setDividerSize(16);

        jLabel1.setText(i18n.getString("label.title")); // NOI18N

        jLabel2.setText(i18n.getString("label.iconUrl")); // NOI18N

        jLabel3.setText(i18n.getString("label.postBody")); // NOI18N

        bodyArea.setColumns(20);
        bodyArea.setLineWrap(true);
        bodyArea.setRows(5);
        bodyArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(bodyArea);

        jLabel4.setText(i18n.getString("label.subtitle")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(subtitleField)
                    .addComponent(iconField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subtitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iconField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        previewPane.setEditable(false);
        jScrollPane2.setViewportView(previewPane);

        jSplitPane1.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        appToolBar.setFloatable(false);
        appToolBar.setRollover(true);
        getContentPane().add(appToolBar, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(statusLabel, java.awt.BorderLayout.CENTER);

        jButton1.setText(i18n.getString("action.post")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (JOptionPane.showConfirmDialog(getRootPane(), new JScrollPane(webhookArea), i18n.getString("label.webhooks"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                validateWebhooks(webhookArea.getText());
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar appToolBar;
    private javax.swing.JTextArea bodyArea;
    private javax.swing.JTextField iconField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JEditorPane previewPane;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField subtitleField;
    private javax.swing.JTextField titleField;
    // End of variables declaration//GEN-END:variables

    private void rerenderPreview() {
        boolean hasImage = false;
        URL imageUrl = null;
        if (!iconField.getText().trim().isEmpty()) {
            try {
                imageUrl = new URL(iconField.getText().trim());
            } catch (Exception e) {
                // Swallow this.
            }
            hasImage = true;
        }
        StringBuilder output = new StringBuilder();
        if (hasImage) {
            output.append("<table border=\"0\"><tr><td><image width=\"64\" height=\"64\" src=\"").append(imageUrl.toExternalForm()).append("\"/></td><td>");
        }
        output.append("<font size=\"+1\"><b>" + StringEscapeUtils.escapeHtml3(titleField.getText()) + "</b></font><br/>");
        if (!subtitleField.getText().trim().isEmpty()) {
            output.append(mdToHtml(subtitleField.getText()));
        }
        if (hasImage) {
            output.append("</td></tr></table>");
        }
        output.append(mdToHtml(bodyArea.getText()));

        previewPane.setText(output.toString());
    }

    private String mdToHtml(String in) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(in);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);

    }

    private void validateWebhooks(String text) throws MalformedURLException {
        Set<String> urls = new TreeSet<>();
        String[] lines = text.split("(\n)");
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                URL u = new URL(trimmed);
                urls.add(u.toExternalForm());
            }
        }
        if (!urls.isEmpty()) {
            MessageFormat format = new MessageFormat(i18n.getString("message.confirmPost"));
            if (JOptionPane.showConfirmDialog(this.rootPane,
                    format.format(new Object[]{(Integer)urls.size()}),
                    i18n.getString("title.confirmPost"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                dispatchToAll(urls, titleField.getText(), subtitleField.getText(), iconField.getText(), bodyArea.getText());
            };
        }
    }

    private void dispatchToAll(Set<String> urls, String title, String subtitle, String imageUrl, String message) {
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
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            try {
                HttpPost request = new HttpPost(webhook);
                StringEntity params = new StringEntity(toJson);
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                CloseableHttpResponse response = httpClient.execute(request);
                System.out.println(response.getStatusLine().toString());
            } catch (Exception ex) {
                Logger.getLogger(TeamCastFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpClient.close();
                } catch (IOException ex) {
                    Logger.getLogger(TeamCastFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        JOptionPane.showMessageDialog(getRootPane(), i18n.getString("message.complete"), i18n.getString("title.complete"), JOptionPane.INFORMATION_MESSAGE);
    }

    private String createTeamsJsonBody(String title, String subtitle, String imageUrl, String message) {
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
        if (subtitle != null & !subtitle.trim().isEmpty()) {
            activity.addProperty("activitySubtitle", subtitle);
        }
        if (imageUrl != null & !imageUrl.trim().isEmpty()) {
            activity.addProperty("activityImage", imageUrl);
        }
        activity.addProperty("activityText", message);
        String toJson = gson.toJson(root);
        return toJson;
    }

    private String createDiscordJsonBody(String title, String subtitle, String imageUrl, String message) {
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

    private String createSlackJsonBody(String title, String subtitle, String imageUrl, String message) {
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
