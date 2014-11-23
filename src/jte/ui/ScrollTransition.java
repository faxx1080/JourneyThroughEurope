/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

/**
 *
 * @author Frank
 */
public class ScrollTransition {
    public static Timeline getTimeline(ScrollPane scp, Duration dur, double byX, double byY) {
        final Timeline out = new Timeline();
        KeyFrame sx = new KeyFrame(dur, new KeyValue(scp.hvalueProperty(), byX));
        KeyFrame sy = new KeyFrame(dur, new KeyValue(scp.vvalueProperty(), byY));
        out.getKeyFrames().add(sx);
        out.getKeyFrames().add(sy);
        return out;
    }
}
