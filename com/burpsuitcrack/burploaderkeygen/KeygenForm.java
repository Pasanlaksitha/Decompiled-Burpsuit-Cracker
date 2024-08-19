package com.burpsuitcrack.burploaderkeygen;

import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.nio.charset.*;
import java.security.cert.*;
import java.security.*;
import javax.net.ssl.*;
import com.burpsuitcrack.json.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;
import java.net.*;

public class KeygenForm
{
    private static final String Version = "v1.17";
    private static JFrame frame;
    private static JButton btn_run;
    private static JTextField text_cmd;
    private static JTextField text_license_text;
    private static JTextArea text_license;
    private static JTextArea request;
    private static JTextArea response;
    private static JLabel label0_1;
    private static JPanel panel1;
    private static JPanel panel2;
    private static JPanel panel3;
    private static JCheckBox check_autorun;
    private static JCheckBox check_ignore;
    private static JCheckBox check_early;
    private static String LatestVersion;
    private static final String DownloadURL = "https://portswigger-cdn.net/burp/releases/download?product=pro&type=Jar&version=";
    private static final String LoaderPath;
    private static String VersionData;
    private static final String LoaderDir;
    private static final String ConfigFileName;
    private static String[] cmd;
    private static String cmd_str;
    
    public static BufferedReader execCommand(final String[] command) {
        try {
            final Process proc = new ProcessBuilder(command).start();
            return new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private static int getJavaVersion(final String path) {
        final String[] command = { path, "-version" };
        final BufferedReader buf = execCommand(command);
        if (buf == null) {
            return 0;
        }
        while (true) {
            String line;
            try {
                line = buf.readLine();
            }
            catch (IOException e) {
                return 0;
            }
            if (line == null) {
                System.out.println("Warning: cannot get Java version of '" + path + "'!");
                return 0;
            }
            if (!line.contains("version")) {
                continue;
            }
            final String[] version = line.split("\"")[1].split("[.\\-]");
            if ("1".equals(version[0])) {
                return Integer.parseInt(version[1]);
            }
            return Integer.parseInt(version[0]);
        }
    }
    
    private static String getBurpPath() {
        String newest_file = "burpsuite_jar_not_found";
        try {
            long newest_time = 0L;
            final File f = new File(KeygenForm.LoaderPath);
            String current_dir;
            if (f.isDirectory()) {
                current_dir = f.getPath();
            }
            else {
                current_dir = f.getParentFile().toString();
            }
            final DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(current_dir, new String[0]), "burpsuite_*.jar");
            for (final Path path : dirStream) {
                if (!Files.isDirectory(path, new LinkOption[0]) && newest_time < path.toFile().lastModified()) {
                    newest_time = path.toFile().lastModified();
                    newest_file = path.toString();
                }
            }
            dirStream.close();
        }
        catch (Throwable t) {}
        return newest_file;
    }
    
    private static boolean IsWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("win");
    }
    
    private static String[] GetCMD() {
        final String JAVA_PATH = getJavaPath();
        final int JAVA_VERSION = getJavaVersion(JAVA_PATH);
        final String BURP_PATH = getBurpPath();
        final ArrayList<String> cmd = new ArrayList<String>();
        cmd.add(JAVA_PATH);
        if (JAVA_VERSION == 0) {
            return new String[] { "Cannot find java! Please put jdk in the same path with keygen." };
        }
        if (JAVA_VERSION == 16) {
            cmd.add("--illegal-access=permit");
        }
        if (JAVA_VERSION >= 17) {
            cmd.add("--add-opens=java.desktop/javax.swing=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/java.lang=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm.tree=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm.Opcodes=ALL-UNNAMED");
        }
        if (JAVA_VERSION > 8) {
            cmd.add("-javaagent:" + KeygenForm.LoaderPath);
            cmd.add("-noverify");
            cmd.add("-jar");
            cmd.add(BURP_PATH);
            return cmd.toArray(new String[0]);
        }
        return new String[] { "Not support Java 8, please use old version! https://github.com/h3110w0r1d-y/BurpLoaderKeygen/releases/tag/1.7" };
    }
    
