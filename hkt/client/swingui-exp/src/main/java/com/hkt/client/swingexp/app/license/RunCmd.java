package com.hkt.client.swingexp.app.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class RunCmd {

    private static RunCmd cmd;

    public static RunCmd getInstance() {
        if (cmd == null) {
            cmd = new RunCmd();
        }
        return cmd;
    }

    public RunCmd() {
    }

    public String getLinuxSerial() {
        String result = "";
        Runtime runtime = Runtime.getRuntime();
        while (result == null || result.length() == 0) {
            try {
                try {
                    Process process = runtime.exec("/sbin/udevadm info --query=property --name=sda");

                    ProcessResultReader stdout = new ProcessResultReader(
                            process.getInputStream(), "STDOUT");
                    stdout.start();
                    final int exitValue = process.waitFor();
                    if (exitValue == 0) {
                        result = stdout.toString();
                    }
                } catch (Exception e) {
                    result = "";
                    JOptionPane.showMessageDialog(null, "Chương trình chưa sẵn sàng!", "Chú ý", JOptionPane.WARNING_MESSAGE);
                }
                new Thread().sleep(1000);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
        }
        if (result != null && result.length() > 0) {
            String s = result.substring(result.indexOf("ID_SERIAL_"), result.length() - 1);
            int len = 0;
            for (int i = 0; i < s.length(); i++) {
                if ('\n' == s.charAt(i)) {
                    len = i;
                    break;
                }
            }
            result = s.substring(0, len).split("=")[1];
        }
        return result;
    }

    class ProcessResultReader extends Thread {

        final InputStream is;
        final String type;
        final StringBuilder sb;

        ProcessResultReader(final InputStream is, String type) {
            this.is = is;
            this.type = type;
            this.sb = new StringBuilder();
        }

        @Override
        public void run() {
            try {
                final InputStreamReader isr = new InputStreamReader(is);
                final BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    this.sb.append(line).append("\n");
                }
            } catch (final IOException ioe) {
                JOptionPane.showMessageDialog(null, "Chương trình chưa sẵn sàng!", "Chú ý", JOptionPane.WARNING_MESSAGE);
            }
        }

        @Override
        public String toString() {
            return this.sb.toString();
        }
    }

    class IllegalCommandException extends Exception {

        private static final long serialVersionUID = 1L;

        public IllegalCommandException() {
        }
    }
}

