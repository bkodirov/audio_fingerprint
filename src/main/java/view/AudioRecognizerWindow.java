package view;

import fingerprint.DTMF;
import org.tritonus.sampled.convert.PCM2PCMConversionProvider;
import spectrum.MisucgSpectrum;
import sun.misc.IOUtils;
import utils.ByteUtils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class AudioRecognizerWindow extends JFrame {

    boolean running = false;
    // Count>>
    long nrSong = 0;
    JTextField fileTextField = null;

    private AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; // mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    private PCM2PCMConversionProvider conversionProvider = new PCM2PCMConversionProvider();

    private void listenSound(long songId, boolean isMatching) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioFormat formatTmp = null;
        TargetDataLine lineTmp = null;
        String filePath = fileTextField.getText();
        AudioInputStream din = null;
        AudioInputStream outDin = null;
        boolean isMicrophone = false;

        if (filePath == null || filePath.equals("") || isMatching) {

            formatTmp = getFormat(); // Fill AudioFormat with the wanted
            // settings
            DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                    formatTmp);
            lineTmp = (TargetDataLine) AudioSystem.getLine(info);
            isMicrophone = true;
        } else {
            AudioInputStream in;

            if (filePath.contains("http")) {
                URL url = new URL(filePath);
                in = AudioSystem.getAudioInputStream(url);
            } else {
                File file = new File(filePath);
                in = AudioSystem.getAudioInputStream(file);
            }

            AudioFormat baseFormat = in.getFormat();

            System.out.println(baseFormat.toString());

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                    false);

            din = AudioSystem.getAudioInputStream(decodedFormat, in);

            if (!conversionProvider.isConversionSupported(getFormat(),
                    decodedFormat)) {
                System.out.println("Conversion is not supported");
            }

            System.out.println(decodedFormat.toString());

            outDin = conversionProvider.getAudioInputStream(getFormat(), din);
            formatTmp = decodedFormat;

            DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                    formatTmp);
            lineTmp = (TargetDataLine) AudioSystem.getLine(info);
        }

        final AudioFormat format = formatTmp;
        final TargetDataLine line = lineTmp;
        final boolean isMicro = isMicrophone;
        final AudioInputStream outDinSound = outDin;

        if (isMicro) {
            try {
                line.open(format);
                line.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        final long sId = songId;
        final boolean isMatch = isMatching;

        Thread listeningThread = new Thread(() -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            running = true;
            int n = 0;
            byte[] buffer = new byte[1024];

            try {
                while (running) {
                    n++;
                    if (n > 1000)
                        break;

                    int count = 0;
                    if (isMicro) {
                        count = line.read(buffer, 0, 1024);
                    } else {
                        count = outDinSound.read(buffer, 0, 1024);
                    }
                    if (count > 0) {
                        out.write(buffer, 0, count);
                    }
                }

                byte b[] = out.toByteArray();
                for (byte aB : b) {
                    System.out.println(aB);
                }

                try {
//                    makeSpectrum(b, sId, isMatch);

                    FileWriter fstream = new FileWriter("out.txt");
                    BufferedWriter outFile = new BufferedWriter(fstream);

                    for (byte aB : b) {
                        outFile.write("" + aB + ";");
                    }
                    outFile.close();

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }

                out.close();
                line.close();
            } catch (IOException e) {
                System.err.println("I/O problems: " + e);
                System.exit(-1);
            }

        });

        listeningThread.start();
    }

    public void createWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Button buttonStart = new Button("Start");
        Button buttonStop = new Button("Stop");
        Button showSpectrumButton = new Button("Spectrum");
        showSpectrumButton.addActionListener(e -> {
            String filePath = fileTextField.getText();

            File file = new File(filePath);
            AudioInputStream audioIs;
            try {
                audioIs = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException | IOException e1) {
                e1.printStackTrace();
                return;
            }

            AudioInputStream audioInputStream = conversionProvider.getAudioInputStream(audioIs.getFormat(), audioIs);
            byte[] audioBytes;
            try {
                audioBytes = IOUtils.readFully(audioInputStream, -1, false);
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            MisucgSpectrum spectrum = new MisucgSpectrum();
            spectrum.show(audioBytes);

            System.out.println("byte array -> "+ Arrays.toString(audioBytes));
            short[] pcm = ByteUtils.INSTANCE.byteArrayToShortArrayLE(audioBytes);
            System.out.println("PCM array -> "+ Arrays.toString(pcm));
//            AudioTimeFrequencyWindow amplitudeView = new AudioTimeFrequencyWindow(pcm);
//            float[] dtmfFloat = DTMF.generateDTMFTone('1');
//            AudioTimeFrequencyWindow amplitudeView = new AudioTimeFrequencyWindow(ByteUtils.INSTANCE.scalePcm(dtmfFloat, 16));
//            amplitudeView.setVisible(true);
        });
        fileTextField = new JTextField(20);
        fileTextField.setText("/Users/bekakodirov/projects/pyplayer/examples/long/output/test.wav");

        buttonStart.addActionListener(e -> {
            try {
                try {
                    listenSound(nrSong, false);
                } catch (IOException | UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                }
                nrSong++;
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        buttonStop.addActionListener(e -> running = false);

        this.add(buttonStart);
        this.add(buttonStop);
        this.add(showSpectrumButton);
        this.add(fileTextField);
        this.setLayout(new FlowLayout());
        this.setSize(300, 100);
        this.setVisible(true);
    }
}