    private static String GetCMDStr(final String[] cmd) {
        final StringBuilder cmd_str = new StringBuilder();
        for (final String x : cmd) {
            cmd_str.append("\"").append(x.replace("\"", "\\\"")).append("\" ");
        }
        return cmd_str.toString();
    }
    
    private static boolean verifyFile(final File javafile) {
        if (!javafile.exists() || javafile.isDirectory()) {
            return false;
        }
        if (!javafile.canExecute()) {
            System.out.println("Warning: '" + javafile.getPath() + "' can not execute!");
            return false;
        }
        System.out.println("\u001b[32mSuccess\u001b[0m: '" + javafile.getPath() + "' can execute!");
        return true;
    }
    
    private static String verifyPath(final String path) {
        File javafile = new File(path);
        if (verifyFile(javafile)) {
            return javafile.getPath();
        }
        javafile = new File(path + ".exe");
        if (verifyFile(javafile)) {
            return javafile.getPath();
        }
        return null;
    }
    
    private static String getJavaPath() {
        final String[] paths = { KeygenForm.LoaderDir + File.separator + "bin", KeygenForm.LoaderDir + File.separator + "jre" + File.separator + "bin", KeygenForm.LoaderDir + File.separator + "jdk" + File.separator + "bin", System.getProperty("java.home") + File.separator + "bin" };
        String java_path = null;
        for (final String path_str : paths) {
            java_path = verifyPath(path_str + File.separator + "java");
            if (java_path != null) {
                break;
            }
            try {
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(path_str, new String[0]), "java[0-9]{1,2}");
                for (final Path path : dirStream) {
                    if (!Files.isDirectory(path, new LinkOption[0]) && Files.isExecutable(path)) {
                        return path.toString();
                    }
                }
                dirStream = Files.newDirectoryStream(Paths.get(path_str, new String[0]), "java[0-9]{1,2}\\.exe");
                for (final Path path : dirStream) {
                    if (!Files.isDirectory(path, new LinkOption[0]) && Files.isExecutable(path)) {
                        return path.toString();
                    }
                }
            }
            catch (IOException ex) {}
        }
        return java_path;
    }
    
    private static String readProperty(final String key) {
        final Properties properties = new Properties();
        final File file = new File(KeygenForm.ConfigFileName);
        try {
            file.createNewFile();
        }
        catch (Exception ignored) {
            return "0";
        }
        try {
            final InputStream inStream = Files.newInputStream(file.toPath(), new OpenOption[0]);
            properties.load(inStream);
        }
        catch (IOException e) {
            return "0";
        }
        return properties.getProperty(key);
    }
    
    private static void setProperty(final String key, final String value) {
        final Properties properties = new Properties();
        try {
            final InputStream inStream = Files.newInputStream(Paths.get(KeygenForm.ConfigFileName, new String[0]), new OpenOption[0]);
            properties.load(inStream);
            properties.setProperty(key, value);
            final FileOutputStream out = new FileOutputStream(KeygenForm.ConfigFileName, false);
            properties.store(out, "");
            out.close();
        }
        catch (IOException ex) {}
    }
    
    public static String GetHTTPBody(final String url) {
        try {
            final URL realUrl = new URL(url);
            final HttpsURLConnection https = (HttpsURLConnection)realUrl.openConnection();
            https.connect();
            if (https.getResponseCode() == 200) {
                final InputStream is = https.getInputStream();
                final BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                final StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                return sbf.toString();
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    private static void trustAllHosts() {
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                }
            } };
        try {
            final SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String GetLatestVersion() {
        try {
            if (KeygenForm.VersionData == null) {
                KeygenForm.VersionData = GetHTTPBody("https://portswigger.net/burp/releases/data?pageSize=5");
            }
            final JSONObject data = JSONParse.Parse(KeygenForm.VersionData);
            if (data == null) {
                return "";
            }
            final JSONObject[] list;
            final JSONObject[] Results = list = data.get("ResultSet").getList("Results");
            for (final JSONObject Result : list) {
                boolean isProfessional = false;
                boolean isEarlyAdopter = false;
                for (final JSONObject category : Result.getList("categories")) {
                    if ("Professional".equals(category.String())) {
                        isProfessional = true;
                    }
                }
                if (isProfessional) {
                    if ("Early Adopter".equals(Result.getList("releaseChannels")[0].String())) {
                        isEarlyAdopter = true;
                    }
                    if (!isEarlyAdopter || "1".equals(readProperty("early"))) {
                        return Result.getString("version");
                    }
                }
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    private static void CheckNewVersion() {
        KeygenForm.label0_1.setText("Checking the latest version of BurpSuite...");
        KeygenForm.label0_1.setForeground(Color.BLACK);
        KeygenForm.LatestVersion = GetLatestVersion();
        if (KeygenForm.LatestVersion.equals("")) {
            KeygenForm.label0_1.setText("Failed to check the latest version of BurpSuite");
        }
        else if (!KeygenForm.cmd_str.contains(KeygenForm.LatestVersion + ".jar")) {
            KeygenForm.label0_1.setText("Latest version: " + KeygenForm.LatestVersion + ". Click to download.");
            KeygenForm.label0_1.setForeground(Color.BLUE);
        }
        else {
            KeygenForm.label0_1.setText("Your BurpSuite is already the latest version(" + KeygenForm.LatestVersion + ")");
            KeygenForm.label0_1.setForeground(new Color(0, 100, 0));
        }
    }
    
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            trustAllHosts();
        }
        catch (Exception ex) {}
        String LicenseName = "h3110w0r1d";
        if (readProperty("auto_run") == null) {
            setProperty("auto_run", "0");
        }
        if (readProperty("ignore") == null) {
            setProperty("ignore", "0");
        }
        if (readProperty("early") == null) {
            setProperty("early", "0");
        }
        for (int i = 0; i < args.length; ++i) {
            final String s = args[i];
            switch (s) {
                case "-a":
                case "-auto": {
                    if (i + 1 >= args.length) {
                        setProperty("auto_run", "1");
                        break;
                    }
                    if (args[i + 1].equals("0")) {
                        setProperty("auto_run", "0");
                        break;
                    }
                    setProperty("auto_run", "1");
                    break;
                }
                case "-i":
                case "-ignore": {
                    if (i + 1 >= args.length) {
                        setProperty("ignore", "1");
                        break;
                    }
                    if (args[i + 1].equals("0")) {
                        setProperty("ignore", "0");
                        break;
                    }
                    setProperty("ignore", "1");
                    break;
                }
                case "-n":
                case "-name": {
                    if (i + 1 < args.length) {
                        LicenseName = args[i + 1];
                        break;
                    }
                    break;
                }
            }
        }
        if (KeygenForm.cmd_str.endsWith(".jar\" ") && readProperty("auto_run").equals("1")) {
            try {
                new ProcessBuilder(KeygenForm.cmd).start();
                if (readProperty("ignore").equals("1") || KeygenForm.cmd_str.contains(GetLatestVersion() + ".jar")) {
                    System.exit(0);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        KeygenForm.panel1 = new JPanel();
        KeygenForm.panel2 = new JPanel();
        KeygenForm.panel3 = new JPanel();
        KeygenForm.frame = new JFrame("Burp Suite Pro Loader & Keygen v1.17 - By h3110w0r1d");
        KeygenForm.btn_run = new JButton("Run");
        KeygenForm.label0_1 = new JLabel("Checking the latest version of BurpSuite...");
        final JLabel label1 = new JLabel("Loader Command:", 4);
        final JLabel label2 = new JLabel("License Text:", 4);
        KeygenForm.text_cmd = new JTextField(KeygenForm.cmd_str);
        KeygenForm.text_license_text = new JTextField("licensed to " + LicenseName);
        KeygenForm.text_license = new JTextArea(Keygen.generateLicense(KeygenForm.text_license_text.getText()));
        KeygenForm.request = new JTextArea();
        KeygenForm.response = new JTextArea();
        KeygenForm.check_autorun = new JCheckBox("Auto Run");
        KeygenForm.check_ignore = new JCheckBox("Ignore Update");
        KeygenForm.check_early = new JCheckBox("Early Adopter");
        KeygenForm.check_autorun.setBounds(150, 25, 110, 20);
        KeygenForm.check_autorun.setSelected(readProperty("auto_run").equals("1"));
        KeygenForm.check_autorun.addChangeListener(changeEvent -> {
            if (KeygenForm.check_autorun.isSelected()) {
                setProperty("auto_run", "1");
            }
            else {
                setProperty("auto_run", "0");
            }
            return;
        });
        KeygenForm.check_ignore.setBounds(260, 25, 150, 20);
        KeygenForm.check_ignore.setSelected(readProperty("ignore").equals("1"));
        KeygenForm.check_ignore.addChangeListener(changeEvent -> {
            if (KeygenForm.check_ignore.isSelected()) {
                setProperty("ignore", "1");
            }
            else {
                setProperty("ignore", "0");
            }
            return;
        });
        KeygenForm.check_early.setBounds(410, 25, 150, 20);
        KeygenForm.check_early.setSelected(readProperty("early").equals("1"));
        KeygenForm.check_early.addChangeListener(changeEvent -> {
            if (KeygenForm.check_early.isSelected()) {
                setProperty("early", "1");
            }
            else {
                setProperty("early", "0");
            }
            CheckNewVersion();
            return;
        });
        KeygenForm.label0_1.setLocation(150, 5);
        label1.setBounds(5, 50, 140, 22);
        KeygenForm.text_cmd.setLocation(150, 50);
        KeygenForm.btn_run.setSize(60, 22);
        label2.setBounds(5, 77, 140, 22);
        KeygenForm.text_license_text.setLocation(150, 77);
        KeygenForm.panel1.setBorder(BorderFactory.createTitledBorder("License"));
        KeygenForm.panel2.setBorder(BorderFactory.createTitledBorder("Activation Request"));
        KeygenForm.panel3.setBorder(BorderFactory.createTitledBorder("Activation Response"));
        KeygenForm.text_license.setLocation(10, 15);
        KeygenForm.request.setLocation(10, 15);
        KeygenForm.response.setLocation(10, 15);
        KeygenForm.panel1.setLayout(null);
        KeygenForm.panel2.setLayout(null);
        KeygenForm.panel3.setLayout(null);
        KeygenForm.frame.setLayout(null);
        KeygenForm.frame.setMinimumSize(new Dimension(800, 500));
        KeygenForm.frame.setLocationRelativeTo(null);
        KeygenForm.frame.setDefaultCloseOperation(3);
        KeygenForm.frame.setBackground(Color.LIGHT_GRAY);
        KeygenForm.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int H = KeygenForm.frame.getHeight() - 150;
                final int W = KeygenForm.frame.getWidth();
                KeygenForm.text_cmd.setSize(W - 235, 22);
                KeygenForm.btn_run.setLocation(W - 80, 50);
                KeygenForm.text_license_text.setSize(W - 170, 22);
                KeygenForm.label0_1.setSize(W - 170, 20);
                KeygenForm.text_license.setSize((W - 15) / 2 - 25, H / 2 - 25);
                KeygenForm.request.setSize((W - 15) / 2 - 25, H / 2 - 25);
                KeygenForm.response.setSize(W - 43, H / 2 - 25);
                KeygenForm.panel1.setBounds(5, 104, (W - 15) / 2 - 5, H / 2);
                KeygenForm.panel2.setBounds(3 + (W - 15) / 2, 104, (W - 15) / 2 - 5, H / 2);
                KeygenForm.panel3.setBounds(5, 109 + H / 2, W - 23, H / 2);
            }
        });
        KeygenForm.btn_run.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Runtime.getRuntime().exec(GetCMD());
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        KeygenForm.text_license.setLineWrap(true);
        KeygenForm.text_license.setEditable(false);
        KeygenForm.text_license_text.setHorizontalAlignment(0);
        KeygenForm.text_license.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        KeygenForm.text_license_text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                KeygenForm.text_license.setText(Keygen.generateLicense(KeygenForm.text_license_text.getText()));
            }
            
            @Override
            public void removeUpdate(final DocumentEvent e) {
                KeygenForm.text_license.setText(Keygen.generateLicense(KeygenForm.text_license_text.getText()));
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
                KeygenForm.text_license.setText(Keygen.generateLicense(KeygenForm.text_license_text.getText()));
            }
        });
        KeygenForm.request.setLineWrap(true);
        KeygenForm.request.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        KeygenForm.request.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                KeygenForm.response.setText(Keygen.generateActivation(KeygenForm.request.getText()));
            }
            
            @Override
            public void removeUpdate(final DocumentEvent e) {
                KeygenForm.response.setText(Keygen.generateActivation(KeygenForm.request.getText()));
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
                KeygenForm.response.setText(Keygen.generateActivation(KeygenForm.request.getText()));
            }
        });
        KeygenForm.response.setLineWrap(true);
        KeygenForm.response.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        KeygenForm.frame.add(KeygenForm.check_autorun);
        KeygenForm.frame.add(KeygenForm.check_ignore);
        KeygenForm.frame.add(KeygenForm.check_early);
        KeygenForm.frame.add(KeygenForm.btn_run);
        KeygenForm.frame.add(KeygenForm.label0_1);
        KeygenForm.frame.add(label1);
        KeygenForm.frame.add(label2);
        KeygenForm.frame.add(KeygenForm.panel1);
        KeygenForm.frame.add(KeygenForm.panel2);
        KeygenForm.frame.add(KeygenForm.panel3);
        KeygenForm.frame.add(KeygenForm.text_cmd);
        KeygenForm.frame.add(KeygenForm.text_license_text);
        KeygenForm.panel1.add(KeygenForm.text_license);
        KeygenForm.panel2.add(KeygenForm.request);
        KeygenForm.panel3.add(KeygenForm.response);
        if (KeygenForm.text_cmd.getText().contains("burpsuite_jar_not_found.jar")) {
            KeygenForm.btn_run.setEnabled(false);
            KeygenForm.check_autorun.setSelected(false);
            KeygenForm.check_autorun.setEnabled(false);
        }
        KeygenForm.frame.setVisible(true);
        KeygenForm.btn_run.setFocusable(false);
        KeygenForm.LatestVersion = GetLatestVersion();
        if (KeygenForm.LatestVersion.equals("")) {
            KeygenForm.label0_1.setText("Failed to check the latest version of BurpSuite");
        }
        else if (!KeygenForm.cmd_str.contains(KeygenForm.LatestVersion + ".jar")) {
            KeygenForm.label0_1.setText("Latest version: " + KeygenForm.LatestVersion + ". Click to download.");
            KeygenForm.label0_1.setForeground(Color.BLUE);
            KeygenForm.label0_1.setCursor(Cursor.getPredefinedCursor(12));
            KeygenForm.label0_1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    super.mouseClicked(e);
                    try {
                        Desktop.getDesktop().browse(new URI("https://portswigger-cdn.net/burp/releases/download?product=pro&type=Jar&version=" + KeygenForm.LatestVersion));
                    }
                    catch (Exception ex) {}
                }
            });
        }
        else {
            KeygenForm.label0_1.setText("Your BurpSuite is already the latest version(" + KeygenForm.LatestVersion + ")");
            KeygenForm.label0_1.setForeground(new Color(0, 100, 0));
        }
    }
    
    static {
        try {
            if (IsWindows()) {
                LoaderPath = KeygenForm.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1);
            }
            else {
                LoaderPath = KeygenForm.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            }
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LoaderDir = new File(KeygenForm.LoaderPath).getParent();
        ConfigFileName = KeygenForm.LoaderDir + File.separator + ".config.ini";
        KeygenForm.cmd = GetCMD();
        KeygenForm.cmd_str = GetCMDStr(KeygenForm.cmd);
    }
}
