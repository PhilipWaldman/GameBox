package com.gamebox.util;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class ToneGenerator {
    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    // and modified again by Philip Waldman

    private final int sampleRate = 8000;
    private final int numSamples;
    private final double[] sample;
    private final double frequency;
    private final byte[] generatedSound;
    private final Handler handler = new Handler();

    /**
     * @param frequency frequency of the tone in hertz
     * @param duration  duration of the tone in seconds
     */
    public ToneGenerator(double frequency, double duration) {
        this.frequency = frequency;
        numSamples = (int) (duration * sampleRate);
        sample = new double[numSamples];
        generatedSound = new byte[2 * numSamples];
    }

    public void play() {
        // Use a new tread as this can take a while
        final Thread thread = new Thread(() -> {
            genTone();
            handler.post(this::playSound);
        });
        thread.start();
    }

    private void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / frequency));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSound[idx++] = (byte) (val & 0x00ff);
            generatedSound[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    private void playSound() {
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSound.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSound, 0, generatedSound.length);
        audioTrack.play();
    }
}